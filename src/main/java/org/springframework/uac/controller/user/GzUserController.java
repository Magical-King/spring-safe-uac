package org.springframework.uac.controller.user;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.dto.user.ModifyPwdDto;
import org.springframework.uac.model.dto.user.ResetPwdDto;
import org.springframework.uac.model.dto.user.UserDto;
import org.springframework.uac.model.validate.*;
import org.springframework.uac.model.vo.user.UserVo;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@RestController
@IRequestMapping(value = "/gz-user", method = RequestMethod.POST, id = 100, parentIds = 1, roleType = 1, component = "User")
public class GzUserController extends BaseController {

    @Autowired
    GzUserService service;

    /**
     * 用户列表
     * @param user
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【查询用户列表】操作")
    @IRequestMapping(value = "/list", id = 101)
    public Message list(@RequestBody GzUser user) {
        PageInfo<UserVo> page = this.service.findAll(user);
        return this.improve(new Message(page));
    }

    /**
     * 创建用户，生成默认密码
     * @return
     */
    @Log(eventType = LogEventType.CREATE, message = "【创建用户】操作，在公司【{0}】下新增了用户ID为【`{1}`】,用户名称为【`{2}`】,用户邮箱为【`{3}`】的用户账号", params = {"orgId", "userId", "userName", "userEmail"})
    @IRequestMapping(value = "/create", id = 102)
    public Message create(@Validated(value = Create.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.create(user);
        return this.improve(new Message(Status.SUCCESS));
    }


    /**
     * 编辑用户
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【编辑用户】操作，在公司【{0}】下修改了用户ID为【`{1}`】的用户,名称修改为【`{2}`】,用户邮箱修改为【`{3}`】", params = {"orgId", "userId", "userName", "userEmail"})
    @IRequestMapping(value = "/update", name = "修改用户", id = 103)
    public Message update(@Validated(value = Update.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.update(user);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 重置密码
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【重置密码】操作，重置用户ID为【`{0}`】的用户密码", params = {"userIdEncodeds"})
    @IRequestMapping(value = "/reset/pwd", id = 104)
    public Message resetPwd(@Valid @RequestBody ResetPwdDto pwd) throws SpringSafeException {
        this.service.restPwd(pwd.getUserIdEncodeds());
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 修改密码
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【修改密码】操作，修改了用户ID为【`{0}`】的用户密码", params = {"userIdEncoded"})
    @IRequestMapping(value = "/modify/pwd", id = 105)
    public Message modifyPwd(@Valid @RequestBody ModifyPwdDto pwd) throws SpringSafeException {
        this.service.modifyPwd(pwd);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 注销用户
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【注销用户】操作，注销了用户ID为【`{0}`】的用户", params = {"userIdEncodeds"})
    @IRequestMapping(value = "/cancel", id = 106)
    public Message  cancel(@Validated(value = Valid1.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.batchChangeStatus(user.getUserIdEncodeds(), 1);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 停用用户
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【停用用户】操作，停用了用户ID为【`{0}`】的用户", params = {"userIdEncodeds"})
    @IRequestMapping(value = "/stop", id = 107)
    public Message stop(@Validated(value = Valid1.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.batchChangeStatus(user.getUserIdEncodeds(), 2);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 删除用户
     * @return
     */
    @Log(eventType = LogEventType.DELETE, level = 1, message = "【删除用户】操作，删除了用户ID为【`{0}`】的用户", params = {"userIdEncodeds"})
    @IRequestMapping(value = "/delete", id = 108)
    public Message delete(@Validated(value = Delete.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.delete(user.getUserIdEncodeds());
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 给用户分配角色
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【用户分配角色】操作，给用户ID为【`{0}`】的用户分配了角色为【`{1}`】", params = {"userIdEncoded", "roleId"})
    @IRequestMapping(value = "/assign/role", id = 109)
    public Message assignRole(@Validated(value = Valid2.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.assignRole(user);
        return this.improve(new Message(Status.SUCCESS));
    }

    @Log(eventType = LogEventType.PASSWORD, level = 1, message = "【确认密码】操作")
    @IRequestMapping(value = "/check/password", id = 110)
    public Message checkPassword(@Validated(value = Valid3.class) @RequestBody UserDto user) throws SpringSafeException {
        this.service.checkPassword(user.getPassword());
        return this.improve(new Message(Status.SUCCESS));
    }


}
