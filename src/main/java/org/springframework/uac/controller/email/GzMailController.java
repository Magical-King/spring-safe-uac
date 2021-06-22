package org.springframework.uac.controller.email;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.safe.utils.message.Message;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.domain.GzMail;
import org.springframework.uac.service.email.GzMailService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-04
 */
@RestController
//@IRequestMapping(value = "/gz-mail", method = RequestMethod.POST, id = 600 , parentIds = 1, roleType = 1)
@RequestMapping(value = "/gz-mail", method = RequestMethod.POST)
public class GzMailController extends BaseController {

    @Autowired
    GzMailService service;

    /**
     * 用户列表
     * @param mail
     * @return
     */
    @IRequestMapping(value = "/list", id = 601)
    public Message list(@RequestBody GzMail mail) {
        PageInfo<GzMail> page = this.service.findAll(mail);
        return this.improve(new Message(page));
    }

}
