package org.springframework.uac.mapper.role;

import org.apache.ibatis.annotations.Delete;
import org.springframework.uac.model.domain.GzUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
public interface GzUserRoleMapper extends BaseMapper<GzUserRole> {

    /**
     * 删除默认生成关联角色
     */
    @Delete("delete from gz_user_role where (role_id = 'sys_admin' and  user_id='sys_admin') or (role_id = 'sys_audit' and  user_id='sys_audit') or (role_id = 'sys_review' and  user_id='sys_review') or (role_id = 'operator' and  user_id='operator') ")
    void delete();

}
