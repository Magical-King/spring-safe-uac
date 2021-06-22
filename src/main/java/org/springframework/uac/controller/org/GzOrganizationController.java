package org.springframework.uac.controller.org;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.domain.GzOrganization;
import org.springframework.uac.model.dto.org.GzOrganizationDto;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Update;
import org.springframework.uac.model.validate.Valid1;
import org.springframework.uac.service.org.GzOrganizationService;
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
 * @since 2020-07-30
 */
@RestController
@IRequestMapping(value = "/gz-organization",method = RequestMethod.POST, id = 300 , parentIds = 1, roleType = 1, component = "Company")
public class GzOrganizationController extends BaseController {

    @Autowired
    GzOrganizationService service;


    /**
     * 公司列表
     * @param org
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【查询公司列表】操作")
    @IRequestMapping(value = "/list", id = 301)
    public Message list(@RequestBody GzOrganization org) {
        PageInfo<GzOrganization> page = this.service.findAll(org);
        return this.improve(new Message(page));
    }

    /**
     * 创建公司
     * @return
     */
    @Log(eventType = LogEventType.CREATE, message = "【创建公司】操作，添加了ID为【`{0}`】, 名称为【`{1}`】的公司", params = {"orgId", "orgName"})
    @IRequestMapping(value = "/create", id = 302)
    public Message create(@Validated(value = Create.class) @RequestBody GzOrganization org) throws SpringSafeException {
        this.service.create(org);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 编辑公司
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【编辑公司】操作，修改了ID为【`{0}`】的公司", params = {"orgId"})
    @IRequestMapping(value = "/update", id = 303)
    public Message update(@Validated(value = Update.class) @RequestBody GzOrganization org) throws SpringSafeException {
        this.service.update(org);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 删除公司集合
     * @return
     */
    @Log(eventType = LogEventType.DELETE, message = "【删除公司】操作，删除了用户ID为【`{0}`】的公司", params = {"ids"})
    @IRequestMapping(value = "/delete", id = 304)
    public Message update(@Validated(value = Valid1.class) @RequestBody GzOrganizationDto org) throws SpringSafeException {
        this.service.delete(org.getIds());
        return this.improve(new Message(Status.SUCCESS));
    }

}
