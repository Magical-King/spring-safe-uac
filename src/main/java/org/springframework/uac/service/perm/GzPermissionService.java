package org.springframework.uac.service.perm;

import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.uac.model.dto.perm.CreatePermDto;
import org.springframework.uac.model.dto.perm.PermDto;
import org.springframework.uac.model.vo.perm.MenuVo;
import org.springframework.uac.model.vo.perm.PermVo;
import org.springframework.uac.model.vo.role.RolePermTreeVo;
import org.springframework.uac.core.security.PcGrantedAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
public interface GzPermissionService extends IService<GzPermission> {
    /**
     * 审核后通过roleId和perStr（按,分隔)新增权限
     * @param roleId
     * @param permId
     * @throws SpringSafeException
     */
    void assignPerm(String roleId, List<Integer> permId) throws SpringSafeException;

    /**
     * 根据角色获取已授权列表
     * @param roleId
     * @return
     */
    List<PcGrantedAuthority> getPermByRoleId(String roleId);

    /**
     * 根据角色获取全部授权列表(根据type 升序排序)
     * @param roleId
     * @return
     */
    List<RolePermTreeVo> selectPermList(String roleId);

    /**
     * 根据角色类型和permId列表查询url
     * @param roleTypeId
     * @param permIds
     * @return
     */
    List<Integer> getUrlByRoleType(Integer roleTypeId, List<Integer> permIds);

    /**
     * 根据角色类型查询permId
     * @param roleTypeId
     * @return
     */
    List<Integer> getIdByRoleType(Integer roleTypeId);

    /**
     * 根据类型获得权限分页
     * @param permDto
     * @return
     */
    PageInfo<PermVo> list(PermDto permDto);

    /**
     * 获得菜单树
     * @return
     */
    List<RolePermTreeVo> selectAllPermTree();

    /**
     * 创建权限（10000以上）,10000以下由初始化生成
     * @param createPermDto
     * @throws SpringSafeException
     */
    void create(CreatePermDto createPermDto) throws SpringSafeException;

    /**
     * 修改权限（10000以上）,10000以下由初始化生成
     * @param createPermDto
     * @throws SpringSafeException
     */
    void update(CreatePermDto createPermDto) throws SpringSafeException;

    /**
     * 删除权限（10000以上）,10000以下由初始化生成
     * @param ids
     * @throws SpringSafeException
     */
    void delete(List<Integer> ids) throws SpringSafeException;

    /**
     * 获取菜单
     * @return
     */
    List<MenuVo> listMenu(String userId);

}
