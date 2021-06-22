package org.springframework.uac.controller.log;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.safe.utils.message.Message;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.dto.log.LogListDto;
import org.springframework.uac.model.vo.log.LogVo;
import org.springframework.uac.service.log.GzLogService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mayato
 * @since 2020-08-10
 */
@RestController
@IRequestMapping(value = "/gz-log",method = RequestMethod.POST, id = 700 , parentIds = 2, roleType = 2, component = "Log")
public class GzLogController extends BaseController {

    @Autowired
    GzLogService gzLogService;


    /**
     * 获取日志列表
     * @param logListDto
     * @return
     */
    @IRequestMapping(value = "/list", id = 701)
    public Message list(@Valid @RequestBody LogListDto logListDto) {
        PageInfo<LogVo> page = gzLogService.list(logListDto);
        return this.improve(new Message(page));
    }

}
