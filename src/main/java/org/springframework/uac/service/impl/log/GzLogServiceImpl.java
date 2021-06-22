package org.springframework.uac.service.impl.log;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.mapper.log.GzLogMapper;
import org.springframework.uac.model.domain.GzLog;
import org.springframework.uac.model.dto.log.LogListDto;
import org.springframework.uac.model.vo.log.LogVo;
import org.springframework.uac.service.log.GzLogService;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mayato
 * @since 2020-08-10
 */
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzLogServiceImpl extends ServiceImpl<GzLogMapper, GzLog> implements GzLogService {

    @Override
    @Transactional(readOnly = true)
    public PageInfo<LogVo> list(LogListDto logListDto) {
        PageHelper.startPage(logListDto.getStart(), logListDto.getLimit());
        QueryWrapper<GzLog> wrapper = new QWrapper<GzLog>()
                .eq(GzLog.EVENT_TYPE, logListDto.getEventType())
                .eq(GzLog.IS_SUCCESS, logListDto.getIsSuccess())
                .eq(GzLog.EVENT_LEVEL, logListDto.getEventLevel());
        if (StringUtils.isNotBlank(logListDto.getStartTime())) {
            wrapper.ge(GzLog.CREATE_DATE, logListDto.getStartTime());
        }
        if (StringUtils.isNotBlank(logListDto.getEndTime())) {
            wrapper.lt(GzLog.CREATE_DATE, logListDto.getEndTime());
        }

        wrapper.orderBy(true, logListDto.isAsc(), logListDto.getProperty() == null ? com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(GzLog.CREATE_DATE) : logListDto.camelToUnderline());

        List<LogVo> list = this.baseMapper.listGzlogVo(wrapper);
        return new PageInfo<LogVo>(list);
    }

    @Override
    public int getMysqlTableSpace(String tableSchema, String tableName) {
        return this.baseMapper.getMysqlTableSpace(tableSchema, tableName);
    }
}
