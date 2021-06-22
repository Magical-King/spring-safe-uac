package org.springframework.uac.service.role;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
public interface GzUserRoleService extends IService<GzUserRole> {

    /**
     * 用户赋权
     * @param userId
     * @param roleId
     * @throws SpringSafeException
     */
    void assignRole(String userId, String roleId) throws SpringSafeException;

    /**
     * 根据用户id找寻角色
     * @param userId
     * @return
     */
    String findRoleIdByUserId(String userId);

    /**
     * 根据角色id列表查询表中存在的角色id
     * @param ids
     * @return
     */
    List<String> findRoleIdListInRoleIds(List<String> ids);

    /**
     * 删除默认角色
     */
    void delete();

}
