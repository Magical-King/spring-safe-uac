package org.springframework.uac.mapper.perm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.uac.model.domain.GzPermission;
import org.springframework.uac.model.vo.perm.MenuVo;
import org.springframework.uac.model.vo.perm.PermVo;
import org.springframework.uac.model.vo.role.RolePermTreeVo;
import org.springframework.uac.core.security.PcGrantedAuthority;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
public interface GzPermissionMapper extends BaseMapper<GzPermission> {

    /**
     * 查询全部权限
     *
     * @param roleId
     * @return
     */
    @Select("<script> SELECT p.perm_id,p.perm_type,p.perm_name,p.perm_parent_id FROM gz_permission p " +
            "WHERE  1=1 " +
            "<if test=\"roleId != null and '' != roleId\"> " +
            "and p.perm_role_type = (select r.role_type_id from gz_role r where r.role_id = #{roleId} ) " +
            "</if> " +
            "ORDER BY perm_type,perm_id ASC </script>")
    List<RolePermTreeVo> selectPermList(@Param("roleId") String roleId);

    /**
     * 查询已授权(查询权限用)
     *
     * @param roleId
     * @return
     */
    @Select("SELECT " +
            "p.perm_url AS role " +
            "FROM " +
            "gz_permission p," +
            "gz_role r," +
            "gz_role_perm rp " +
            "WHERE " +
            "r.role_id = #{roleId} and r.role_status = 0 AND r.role_id = rp.role_id AND p.perm_id = rp.perm_id  and p.perm_type=2")
    List<PcGrantedAuthority> getPermByRoleId(@Param("roleId") String roleId);

    /**
     * 查询已授权(菜单)
     *
     * @param roleId
     * @return
     */
    @Select("SELECT " +
            "p.perm_id,p.perm_type,p.perm_name,p.perm_parent_id,p.perm_url,p.perm_url_prefix,p.component,p.iframe_url " +
            "FROM " +
            "gz_permission p," +
            "gz_role r," +
            "gz_role_perm rp " +
            "WHERE " +
            "r.role_id = #{roleId}  AND r.role_id = rp.role_id AND p.perm_id = rp.perm_id  and (p.perm_type=1 or p.perm_type=0) and p.perm_is_show=1  ORDER BY p.perm_type,p.perm_id ASC")
    List<MenuVo> selectPermMenu(@Param("roleId") String roleId);

    /**
     * 根据权限类型查询列表
     *
     * @param permType
     * @return
     */
    @Select("<script> " +
            "SELECT p.*,pp.perm_name as permParentName " +
            "FROM gz_permission p " +
            "LEFT JOIN   " +
            "gz_permission pp " +
            "on pp.perm_id=p.perm_parent_id " +
            "where 1 = 1 " +
            "<if test=\"permType != null \"> " +
            "and p.perm_type = ${permType} " +
            "</if> " +
            "<if test=\"permParentId != null \"> " +
            "and p.perm_parent_id = #{permParentId} " +
            "</if> " +
            "</script>")
    List<PermVo> selectPermVoList(@Param("permType")Integer permType, @Param("permParentId")Integer permParentId);
}
