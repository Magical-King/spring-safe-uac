package org.springframework.uac.core.security.handledataintegrity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.safe.security.validate.dataintegrity.HandleDataIntegrity;
import org.springframework.stereotype.Component;
import org.springframework.uac.service.user.GzUserModifiedService;
import org.springframework.uac.service.user.GzUserService;

/**
 * @author Sir.D
 */
@Slf4j
@Component
public class PcHandleDataIntegrity implements HandleDataIntegrity {

    @Autowired
    private GzUserService userService;

    @Autowired
    private GzUserModifiedService modifiedService;

    @Override
    public void handleDataIntegrity(String userId) {
//        if (!this.userService.checkDataIntegrity(userId)) {
            try {
                this.modifiedService.handleDataIntegrity();
            } catch (SpringSafeException e) {
                throw new ValidateCodeException("10012");
            }
//        }
    }
}
