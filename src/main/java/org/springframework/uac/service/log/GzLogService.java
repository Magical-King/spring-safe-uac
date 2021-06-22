package org.springframework.uac.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.uac.model.domain.GzLog;
import org.springframework.uac.model.dto.log.LogListDto;
import org.springframework.uac.model.vo.log.LogVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mayato
 * @since 2020-08-10
 */
public interface GzLogService extends IService<GzLog> {
    /**
     * 查询日志列表
     * @param logListDto
     * @return
     */
    PageInfo<LogVo> list(LogListDto logListDto);

    /**
     * 获取表空间占用，单位GB
     * @param tableSchema
     * @param tableName
     * @return
     */
    int getMysqlTableSpace(String tableSchema, String tableName);
}
