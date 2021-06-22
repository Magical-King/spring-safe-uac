package org.springframework.uac.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.safe.utils.message.Status;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的
 * @author Sir.D
 */
@Slf4j
@Configuration
public class PcAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private MessageSourceUtil sourceUtil;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        log.info("===> 异常权限: {}", e.getMessage());
        HttpUtil.response(response, Status.FORBIDDEN, this.sourceUtil.message(Status.FORBIDDEN.code()));
    }
}
