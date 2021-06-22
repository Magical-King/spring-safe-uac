package org.springframework.uac.mapper.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.uac.model.domain.GzUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.uac.model.vo.user.UserVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
public interface GzUserMapper extends BaseMapper<GzUser> {

    /**
     * 查询列表
     * @param wrapper
     * @return
     */
    @Select("SELECT " +
            "u.user_id,u.user_name,u.user_status,u.user_email,u.user_mobile,u.user_identity_number,u.user_is_temporary,u.user_end_date,u.user_update_date," +
            "org.org_id as 'org.org_id', org.org_name as 'org.org_name', org.org_is_invalid as 'org.org_is_invalid', " +
            "role.role_id as 'role.role_id', role.role_name as 'role.role_name',role.role_status as 'role.role_status' " +
            "FROM gz_user u  " +
            "LEFT JOIN gz_organization org on u.user_org_id = org.org_id " +
            "LEFT JOIN gz_user_role userRole on userRole.user_id = u.user_id " +
            "LEFT JOIN gz_role role on userRole.role_id = role.role_id " +
            "${ew.customSqlSegment}")
    List<UserVo> findAll(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 账号是否存在
     * @param userId
     * @return
     */
    @Select("SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END FROM gz_user t WHERE t.user_id = #{userId}")
    boolean exists(@Param("userId") String userId);

}
