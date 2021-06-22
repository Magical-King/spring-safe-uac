package org.springframework.uac.service.impl.user;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.common.mybatis.UWrapper;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.DateUtil;
import org.springframework.safe.utils.Sm2Util;
import org.springframework.safe.utils.Sm4Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.core.security.PcGrantedAuthority;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.mapper.user.GzUserMapper;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.dto.user.ModifyPwdDto;
import org.springframework.uac.model.dto.user.UserDto;
import org.springframework.uac.model.vo.org.OrganizationVo;
import org.springframework.uac.model.vo.user.UserVo;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.email.GzMailService;
import org.springframework.uac.service.org.GzOrganizationService;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.review.GzReviewService;
import org.springframework.uac.service.role.GzRoleService;
import org.springframework.uac.service.role.GzUserRoleService;
import org.springframework.uac.service.user.GzUserBackService;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.uac.utils.EmailUtil;
import org.springframework.uac.utils.enums.EmailSubject;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@Slf4j
@Service
@Transactional(rollbackFor=SpringSafeException.class)
public class GzUserServiceImpl extends ServiceImpl<GzUserMapper, GzUser> implements GzUserService {

    @Autowired
    GzUserBackService userBackService;

    @Autowired
    GzSecurityConfigService configService;

    @Autowired
    GzOrganizationService orgService;

    @Autowired
    GzUserRoleService userRoleService;

    @Autowired
    GzRoleService gzRoleService;

    @Autowired
    GzPermissionService permissionService;

    @Autowired
    GzReviewService reviewService;

    @Autowired
    private GzMailService mailService;

    @Override
    @Transactional(readOnly = true)
    public List<String> findInitUser() {
        QWrapper<GzUser> qWrapper = new QWrapper<>();
        qWrapper.select(GzUser.USER_ID)
                .in(GzUser.USER_ID, "sys_admin", "sys_audit", "sys_review", "operator");
        return this.listObjs(qWrapper, String::valueOf);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<UserVo> findAll(GzUser user) {
        PageHelper.startPage(user.getStart(), user.getLimit());

        QWrapper<GzUser> queryWrapper = new QWrapper();
        queryWrapper.eq("u."+GzUser.USER_ID, user.getUserId())
                .eq(GzUser.USER_ORG_ID, user.getUserOrgId())
                .eq(GzUser.USER_STATUS, user.getUserStatus())
                .eq(GzUser.USER_MOBILE, user.getUserMobile())
                .eq(GzUser.USER_EMAIL, user.getUserEmail())
                .eq(GzUser.USER_IS_TEMPORARY, user.getUserIsTemporary())
                .eq(GzUser.USER_IDENTITY_NUMBER, user.getUserIdentityNumber())
                .eq(OrganizationVo.ORG_IS_INVALID,1)
                .like(GzUser.USER_NAME, user.getUserName())
                .orderBy(true, user.isAsc(), user.getProperty() == null ? "u."+StringUtils.camelToUnderline(GzUser.USER_ID) : user.camelToUnderline());

        List<UserVo> gzUsers = this.baseMapper.findAll(queryWrapper);
        return new PageInfo<UserVo>(gzUsers);
    }


    @Override
    public void create(UserDto user) throws SpringSafeException {
        if (!this.orgService.exists(user.getOrgId())) {
            throw new SpringSafeException(14001);
        }

        if (this.baseMapper.exists(user.getUserId())) {
            throw new SpringSafeException(11005);
        }

        GzUser var3 = new ModelMapper().map(user, GzUser.class);
        var3.encodedUserPassword(this.configService.password(user.getUserId(), user.getUserEmail()));
        var3 = newUser(var3);

        if (!user.isUserIsTemporary()) {
            var3.setUserEndDate(null);
        }

        if (!this.save(var3)) {
            throw new SpringSafeException(10001);
        }

    }

    @Override
    public void update(UserDto user) throws SpringSafeException {
        try {
            if (!this.orgService.exists(user.getOrgId())) {
                throw new SpringSafeException(14001);
            }

            user.setUserId(Sm2Util.decrypt(user.getUserIdEncoded()));

            // 不存在则返回报警
            if (!this.baseMapper.exists(user.getUserId())) {
                throw new SpringSafeException(12000);
            }

            GzUser var4 = this.getById(user.getUserId());
            GzUser var3 = new ModelMapper().map(user, GzUser.class);
            var3.setUserPassword(var4.getUserPassword());
            var3 = newUser(var3);

            if (!user.isUserIsTemporary()) {
                var3.setUserEndDate(null);
            }

            if (!this.updateById(var3)) {
                throw new SpringSafeException(10001);
            }
        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }
    }

    @Override
    public void update(String userId) {
        UWrapper<GzUser> uWrapper = new UWrapper<>();
        uWrapper.eq(GzUser.USER_ID, userId);
        uWrapper.set(GzUser.USER_STATUS, 0);
        uWrapper.set(GzUser.USER_LOGIN_FAILURE_NUM, 0);
        uWrapper.set(GzUser.USER_FIRST_FAILURE_DATE, null);
        uWrapper.set(GzUser.USER_LOGIN_FAILURE_DATE, null);
        uWrapper.set(GzUser.USER_LOCK_DATE, null);
        this.update(uWrapper);
    }

    @Override
    public void restPwd(String[] userIdEncodeds) throws SpringSafeException {
        try {
            LocalDateTime changePwdDay = this.configService.loginPsswordValidDays(LocalDateTime.now());
            for (int i = 0; i < userIdEncodeds.length; i++) {
                userIdEncodeds[i] = Sm2Util.decrypt(userIdEncodeds[i]);
                GzUser var3 = this.getById(userIdEncodeds[i]);
                if (null == var3) {
                    break;
                }

                var3.encodedUserPassword(this.configService.password(var3.getUserId(), var3.getUserEmail()));
                var3.setUserLastPwdChange(changePwdDay);
                var3 = newUser(var3);

                if (!this.updateById(var3)) {
                    throw new SpringSafeException(10001);
                }
            }
        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }
    }

    @Override
    public void modifyPwd(ModifyPwdDto pwd) throws SpringSafeException {
        try {
            String var0 = Sm2Util.decrypt(pwd.getUserIdEncoded());
            pwd.setUserIdEncoded(var0);

            if (pwd.getCurrentPwd().equals(pwd.getNewPassword())) {
                throw new SpringSafeException(12002);
            }

            if (!pwd.getNewPassword().equals(pwd.getConfirmPwd())) {
                throw new SpringSafeException(12003);
            }

            if (!this.baseMapper.exists(var0)) {
                throw new SpringSafeException(12000);
            }

            GzUser user = this.getById(var0);
            // 判断传入原密码是否为数据库密码
            String modifyPwd = this.configService.password(user.getUserId(), user.getUserEmail(), user.decryptUserPassword(), pwd.getCurrentPwd(), pwd.getConfirmPwd());
            user.encodedUserPassword(modifyPwd);

            LocalDateTime changePwdDay = this.configService.loginPsswordValidDays(LocalDateTime.now());
            user.setUserLastPwdChange(changePwdDay);
            user = newUser(user);

            if (!this.updateById(user)) {
                throw new SpringSafeException(10001);
            }

        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }
    }

    @Override
    public void batchChangeStatus(String[] userIdEncodeds, int status) throws SpringSafeException {
        try {
            for (int i = 0; i < userIdEncodeds.length; i++) {
                userIdEncodeds[i] = Sm2Util.decrypt(userIdEncodeds[i]);
            }

            GzUser user = new GzUser();
            user.setUserStatus(status);
            UWrapper<GzUser> uWrapper = new UWrapper<>();
            uWrapper.in(GzUser.USER_ID, userIdEncodeds);

            if (!this.update(user,uWrapper)) {
                throw new SpringSafeException(10001);
            }

        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }

    }

    @Override
    public void changeStatus(String userIdEncoded, int status) throws SpringSafeException {
        try {
            userIdEncoded = Sm2Util.decrypt(userIdEncoded);
            GzUser user = new GzUser();
            user.setUserId(userIdEncoded);
            user.setStart(status);

            if (!this.updateById(user)) {
                throw new SpringSafeException(10001);
            }
        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }
    }

    @Override
    public void delete(String[] userIdEncodeds) throws SpringSafeException {
        try {
            for (int i = 0; i < userIdEncodeds.length; i++) {
                userIdEncodeds[i] = Sm2Util.decrypt(userIdEncodeds[i]);
            }

            if (! (this.removeByIds(Arrays.asList(userIdEncodeds)) && this.userBackService.deleteByIds(Arrays.asList(userIdEncodeds)) ) ) {
                throw new SpringSafeException(10001);
            }
        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }
    }

    @Override
    public void assignRole(UserDto user) throws SpringSafeException {
        try {
            String userIdEncoded = Sm2Util.decrypt(user.getUserIdEncoded());
            user.setUserIdEncoded(userIdEncoded);

            // 判断是否开启审计功能
            if (this.configService.reviewFlag()) {
                this.reviewService.create(userIdEncoded, user.getRoleId(), 1, org.apache.commons.lang3.StringUtils.isNotBlank(user.getRoleId()) ? 20000 : 20001);
            }  else {
                this.userRoleService.assignRole(userIdEncoded, user.getRoleId());
            }
        } catch (InvalidCipherTextException e) {
            throw new SpringSafeException(12001);
        }
    }

    @Log(eventCategory = "100", eventType = LogEventType.LOGIN, message = "用户【{0}】执行了【用户登录】操作", params = {"userId"})
    @Transactional(rollbackFor = Exception.class, noRollbackFor=SpringSafeException.class)
    @Override
    public boolean updateUserLoginFailStatus(String userId) throws SpringSafeException {
        GzUser user = this.getById(userId);
        if (null == user) {
            throw new SpringSafeException(11001);
        }

        // 过期时间
        Long hour = Long.valueOf(this.configService.loginFailCheckperiodHours()) * 3600L * 1000L;
        // 锁定分钟
        Long mins = Long.valueOf(this.configService.loginFailLockMins()) * 60L * 1000L;
        // 失败最大次数
        int max = Integer.valueOf(this.configService.loginFailMaxNum());

        long millis = DateUtil.getTimeMillis();

        if (user.getUserLockDate() != null && millis <= DateUtil.getTimeMillis( user.getUserLockDate()) && user.getUserLoginFailureNum() >= max) {
            throw new SpringSafeException(12006);
        }

        if (user.getUserFirstFailureDate() == null || DateUtil.getTimeMillis() > DateUtil.getTimeMillis(user.getUserFirstFailureDate()) + hour) {
            user.setUserFirstFailureDate(DateUtil.getLocalDateTime(millis));
            user.setUserLoginFailureNum(0);
        }

        boolean flag = false;
        if (user.getUserLoginFailureNum() + 1 >= max) {
            flag = true;
            user.setUserStatus(4);
            user.setUserLoginFailureNum(user.getUserLoginFailureNum() + 1);
            user.setUserLoginFailureDate(DateUtil.getLocalDateTime(millis));
            user.setUserLockDate(DateUtil.getLocalDateTime(millis + mins));
            this.updateById(user);
        } else {
            user.setUserLoginFailureNum(user.getUserLoginFailureNum() + 1);
            user.setUserLoginFailureDate(DateUtil.getLocalDateTime(millis));
            this.updateById(user);
        }

        if (this.configService.sendEmailFlag() && user.getUserLoginFailureNum() >= max) {
            String text = EmailUtil.text(null, user.getUserName(), EmailUtil.userLock(user.getUserId(), user.getUserName(), user.getUserLoginFailureNum()), new Date());
            this.mailService.sendAndSave(user.getUserEmail(), EmailSubject.USER_LOCK.code(),text);
        }

        if (flag) {
            throw new SpringSafeException(12006);
        }

        return false;
    }

    @Override
    public List<PcGrantedAuthority> findAuthority(String userId) {
        String roleId = this.userRoleService.findRoleIdByUserId(userId);

        if (org.apache.commons.lang3.StringUtils.isBlank(roleId)) {
            return null;
        }

        return this.gzRoleService.getPcGrantedAuthorityByRoleId(roleId);
    }

    @Override
    public boolean checkDataIntegrity(String userId) {
        GzUser user = this.getById(userId);
        if (user == null) {
            if (this.userBackService.getById(userId) == null) {
                return true;
            }
        } else if (user.generateUserSalt().equals(user.getUserSalt())) {
            return true;
        }

        return false;
    }

    @Override
    public void checkPassword(String password) throws SpringSafeException {
        String userId = SecurityUtils.getCurrentLoginName();
        // 不存在则返回报警
        if (!this.baseMapper.exists(userId)) {
            throw new SpringSafeException(12000);
        }

        GzUser user = this.getById(userId);
        if (user.getUserStatus() != 0) {
            throw new SpringSafeException(11002);
        }

        if (!Sm4Util.decryptByEcb(user.getUserPassword()).equals(password)){
            try {
                this.updateUserLoginFailStatus(userId);
            } catch (SpringSafeException e) {
                throw new SpringSafeException(12005);
            }
        }
    }

    private GzUser newUser(GzUser user) {
        LocalDateTime now = LocalDateTime.now();
        user.setUserCreateBy(SecurityUtils.getCurrentLoginName());
        user.setUserCreateDate(now);
        user.setUserUpdateBy(SecurityUtils.getCurrentLoginName());
        user.setUserUpdateDate(now);
        user.setUserSalt(user.generateUserSalt());
        return user;
    }

























}
