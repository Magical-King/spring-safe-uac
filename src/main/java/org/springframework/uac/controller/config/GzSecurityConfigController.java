package org.springframework.uac.controller.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.dto.config.SecurityConfigDto;
import org.springframework.uac.model.vo.config.SecurityConfigVo;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@RestController
@IRequestMapping(value = "/gz-security-config", method = RequestMethod.POST, id = 800, parentIds = 1, roleType = 1, component = "Config")
public class GzSecurityConfigController extends BaseController {

    @Autowired
    GzSecurityConfigService gzSecurityConfigService;

    /**
     * 查询配置（非系统配置）
     *
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【查询配置】操作")
    @IRequestMapping(value = "/list", id = 801)
    public Message listNonSys() {
        List<SecurityConfigVo> list = gzSecurityConfigService.listNonSys();
        return this.improve(new Message(list));
    }

    /**
     * 修改配置(非系统配置)
     *
     * @param securityConfigDto
     * @return
     * @throws SpringSafeException
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【修改配置】操作，修改了id为【{0}】的配置", params = "configId")
    @IRequestMapping(value = "/update", id = 802)
    public Message updateNonSys(@Valid SecurityConfigDto securityConfigDto) throws SpringSafeException {
        gzSecurityConfigService
                .updateNonSys(securityConfigDto.getConfigId(), securityConfigDto.getConfigValue(), securityConfigDto.getRemake());
        return this.improve(new Message(Status.SUCCESS));
    }

}
