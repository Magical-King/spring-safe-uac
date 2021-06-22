package org.springframework.uac.mapper.log;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.uac.model.domain.GzLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.uac.model.vo.log.LogVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mayato
 * @since 2020-08-10
 */
public interface GzLogMapper extends BaseMapper<GzLog> {
    /**
     * 查询日志列表
     * @param wrapper
     * @return
     */
    @Select("SELECT * FROM gz_log ${ew.customSqlSegment}")
    List<LogVo> listGzlogVo(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 获取表空间占用，单位GB
     * @param tableSchema
     * @param tableName
     * @return
     */
    @Select("SELECT truncate(a.data_length/1024/1024/1024, 3)+truncate(a.index_length/1024/1024/1024, 3) FROM INFORMATION_SCHEMA.TABLES AS a WHERE a.TABLE_SCHEMA=#{tableSchema} and a.TABLE_NAME=#{tableName}")
    int getMysqlTableSpace(@Param("tableSchema") String tableSchema,@Param("tableName") String tableName);


}
