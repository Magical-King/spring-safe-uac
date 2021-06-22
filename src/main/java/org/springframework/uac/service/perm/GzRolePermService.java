package org.springframework.uac.service.perm;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzRolePerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
public interface GzRolePermService extends IService<GzRolePerm> {
    /**
     * 获取列表中存在的角色id
     * @param ids
     * @return
     */
    List<String> findRoleIdListInRoleIds(List<String> ids);

    /**
     * 保存角色对应权限
     * @param roleId
     * @param permList
     * @throws SpringSafeException
     */
    void savePerm(String roleId, List<Integer> permList) throws SpringSafeException;

    /**
     * 根据角色删除对应权限关联
     * @param roleId
     */
    void removeByRoleId(String roleId);

    /**
     * 根据角色和permIds查询存在的记录
     * @param roleId
     * @param permIds
     * @return
     */
    List<GzRolePerm> findByRoleAndPermIds(String roleId, List<Integer> permIds);
}
