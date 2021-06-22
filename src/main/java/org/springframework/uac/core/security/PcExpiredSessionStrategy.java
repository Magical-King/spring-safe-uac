package org.springframework.uac.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.safe.utils.message.Status;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Sir.D
 */
@Component
public class PcExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    private MessageSourceUtil sourceUtil;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpUtil.response(event.getResponse(), Status.EXPIRED_SESSION, this.sourceUtil.message(Status.EXPIRED_SESSION.code()));
    }
}
