package org.springframework.uac.service.impl.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.domain.GzUserBack;
import org.springframework.uac.model.domain.GzUserModified;
import org.springframework.uac.mapper.user.GzUserModifiedMapper;
import org.springframework.uac.service.email.GzMailService;
import org.springframework.uac.service.user.GzUserBackService;
import org.springframework.uac.service.user.GzUserModifiedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.uac.utils.EmailUtil;
import org.springframework.uac.utils.enums.EmailSubject;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-07
 */
@Service
@Transactional(rollbackFor= SpringSafeException.class)
public class GzUserModifiedServiceImpl extends ServiceImpl<GzUserModifiedMapper, GzUserModified> implements GzUserModifiedService {

    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String MODIFIED = "MODIFIED";

    @Autowired
    private GzUserService userService;

    @Autowired
    private GzUserBackService userBackService;

    @Autowired
    private GzMailService mailService;

    @Override
    @Transactional(rollbackFor= Exception.class, noRollbackFor = SpringSafeException.class)
    public void handleDataIntegrity() throws SpringSafeException {
        List<GzUserModified> list = this.list();
        for (GzUserModified userModified : list) {
            modify(userModified);
            this.removeById(userModified.getId());
        }
    }

    private void modify(GzUserModified var1) throws SpringSafeException {
        try {
            if (ADD.equalsIgnoreCase(var1.getModifiedType())) {
                addValidate(var1.getUserId());
            } else if (DELETE.equalsIgnoreCase(var1.getModifiedType())) {
                deleteValidate(var1.getUserId());
            } else if (MODIFIED.equalsIgnoreCase(var1.getModifiedType())) {
                modifyValidate(var1.getUserId());
            }
        } catch (Exception e) {
            throw new SpringSafeException(10012);
        }
    }

    /**
     * 修改时验证逻辑
     * @param var1
     * @throws SpringSafeException
     */
    private void modifyValidate(String var1) throws SpringSafeException {
        GzUser var2 = this.userService.getById(var1);
        if (null != var2) {
            if (!var2.generateUserSalt().equals(var2.getUserSalt())) {
                GzUserBack var3 = this.userBackService.getById(var1);
                GzUser var4 = new ModelMapper().map(var3, GzUser.class);
                this.userService.updateById(var4);

                // 发送邮箱通知系统管理员
                GzUser var5 = this.userService.getById("sys_admin");
                String text = EmailUtil.text(null, var5.getUserName(), EmailUtil.modifyHandleDataIntegrity(var4.getUserId()), new Date());
                this.mailService.sendAndSave(var5.getUserEmail(), EmailSubject.HANDLE_DATA_INTEGRITY.code(),text);
            } else {
                this.userBackService.saveOrUpdate(var2);
            }
        }
    }

    /**
     * 删除时验证逻辑
     * @param var1
     * @throws SpringSafeException
     */
    private void deleteValidate(String var1) throws SpringSafeException {
        GzUserBack var2 = this.userBackService.getById(var1);
        if (null != var2) {
            GzUser user = new ModelMapper().map(var2, GzUser.class);
            this.userService.saveOrUpdate(user);

            // 发送邮箱
            GzUser var5 = this.userService.getById("sys_admin");
            String text = EmailUtil.text(null, var5.getUserName(), EmailUtil.deleteHandleDataIntegrity(var2.getUserId()), new Date());
            this.mailService.sendAndSave(var5.getUserEmail(), EmailSubject.HANDLE_DATA_INTEGRITY.code(),text);
        }
    }

    /**
     * 添加时验证逻辑
     * @param var1
     * @throws SpringSafeException
     */
    private void addValidate(String var1) throws SpringSafeException {
        GzUser var2 = this.userService.getById(var1);
        if (null != var2) {
            if (!var2.generateUserSalt().equals(var2.getUserSalt())) {
                this.userService.removeById(var1);

                // 发送邮箱
                GzUser var5 = this.userService.getById("sys_admin");
                String text = EmailUtil.text(null, var5.getUserName(), EmailUtil.addHandleDataIntegrity(var2.getUserId()), new Date());
                this.mailService.sendAndSave(var5.getUserEmail(), EmailSubject.HANDLE_DATA_INTEGRITY.code(),text);
            } else {
                this.userBackService.saveOrUpdate(var2);
            }
        }
    }




}
