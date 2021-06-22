package org.springframework.uac.service.email;

import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzMail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.vo.user.UserVo;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-04
 */
public interface GzMailService extends IService<GzMail> {

    /**
     * 根据条件筛选
     * @param mail
     * @return
     * @throws SpringSafeException
     */
    PageInfo<GzMail> findAll(GzMail mail);

    /**
     * 数据库留档
     * @param sender
     * @param recipient
     * @param subject
     * @param text
     * @return
     * @throws SpringSafeException
     */
    Integer create(String sender, String recipient, String subject, String text) throws SpringSafeException;

    /**
     * 修改状态
     * @param id
     * @param status
     * @param time
     * @throws SpringSafeException
     */
    void changeStatus(int id, int status, LocalDateTime time) throws SpringSafeException;

    /**
     * 发送邮箱
     * @param id
     * @param email
     * @param subject
     * @param text
     * @return
     * @throws SpringSafeException
     */
    boolean send(int id, String email, String subject, String text) throws SpringSafeException;

    /**
     * 发送邮箱并数据库留档
     * @param email
     * @param subject
     * @param text
     * @throws SpringSafeException
     */
    void sendAndSave(String email, String subject, String text) throws SpringSafeException;
}
