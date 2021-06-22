package org.springframework.uac.core.security.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.security.validate.code.email.EmailCodeSender;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.safe.server.Sm4PasswordEncoder;
import org.springframework.safe.utils.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.email.GzMailService;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.uac.utils.EmailUtil;
import org.springframework.uac.utils.MessageSourceUtil;
import org.springframework.uac.utils.enums.EmailSubject;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 邮箱发送器
 * @author Sir.D
 */
@Slf4j
@Component
@Transactional(rollbackFor= SpringSafeException.class)
public class PcEmailCodeSender implements EmailCodeSender {

    @Autowired
    private GzUserService userService;

    @Autowired
    private GzMailService mailService;

    @Autowired
    private GzSecurityConfigService configService;

    @Autowired
    private GzrobotProperties config;

    @Autowired
    private MessageSourceUtil sourceUtil;

    private static Long mils = 1000L;

    @Transactional(rollbackFor= SpringSafeException.class, noRollbackFor = ValidateCodeException.class )
    @Override
    public void send(String username, String password, String code) throws SpringSafeException {
        if (!configService.sendEmailFlag()) {
            return;
        }

        GzUser user = this.userService.getById(username);
        if (null == user) {
            throw new ValidateCodeException(sourceUtil.message(14001));
        }

        if (new Sm4PasswordEncoder().matches(password, user.getUserPassword())) {
            if ( null != user.getUserLastSendEmailDate()) {
                if (DateUtil.getTimeMillis() <= DateUtil.getTimeMillis(user.getUserLastSendEmailDate()) + config.getEmail().getService().getSleepSeconds() * mils) {
                    throw new ValidateCodeException(sourceUtil.message(9903));
                }
            }

            user.setUserLastSendEmailDate(LocalDateTime.now());
            this.userService.updateById(user);

            try {
                String text = EmailUtil.text(null, user.getUserName(), EmailUtil.dynamicPassword(code, config.getEmail().getExpire()), new Date());
                this.mailService.sendAndSave(user.getUserEmail(), EmailSubject.USER_DYNAMIC_PASSWORD.code(),text);
            } catch (SpringSafeException e) {
                throw new ValidateCodeException(sourceUtil.message(e.get().getCode()));
            }
        } else {
            throw new ValidateCodeException(sourceUtil.message(11001));
        }
    }





























}
