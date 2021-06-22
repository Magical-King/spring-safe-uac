package org.springframework.uac.service.impl.email;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Service;
import org.springframework.uac.mapper.email.GzMailMapper;
import org.springframework.uac.model.domain.GzMail;
import org.springframework.uac.service.email.GzMailService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-04
 */
@Service
public class GzMailServiceImpl extends ServiceImpl<GzMailMapper, GzMail> implements GzMailService {

    @Autowired
    GzrobotProperties config;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public PageInfo<GzMail> findAll(GzMail mail) {
        PageHelper.startPage(mail.getStart(), mail.getLimit());

        QWrapper<GzMail> queryWrapper = new QWrapper();
        queryWrapper.eq(GzMail.MAIL_SENDER, mail.getMailSender())
                .eq(GzMail.MAIL_RECIPIENT, mail.getMailRecipient())
                .eq(GzMail.MAIL_STATUS, mail.getMailStatus())
                .orderBy(true, mail.isAsc(), mail.getProperty() == null ? com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(GzMail.ID) : mail.camelToUnderline());

        List<GzMail> gzUsers = this.list(queryWrapper);
        return new PageInfo<GzMail>(gzUsers);
    }

    @Override
    public Integer create(String sender, String recipient, String subject, String text) throws SpringSafeException {
        if (StringUtils.isBlank(sender)) {
            throw new SpringSafeException(30001);
        }
        if (StringUtils.isBlank(recipient)) {
            throw new SpringSafeException(30002);
        }
        if (StringUtils.isBlank(subject)) {
            throw new SpringSafeException(30003);
        }
        if (StringUtils.isBlank(text)) {
            throw new SpringSafeException(30004);
        }

        GzMail mail = new GzMail();
        mail.setMailSender(sender);
        mail.setMailRecipient(recipient);
        mail.setMailSubject(subject);
        mail.setMailContentText(text);
        mail.setCreateDate(LocalDateTime.now());

        if (!this.save(mail)) {
            throw new SpringSafeException(30000);
        }

        return mail.getId();
    }

    @Override
    public void changeStatus(int id, int status, LocalDateTime time) throws SpringSafeException {
        GzMail mail = new GzMail();
        mail.setId(id);
        mail.setMailStatus(status);
        mail.setSuccessDate(time);
        if (!this.updateById(mail)) {
            throw new SpringSafeException(30000);
        }
    }

    @Override
    public boolean send(int id, String email, String subject, String text) throws SpringSafeException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(config.getEmail().getSender().getAccount());
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            log.error("===> 发送异常" + e.getMessage());
            this.changeStatus(id, 2, null);
            return false;
        }
    }

    @Override
    public void sendAndSave(String email, String subject, String text) throws SpringSafeException {
        Integer id = this.create(config.getEmail().getSender().getAccount(), email, subject, text);
        if (this.send(id, email, subject, text)) {
            this.changeStatus(id, 1, LocalDateTime.now());
        } else {
            throw new SpringSafeException(30005);
        }
    }
}
