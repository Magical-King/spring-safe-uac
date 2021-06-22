package org.springframework.uac.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.Sm2Util;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.core.security.SecurityUser;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.model.dto.user.ModifyPwdDto;
import org.springframework.uac.model.vo.perm.MenuVo;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mayato
 */
@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
public class GzAuthController extends BaseController {
    @Autowired
    GzPermissionService gzPermissionService;

    @Autowired
    GzUserService service;

    @RequestMapping(value = "/menu/list")
    public Message list() throws SpringSafeException {
        SecurityUser currentUser = SecurityUtils.getCurrentUser();
        if (null == currentUser) {
            throw new SpringSafeException(10020);
        }

        List<MenuVo> menuVoList = gzPermissionService.listMenu(currentUser.getUserId());
        return this.improve(new Message(menuVoList));
    }

    @RequestMapping(value = "/user/encodeId")
    public Message encodeId() throws SpringSafeException {
        SecurityUser currentUser = SecurityUtils.getCurrentUser();
        if (null == currentUser) {
            throw new SpringSafeException(10020);
        }

        return this.improve(new Message(Status.SUCCESS, Sm2Util.encrypt(currentUser.getUserId())));
    }

    /**
     * 修改密码
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, level = 1, message = "【修改密码】操作，修改了用户ID为【`{0}`】的用户密码", params = {"userIdEncoded"})
    @IRequestMapping(value = "/modify/pwd", id = 105)
    public Message modifyPwd(@Valid @RequestBody ModifyPwdDto pwd) throws SpringSafeException {
        SecurityUser currentUser = SecurityUtils.getCurrentUser();
        if (null == currentUser) {
            throw new SpringSafeException(10020);
        }
        pwd.setUserIdEncoded(Sm2Util.encrypt(currentUser.getUserId()));

        this.service.modifyPwd(pwd);
        return this.improve(new Message(Status.SUCCESS));
    }

}
