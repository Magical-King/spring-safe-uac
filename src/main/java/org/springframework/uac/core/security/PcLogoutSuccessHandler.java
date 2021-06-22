package org.springframework.uac.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.safe.utils.message.Status;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sir.D
 */
@Component("pcLogoutSuccessHandler")
@Slf4j
public class PcLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private MessageSourceUtil sourceUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 退出时需要进行的操作，比如：登陆时间统计，定时器开关
        log.info("===> 用户已成功退出！");
        HttpUtil.response(response, Status.SUCCESS, sourceUtil.message(10022) );
    }
}
