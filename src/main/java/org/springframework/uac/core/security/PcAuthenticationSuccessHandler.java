package org.springframework.uac.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.safe.utils.message.Status;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 默认的成功管理器
 * @author Sir.D
 */
@Component("pcAuthenticationSuccessHandler")
public class PcAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private MessageSourceUtil sourceUtil;

    @Autowired
    private GzSecurityConfigService configService;

    @Autowired
    private GzUserService userService;

    @Log(eventCategory = "100", eventType = LogEventType.LOGIN, message = "【用户登录】操作")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("====> 登录成功 !");

        GzUser user = this.userService.getById(SecurityUtils.getCurrentLoginName());
        this.userService.update(user.getUserId());

        if (null == user.getUserLastPwdChange()) {
            response.setHeader("FORCE_EDIT_FLAG", "true");
        } else {
            LocalDateTime loginPsswordValidDays = this.configService.loginPsswordValidDays(user.getUserLastPwdChange());
            response.setHeader("VALID_DAYS_FLAG", String.valueOf(loginPsswordValidDays.isBefore(LocalDateTime.now())));
            response.setHeader("VALID_DAYS", this.configService.loginPasswordValidDays());
        }

        HttpUtil.response(response, Status.SUCCESS, sourceUtil.message(10000));
    }
}
