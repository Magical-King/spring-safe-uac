package org.springframework.uac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.uac.model.domain.GzSecurityConfig;
import org.springframework.uac.model.vo.config.SecurityConfigVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
public interface GzSecurityConfigMapper extends BaseMapper<GzSecurityConfig> {

    /**
     * 判断是否存在库
     * @param tableSchema
     * @return
     */
    @Select( "SELECT CASE WHEN COUNT(1) THEN true else false end FROM information_schema.TABLES WHERE table_schema = #{tableSchema} and table_name = 'gz_version'" )
    boolean exists(@Param("tableSchema") String tableSchema);

    /**
     * 查询非系统配置
     * @param wrapper
     * @return
     */
    @Select("SELECT config_id,config_name,config_value,remake  FROM gz_security_config ${ew.customSqlSegment}")
    List<SecurityConfigVo> listNonSys(@Param(Constants.WRAPPER) Wrapper wrapper);
}
