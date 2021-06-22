package org.springframework.uac.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.core.component.InitPermission;
import org.springframework.uac.utils.FileUtil;
import org.springframework.uac.utils.MessageSourceUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Sir.D
 */
@RestController
@RequestMapping(value = "/auth/internationalise")
public class MessageSourceController extends BaseController {

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @Autowired
    private InitPermission initPermission;

    @RequestMapping(value = "/{language}/{country}")
    public Message language(@PathVariable(value = "language") String language, @PathVariable(value = "country") String country) {
        try {
            File[] files = FileUtil.getFiles("i18n/*_" + language + "_" + country + ".properties");
            if (null == files) {
                return this.improve(new Message(Status.PARAM_INVALID));
            }

            messageSourceUtil.setLanguage(new Locale(language, country));
            initPermission.init();
            return this.improve(new Message(Status.SUCCESS));
        } catch (IOException e) {
            e.printStackTrace();
            return this.improve(new Message(Status.PARAM_INVALID));
        }

    }

}
