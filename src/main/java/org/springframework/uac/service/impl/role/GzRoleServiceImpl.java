package org.springframework.uac.service.impl.role;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.core.security.PcGrantedAuthority;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.mapper.role.GzRoleMapper;
import org.springframework.uac.model.domain.GzRole;
import org.springframework.uac.model.dto.role.RoleDto;
import org.springframework.uac.model.dto.role.RoleListDto;
import org.springframework.uac.model.vo.role.RolePermTreeVo;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.perm.GzRolePermService;
import org.springframework.uac.service.review.GzReviewService;
import org.springframework.uac.service.role.GzRoleService;
import org.springframework.uac.service.role.GzUserRoleService;
import org.springframework.uac.utils.MessageSourceUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
@Service
@Slf4j
@Transactional(rollbackFor = SpringSafeException.class)
public class GzRoleServiceImpl extends ServiceImpl<GzRoleMapper, GzRole> implements GzRoleService {
    @Autowired
    GzUserRoleService gzUserRoleService;
    @Autowired
    GzRolePermService gzRolePermService;
    @Autowired
    GzPermissionService gzPermissionService;
    @Autowired
    GzReviewService reviewService;
    @Autowired
    GzSecurityConfigService configService;

    @Autowired
    MessageSourceUtil messageSourceUtil;


    @Override
    @Transactional(readOnly = true)
    public PageInfo<GzRole> list(RoleListDto role) {
        PageHelper.startPage(role.getStart(), role.getLimit());

        QWrapper<GzRole> queryWrapper = new QWrapper<>();
        queryWrapper.eq(GzRole.ROLE_ORG_ID, role.getRoleOrgId())
                .like(GzRole.ROLE_NAME, role.getRoleName())
                .orderBy(true, role.isAsc(), role.getProperty() == null ? GzRole.ROLE_ID : role.getProperty());
        List<GzRole> list = this.list(queryWrapper);
        return new PageInfo<>(list);
    }

    @Override
    public void create(RoleDto role) throws SpringSafeException {
        //如果角色名称已存在则返回异常
//        QWrapper<GzRole> queryWrapper = new QWrapper<>();
//        queryWrapper.eq(GzRole.ROLE_NAME, role.getRoleName());
//        GzRole one = this.getOne(queryWrapper);
//        if (one != null) {
//            throw new SpringSafeException(13000);
//        }
        GzRole gzRole = new ModelMapper().map(role, GzRole.class);
        gzRole.setRoleId(UUID.randomUUID().toString());
        gzRole.setRoleCreateBy(SecurityUtils.getCurrentLoginName());
        gzRole.setRoleCreateDate(LocalDateTime.now());
        gzRole.setRoleUpdateBy(SecurityUtils.getCurrentLoginName());
        gzRole.setRoleUpdateDate(LocalDateTime.now());
        gzRole.setRoleStatus(0);
        //插入失败则抛出异常
        if (!this.save(gzRole)) {
            throw new SpringSafeException(10001);
        }
    }

    @Override
    public void update(RoleDto role) throws SpringSafeException {
        //如果角色名称已存在则返回异常
//        QWrapper<GzRole> queryWrapper = new QWrapper<GzRole>();
//        queryWrapper.eq(GzRole.ROLE_NAME, role.getRoleName());
//        GzRole one = this.getOne(queryWrapper);
//        if (one != null) {
//            throw new SpringSafeException(13000);
//        }
        GzRole gzRole = new ModelMapper().map(role, GzRole.class);
        gzRole.setRoleUpdateBy(SecurityUtils.getCurrentLoginName());
        gzRole.setRoleUpdateDate(LocalDateTime.now());

        if (!this.updateById(gzRole)) {
            throw new SpringSafeException(10001);
        }

    }

    @Override
    public Message delete(List<String> ids) throws SpringSafeException {
        if (ids == null || ids.size() == 0) {
            return null;
        }
        //存在关联的role
        List<String> roleIdList = new ArrayList<>();
        //userRole中存在关联
        roleIdList = gzUserRoleService.findRoleIdListInRoleIds(ids);

        //rolePerm中存在关联
        roleIdList.addAll(gzRolePermService.findRoleIdListInRoleIds(ids));

        //去重
        roleIdList = roleIdList.stream().distinct().collect(Collectors.toList());
        //去除不能删的
        List<String> finalRoleIdList = roleIdList;
        List<String> realIds = ids.stream().filter(item -> !finalRoleIdList.contains(item)).collect(Collectors.toList());
        //执行删除
        if (!this.removeByIds(realIds)) {
            throw new SpringSafeException(13001);
        }

        if (roleIdList != null && roleIdList.size() > 0) {
            return new Message(Status.SUCCESS, messageSourceUtil.message(13001));
        }

        return null;
    }

    @Override
    public void changeStatus(RoleDto role, Integer status) throws SpringSafeException {
        GzRole gzrole = new GzRole();
        gzrole.setRoleId(role.getRoleId());
        gzrole.setRoleStatus(status);
        if (!this.updateById(gzrole)) {
            throw new SpringSafeException(10001);
        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<PcGrantedAuthority> getPcGrantedAuthorityByRoleId(String roleId) {
        return gzPermissionService.getPermByRoleId(roleId);
    }
    @Transactional(readOnly = true)
    @Override
    public List<RolePermTreeVo> listPerm(String roleId) {
        List<RolePermTreeVo> permTreeVoList = gzPermissionService.selectPermList(roleId);
        //组合成vo
        List<RolePermTreeVo> level0List = new ArrayList<>();
        //查询的时候按升序排序了
        for (RolePermTreeVo permTree : permTreeVoList) {
            if (permTree.getPermType().equals(0)) {
                level0List.add(permTree);
            } else {
                for (RolePermTreeVo permTree0 : level0List) {
                    if (permTree.getPermType().equals(1)) {
                        if (permTree0.getPermId().equals(permTree.getPermParentId())) {
                            permTree0.getPermTreeVoList().add(permTree);
                        }
                    } else if (permTree.getPermType().equals(2)) {
                        for (RolePermTreeVo permTreeVo1 : permTree0.getPermTreeVoList()) {
                            if (permTreeVo1.getPermId().equals(permTree.getPermParentId())) {
                                permTreeVo1.getPermTreeVoList().add(permTree);
                            }
                        }
                    }
                }
            }
        }
        return level0List;
    }

    @Override
    public void assignPerms(String roleId, List<Integer> permIds) throws SpringSafeException {
        GzRole role = this.getById(roleId);
        if (role == null) {
            throw new SpringSafeException(12000);
        }
        //查询用户是否有权绑定
        List<Integer> list = gzPermissionService.getUrlByRoleType(role.getRoleTypeId(), permIds);

        //部分操作无权限
        if (null != list && null != permIds && list.size() < permIds.size()) {
            throw new SpringSafeException(10008);
        }
        //查询是否已存在
//        List<GzRolePerm> gzRolePermList=gzRolePermService.findByRoleAndPermIds(roleId,permIds);
//        if (!gzRolePermList.isEmpty()) {
//            throw new SpringSafeException(10010);
//        }


        //如果开启了审核功能
        if (this.configService.reviewFlag()) {
            String permIdStr = list.stream().map(String::valueOf).collect(Collectors.joining(","));
            //创建审核记录
            this.reviewService.create(roleId, permIdStr, 2, 20002);
        } else {

            //批量保存
          gzRolePermService.savePerm(roleId,permIds);

        }
    }
}
