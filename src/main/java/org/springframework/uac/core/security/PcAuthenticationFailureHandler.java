package org.springframework.uac.core.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.safe.utils.message.Status;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 默认的失败管理器
 * @author Sir.D
 */
@Component("pcAuthenticationFailureHandler")
public class PcAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private GzUserService userService;

    @Autowired
    private MessageSourceUtil sourceUtil;

    Pattern pattern = Pattern.compile("[0-9]*");

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        this.logger.info("===> 登录失败 !");

        try {
            String notKeep = "9901";
            if (!notKeep.equals(e.getMessage())) {
                String username = request.getParameter("username");
                if (StringUtils.isNotBlank(username)) {
                    this.userService.updateUserLoginFailStatus(username);
                }
            }
        } catch (SpringSafeException var9) {
            HttpUtil.response(response, Status.FAILED, this.sourceUtil.message(var9.get().getCode()));
            return;
        }

        String message;
        if (StringUtils.isNumeric(e.getMessage())) {
            message = this.sourceUtil.message(Integer.parseInt(e.getMessage()));
        } else {
            message = e.getMessage();
        }

        HttpUtil.response(response, Status.FAILED, message);
    }

}
