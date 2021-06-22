package org.springframework.uac.service.role;

import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.uac.model.domain.GzRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.uac.model.dto.role.RoleDto;
import org.springframework.uac.model.dto.role.RoleListDto;
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
public interface GzRoleService extends IService<GzRole> {
    /**
     * 查询角色
     * @param role
     * @return
     */
    PageInfo<GzRole> list(RoleListDto role) ;

    /**
     * 创建角色
     * @param gzRole
     * @throws SpringSafeException
     */
    void create(RoleDto gzRole) throws SpringSafeException;

    /**
     * 更新角色
     * @param role
     * @throws SpringSafeException
     */
    void update(RoleDto role) throws SpringSafeException;

    /**
     * 批量删除角色
     * @param ids
     * @return Message
     * @throws SpringSafeException
     */
    Message delete(List<String> ids) throws SpringSafeException;

    /**
     * 改变角色状态
     * @param role
     * @param status
     * @throws SpringSafeException
     */
    void changeStatus(RoleDto role, Integer status) throws SpringSafeException;

    /**
     * 根据角色获取已授权列表
     * @param roleId
     * @return
     */
    List<PcGrantedAuthority> getPcGrantedAuthorityByRoleId(String roleId);

    /**
     * 根据角色id获取权限菜单
     * @param roleId
     * @return
     */
    List<RolePermTreeVo> listPerm(String roleId);

    /**
     * 授予角色权限
     * @param roleId
     * @param permIds
     * @throws SpringSafeException
     */
    void assignPerms(String roleId, List<Integer> permIds) throws SpringSafeException;

}
