package org.springframework.uac.mapper.org;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.uac.model.domain.GzOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-30
 */
public interface GzOrganizationMapper extends BaseMapper<GzOrganization> {


    /**
     * 组织是否存在
     * @param orgId
     * @return
     */
    @Select("SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END FROM gz_organization t WHERE t.org_id = #{orgId}")
    boolean exists(@Param("orgId") String orgId);

}
