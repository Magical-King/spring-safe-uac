package org.springframework.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.mapper.GzSecurityConfigMapper;
import org.springframework.uac.model.domain.GzSecurityConfig;
import org.springframework.uac.model.vo.config.SecurityConfigVo;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.email.GzMailService;
import org.springframework.uac.utils.EmailUtil;
import org.springframework.uac.utils.enums.EmailSubject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@Slf4j
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzSecurityConfigServiceImpl extends ServiceImpl<GzSecurityConfigMapper, GzSecurityConfig> implements GzSecurityConfigService {

    @Autowired
    private GzMailService mailService;


    @Override
    public boolean exists(String tableSchema) {
        return this.getBaseMapper().exists(tableSchema);
    }

    @Transactional(readOnly = true)
    @Override
    public String findConfigStrById(String var1, String var2) {
        GzSecurityConfig var3 = this.getById(var1);
        if (var3 == null) {
            return var2;
        } else {
            return var3.getConfigValue() != null && !"".equals(var3.getConfigValue()) ? var3.getConfigValue() : var2;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public int resourceLimitPercent() {
        return Integer.valueOf(this.findConfigStrById("SYSTEM_RESOURCE_LIMIT_PERCENT", "80"));
    }

    @Transactional(readOnly = true)
    @Override
    public String loginFailCheckperiodHours() {
        return this.findConfigStrById("SYSTEM_LOGIN_FAIL_CHECKPERIOD_HOURS", "1");
    }

    @Transactional(readOnly = true)
    @Override
    public String loginFailLockMins() {
        return this.findConfigStrById("SYSTEM_LOGIN_FAIL_LOCK_MINS", "20");
    }

    @Transactional(readOnly = true)
    @Override
    public String loginFailMaxNum() {
        return this.findConfigStrById("SYSTEM_LOGIN_FAIL_MAX_NUM", "5");
    }

    @Transactional(readOnly = true)
    @Override
    public LocalDateTime loginPsswordValidDays(LocalDateTime time) {
        String var0 = this.findConfigStrById("SYSTEM_LOGIN_PASSWORD_VALID_DAYS", "30");
        return time.plusDays(Long.parseLong(var0));
    }

    @Override
    public String loginPasswordValidDays() {
        return this.findConfigStrById("SYSTEM_LOGIN_PASSWORD_VALID_DAYS", "30");
    }

    @Transactional(readOnly = true)
    @Override
    public boolean reviewFlag() {
        String var0 = this.findConfigStrById("SYSTEM_REVIEW_FLAG", "false");
        return Boolean.valueOf(var0);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean sendEmailFlag() {
        String var0 = this.findConfigStrById("SYSTEM_SEND_EMAIL_ON_FLAG", "false");
        return Boolean.valueOf(var0);
    }

    @Override
    public String password(String var1, String var2) throws SpringSafeException {
        // 密码策略
        GzSecurityConfig var3 = this.getById("SYSTEM_CREATE_RANDOM_PASSWORD_FLAG");
        String var4 = this.findConfigStrById("SYSTEM_PASSWORD_DIGEST_ALGORITHM", "SM3");
        String var5 = this.findConfigStrById("SYSTEM_LOGIN_PASSWORD_TYPE", "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*.?])[a-zA-Z0-9~!@#$%^&*.?]{8,}");
        String var6 = PasswordUtil.get(var5, var3.getConfigValue() == null ? 8 : Integer.parseInt(var3.getConfigValue()), var3.getConfigKey());

        log.info("=====> 用户:{}, 初始密码为: {}, ", var1, var6);
        this.sendEmailPassword(var1, var2, var6, EmailSubject.USER_RESET_PASSWORD);

        return PasswordUtil.digest(var6, var4);
    }

    @Override
    public String password(String var1, String var2, String var3, String var4, String var5) throws SpringSafeException {
        // 密码策略
        String var6 = this.findConfigStrById("SYSTEM_PASSWORD_DIGEST_ALGORITHM", "SM3");
        String var7 = this.findConfigStrById("SYSTEM_LOGIN_PASSWORD_TYPE", "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*.?])[a-zA-Z0-9~!@#$%^&*.?]{8,}");

        if (!var5.matches(var7)) {
            throw new SpringSafeException(12004);
        }

        var4 = PasswordUtil.digest(var4, var6);
        if (!var3.equals(var4)) {
            throw new SpringSafeException(12005);
        }

        log.info("=====> 用户:{}, 修改后的密码为: {}, ", var1, var5);
        this.sendEmailPassword(var1, var2, var5, EmailSubject.USER_MODIFY_PASSWORD);

        return PasswordUtil.digest(var5, var6);
    }

    @Override
    public List<SecurityConfigVo> listNonSys() {
        QueryWrapper<GzSecurityConfig> wrapper = new QWrapper<GzSecurityConfig>().eq(GzSecurityConfig.IS_SYS, false);

        List<SecurityConfigVo> list = this.baseMapper.listNonSys(wrapper);
        return list;
    }

    @Override
    public void updateNonSys(String configId, String configValue, String remake) throws SpringSafeException {

        GzSecurityConfig securityConfig = this.getById(configId);
        if (securityConfig.getIsSys()) {
            throw new SpringSafeException(10008);
        }

        securityConfig.setConfigValue(configValue);
        securityConfig.setRemake(remake);
        this.updateById(securityConfig);
    }

    /**
     * 是否发送邮箱
     *
     * @param var1    用户
     * @param var2    邮箱
     * @param var3    密码
     * @param subject 主题
     * @throws SpringSafeException
     */
    private void sendEmailPassword(String var1, String var2, String var3, EmailSubject subject) throws SpringSafeException {
        if (this.sendEmailFlag()) {
            String text = EmailUtil.text(SecurityUtils.getCurrentLoginName(), var1, EmailUtil.password(SecurityUtils.getCurrentLoginName(), var3), new Date());
            this.mailService.sendAndSave(var2, subject.code(), text);
        }
    }


}
