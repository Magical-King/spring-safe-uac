package org.springframework.uac.controller.config;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.core.security.handleipwhite.PcHandleIpWhite;
import org.springframework.uac.model.domain.GzIpWhite;
import org.springframework.uac.model.dto.config.IpWhiteDto;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Delete;
import org.springframework.uac.model.validate.Update;
import org.springframework.uac.service.GzIpWhiteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sir.D
 * @since 2020-11-30
 */
@RestController
@IRequestMapping(value = "/gz-ip-white", method = RequestMethod.POST, id = 900, parentIds = 1, roleType = 1, component = "Whitelist")
public class GzIpWhiteController extends BaseController {

    @Autowired
    GzIpWhiteService service;
    /**
     * 用户列表
     * @param ipWhite
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【查询白名单列表】操作")
    @IRequestMapping(value = "/list", id = 901)
    public Message list(@RequestBody GzIpWhite ipWhite) {
        PageInfo<GzIpWhite> page = this.service.findAll(ipWhite);
        return this.improve(new Message(page));
    }

    /**
     * 创建白名单
     * @return
     */
    @Log(eventType = LogEventType.CREATE, message = "【创建白名单】操作，新增了白名单区间为【`{0}`】", params = {"ipSegment"})
    @IRequestMapping(value = "/create", id = 902)
    public Message create(@Validated(value = Create.class) @RequestBody IpWhiteDto ipWhite) throws SpringSafeException {
        PcHandleIpWhite.clear();
        this.service.create(ipWhite);
        return this.improve(new Message(Status.SUCCESS));
    }


    /**
     * 编辑白名单
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【编辑白名单】操作，修改了ID为【`{1}`】的白名单,白名单区间修改为【`{2}`】", params = {"id", "ipSegment"})
    @IRequestMapping(value = "/update", id = 903)
    public Message update(@Validated(value = Update.class) @RequestBody IpWhiteDto ipWhite) throws SpringSafeException {
        PcHandleIpWhite.clear();
        this.service.update(ipWhite);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 删除白名单
     * @return
     */
    @Log(eventType = LogEventType.DELETE, level = 1, message = "【删除白名单】操作，删除了ID为【`{0}`】的白名单", params = {"ids"})
    @IRequestMapping(value = "/delete", id = 904)
    public Message delete(@Validated(value = Delete.class) @RequestBody IpWhiteDto ipWhite) throws SpringSafeException {
        PcHandleIpWhite.clear();
        this.service.delete(ipWhite.getIds());
        return this.improve(new Message(Status.SUCCESS));
    }
}
