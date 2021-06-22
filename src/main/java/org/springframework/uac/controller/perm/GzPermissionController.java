package org.springframework.uac.controller.perm;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.dto.perm.CreatePermDto;
import org.springframework.uac.model.dto.perm.DeletePermDto;
import org.springframework.uac.model.dto.perm.PermDto;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Delete;
import org.springframework.uac.model.validate.Update;
import org.springframework.uac.model.vo.perm.PermVo;
import org.springframework.uac.model.vo.role.RolePermTreeVo;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
@RestController
@IRequestMapping(value = "/gz-permission", method = RequestMethod.POST, id = 500, parentIds = 1, roleType = 1, component = "Authority")
public class GzPermissionController extends BaseController {

    @Autowired
    GzPermissionService gzPermissionService;

    /**
     * 根据类型获取权限列表
     *
     * @param permDto
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "查看权限列表")
    @IRequestMapping(value = "/list", id = 501)
    public Message list(@RequestBody PermDto permDto) {
        PageInfo<PermVo> page = gzPermissionService.list(permDto);
        return this.improve(new Message(page));
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @IRequestMapping(value = "/menu", id = 502)
    public Message menu() {
        List<RolePermTreeVo> list = gzPermissionService.selectAllPermTree();
        return this.improve(new Message(list));
    }

    /**
     * 新建权限
     *
     * @param createPermDto
     * @return
     */
    @Log(eventType = LogEventType.CREATE, message = "创建了权限类型为【{0}】的【{1}】", params = {"permType", "permName"})
    @IRequestMapping(value = "/create", id = 503)
    public Message create(@Validated(value = Create.class) @RequestBody CreatePermDto createPermDto) throws SpringSafeException {
        gzPermissionService.create(createPermDto);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 更新权限
     *
     * @param createPermDto
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "更新了id为【{0}】的权限内容", params = "permId")
    @IRequestMapping(value = "/update", id = 504)
    public Message update(@Validated(value = Update.class) @RequestBody CreatePermDto createPermDto) throws SpringSafeException {
        gzPermissionService.update(createPermDto);
        return this.improve(new Message(Status.SUCCESS));

    }

    /**
     * 删除权限
     *
     * @param deletePermDto
     * @return
     */
    @Log(eventType = LogEventType.DELETE, message = "删除了id为【{0}】的权限内容", params = "permIds")
    @IRequestMapping(value = "/delete", id = 505)
    public Message delete(@Validated(value = Delete.class) @RequestBody DeletePermDto deletePermDto) throws SpringSafeException {
        gzPermissionService.delete(Arrays.asList(deletePermDto.getPermIds()));
        return this.improve(new Message(Status.SUCCESS));


    }


}
