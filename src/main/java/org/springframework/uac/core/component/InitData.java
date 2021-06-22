package org.springframework.uac.core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * @author Sir.D
 */
@Configuration
public class InitData {

    @Autowired
    private InitDataBase initDataBase;

    @Autowired
    private InitOrganization initOrganization;

    @Autowired
    private InitUser initUser;

    @Autowired
    private InitPermission initPermission;

    @Autowired
    private InitRole initRole;

    @Autowired
    private GzrobotProperties config;

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @PostConstruct
    private void init() {
        messageSourceUtil.setLanguage(new Locale(config.getInternationalise().getLanguage(), config.getInternationalise().getCountry()));

        this.initDataBase.init();
        this.initOrganization.init();
        this.initUser.init();
        this.initPermission.init();
        this.initRole.init();
    }

}
