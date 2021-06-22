package org.springframework.uac.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.safe.utils.message.Status;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sir.D
 */
@Slf4j
@Component("PcAuthenticationEntryPointHandler")
public class PcAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Autowired
    private MessageSourceUtil sourceUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // 退出时需要进行的操作，比如：登陆时间统计，定时器开关
        log.info("===> 用户未登录，请登录后再操作！");
        HttpUtil.response(response, Status.ACTION_INVALID, sourceUtil.message(10020) );

    }
}
