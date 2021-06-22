package org.springframework.uac.service.impl.perm;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.mapper.perm.GzPermissionMapper;
import org.springframework.uac.model.domain.GzPermission;
import org.springframework.uac.model.dto.perm.CreatePermDto;
import org.springframework.uac.model.dto.perm.PermDto;
import org.springframework.uac.model.vo.perm.MenuVo;
import org.springframework.uac.model.vo.perm.PermVo;
import org.springframework.uac.model.vo.role.RolePermTreeVo;
import org.springframework.uac.core.security.PcGrantedAuthority;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.perm.GzRolePermService;
import org.springframework.uac.service.review.GzReviewService;
import org.springframework.uac.service.role.GzRoleService;
import org.springframework.uac.service.role.GzUserRoleService;
import org.springframework.uac.utils.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzPermissionServiceImpl extends ServiceImpl<GzPermissionMapper, GzPermission> implements GzPermissionService {
    @Autowired
    GzRoleService gzRoleService;
    @Autowired
    GzRolePermService gzRolePermService;
    @Autowired
    GzSecurityConfigService configService;
    @Autowired
    GzReviewService reviewService;
    @Autowired
    GzUserRoleService gzUserRoleService;


    @Override
    public void assignPerm(String roleId, List<Integer> permList) throws SpringSafeException {

        gzRolePermService.savePerm(roleId, permList);

    }

    @Transactional(readOnly = true)
    @Override
    public List<PcGrantedAuthority> getPermByRoleId(String roleId) {
        List<PcGrantedAuthority> list = this.baseMapper.getPermByRoleId(roleId);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RolePermTreeVo> selectPermList(String roleId) {
        List<RolePermTreeVo> permTreeVoList = this.baseMapper.selectPermList(roleId);
        return permTreeVoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Integer> getUrlByRoleType(Integer roleTypeId, List<Integer> permIds) {
        QWrapper<GzPermission> wrapper = new QWrapper<GzPermission>();
        wrapper.in(GzPermission.PERM_ID, permIds)
                .apply("FIND_IN_SET({0}," + StringUtils.camelToUnderline(GzPermission.PERM_ROLE_TYPE) + ")", roleTypeId)
                .select(GzPermission.PERM_ID);
        List<Integer> list = this.listObjs(wrapper, item -> Integer.parseInt(item.toString()));
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Integer> getIdByRoleType(Integer roleTypeId) {
        List<Integer> list = this.listObjs(new QWrapper<GzPermission>()
                        .apply("FIND_IN_SET({0}," + StringUtils.camelToUnderline(GzPermission.PERM_ROLE_TYPE) + ")", roleTypeId)
                        .select(GzPermission.PERM_ID)
                , i -> Integer.parseInt(i.toString()));
        return list;

    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<PermVo> list(PermDto permDto) {
        PageHelper.startPage(permDto.getStart(), permDto.getLimit());
        List<PermVo> list = this.baseMapper.selectPermVoList(permDto.getPermType(), permDto.getPermParentId());
        return new PageInfo<>(list);

    }

    @Transactional(readOnly = true)
    @Override
    public List<RolePermTreeVo> selectAllPermTree() {
        return gzRoleService.listPerm(null);
    }

    @Override
    public void create(CreatePermDto createPermDto) throws SpringSafeException {

        Integer type0 = 0;
        Integer type1 = 1;
        Integer type2 = 2;
        //判断类型
        Integer permType = createPermDto.getPermType();
        //如果操作url为空则返回
        if (permType.equals(type2) && createPermDto.getPermUrl().isEmpty()) {
            throw new SpringSafeException(40000);
        }
        GzPermission permission = new GzPermission();
        //全部为业务类型
        permission.setPermRoleType(RoleEnum.OPERATOR.getRoleTypeId() + "");
        permission.setPermType(permType);
        permission.setPermName(createPermDto.getPermName());
        permission.setPermParentId(createPermDto.getPermParentId());
        permission.setComponent(createPermDto.getComponent());
        permission.setIframeUrl(createPermDto.getIframeUrl());
        permission = createInfo(permission);


        //功能
        if (permType.equals(type1)) {
            //如果没有同级别id则生成第一个
            permission.setPermParentIds(createPermDto.getPermParentId() + "");
            permission.setPermUrlPrefix(createPermDto.getPermUrlPrefix());
            permission.setPermUrl(createPermDto.getPermUrl());
        }
        //操作
        else if (permType.equals(type2)) {
            //根据父类查询祖先
            GzPermission ancestor = this.getById(createPermDto.getPermParentId());
            permission.setPermParentIds(ancestor.getPermParentId() + "," + createPermDto.getPermParentId());
            permission.setPermUrlPrefix(createPermDto.getPermUrlPrefix());
            permission.setPermUrl(createPermDto.getPermUrl());
        }

        if (!this.save(permission)) {
            throw new SpringSafeException(10001);
        }


    }

    @Override
    public void update(CreatePermDto createPermDto) throws SpringSafeException {
        Integer businessStart = 100000;
        if (createPermDto.getPermId() < businessStart) {
            throw new SpringSafeException(40002);
        }
        GzPermission permission = new GzPermission();
        permission.setPermId(createPermDto.getPermId());
        permission.setPermUrl(createPermDto.getPermUrl());
        permission.setPermUrlPrefix(createPermDto.getPermUrlPrefix());
        permission.setPermName(createPermDto.getPermName());
        permission.setComponent(createPermDto.getComponent());
        permission.setIframeUrl(createPermDto.getIframeUrl());
        GzPermission gzPermission = updateInfo(permission);

        if (!this.updateById(gzPermission)) {
            throw new SpringSafeException(10001);
        }
    }

    @Override
    public void delete(List<Integer> ids) throws SpringSafeException {
        Integer businessStart = 100000;
        for (Integer i : ids) {
            if (i < businessStart) {
                throw new SpringSafeException(40002);
            }
        }
        if (!this.removeByIds(ids)) {
            throw new SpringSafeException(10001);
        }

    }

    @Override
    public List<MenuVo> listMenu(String userId) {
        String roleId = gzUserRoleService.findRoleIdByUserId(userId);
        List<MenuVo> list = this.baseMapper.selectPermMenu(roleId);
        List<MenuVo> level0List = new ArrayList<>();

        //查询的时候按升序排序了
        for (MenuVo permTree : list) {
            if (permTree.getPermType().equals(0)) {
                level0List.add(permTree);
            } else {
                for (MenuVo permTree0 : level0List) {
                    if (permTree.getPermType().equals(1)) {
                        if (permTree0.getPermId().equals(permTree.getPermParentId())) {
                            permTree0.getMenuVoList().add(permTree);
                        }
                    }
                }
            }
        }
        return level0List;
    }

    /**
     * 创建信息
     *
     * @param permission
     * @return
     */
    private GzPermission createInfo(GzPermission permission) throws SpringSafeException {
        updateInfo(permission);
        permission.setPermCreateDate(LocalDateTime.now());
        permission.setPermCreateBy(SecurityUtils.getCurrentLoginName());

        //根据permType和permParentId生成id
        Integer id = generateId(permission.getPermType(), permission.getPermParentId());
        permission.setPermId(id);
        return permission;
    }

    /**
     * 更新信息
     *
     * @param permission
     * @return
     */
    private GzPermission updateInfo(GzPermission permission) {
        permission.setPermUpdateDate(LocalDateTime.now());
        permission.setPermUpdateBy(SecurityUtils.getCurrentLoginName());
        return permission;
    }

    /**
     * 生成id
     *
     * @param permType
     * @param parentId
     * @return
     * @throws SpringSafeException
     */
    private synchronized Integer generateId(Integer permType, Integer parentId) throws SpringSafeException {

//        Integer type0 = 0;
//        Integer type1 = 1;
//        Integer type2 = 2;

        Integer businessStart = 100000;

        if (null == permType) {
            throw new SpringSafeException(40001);
        }
        //为空则创建大类
//        if (null == parentId) {
//            parentId = 0;
//        }
        //根据类型和父类查询数据库最大id
        QWrapper<GzPermission> wrapper = new QWrapper<>();
        wrapper.orderByDesc(StringUtils.camelToUnderline(GzPermission.PERM_ID))
                .last("limit 1")
                .select(GzPermission.PERM_ID);
        Integer id = this.getObj(wrapper, i -> Integer.parseInt(i.toString()));
        if (id.longValue() < businessStart.longValue()) {
            id = 100001;
        } else {
            id++;
        }
//        if (permType.equals(type0)) {
//            if (id == null || id < businessStart) {
//                //业务大类从10001开始
//                id = 100001;
//            } else {
//                id = id + 1;
//            }
//        } else if (permType.equals(type1)) {
//            if (id == null) {
//                id = 100 * (1000 + parentId % businessStart);
//            } else {
//                id = 100 * (id / 100 + 1);
//            }
//        } else if (permType.equals(type2)) {
//            if (id == null) {
//                id = parentId + 1;
//            } else {
//                id = id + 1;
//            }
//        }
        return id;
    }

}
