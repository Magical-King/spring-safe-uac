package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.safe.utils.Sm3Util;
import org.springframework.safe.utils.Sm4Util;
import org.springframework.uac.model.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@Data
@TableName("gz_user")
@FieldNameConstants(prefix = "")
public class GzUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String userId;

    @TableField("user_org_id")
    private String userOrgId;

    @TableField("user_name")
    private String userName;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_status")
    private Integer userStatus;

    @TableField("user_email")
    private String userEmail;

    @TableField("user_mobile")
    private String userMobile;

    @TableField("user_identity_number")
    private String userIdentityNumber;

    @TableField("user_is_temporary")
    private Boolean userIsTemporary;

    @TableField("user_end_date")
    private LocalDate userEndDate;

    @TableField("user_last_login_ip")
    private String userLastLoginIp;

    @TableField("user_last_login_date")
    private LocalDateTime userLastLoginDate;

    @TableField("user_last_pwd_change")
    private LocalDateTime userLastPwdChange;

    @TableField("user_last_send_email_date")
    private LocalDateTime userLastSendEmailDate;

    @TableField("user_login_failure_num")
    private Integer userLoginFailureNum;

    @TableField("user_login_failure_date")
    private LocalDateTime userLoginFailureDate;

    @TableField("user_first_failure_date")
    private LocalDateTime userFirstFailureDate;

    @TableField("user_lock_date")
    private LocalDateTime userLockDate;

    @TableField("user_lock_cause")
    private String userLockCause;

    @TableField("user_pwd_security_level")
    private Integer userPwdSecurityLevel;

    @TableField("user_create_by")
    private String userCreateBy;

    @TableField("user_create_date")
    private LocalDateTime userCreateDate;

    @TableField("user_update_by")
    private String userUpdateBy;

    @TableField("user_update_date")
    private LocalDateTime userUpdateDate;

    @TableField("user_salt")
    private String userSalt;

    public String generateUserSalt() {
        StringBuffer var1 = new StringBuffer();
        var1.append(this.userId);
        var1.append(this.userPassword);
        var1.append(this.userEmail);
        var1.append(this.userMobile);
        var1.append(this.userIdentityNumber);
        return Sm3Util.digest(var1.toString());
    }

    public void encodedUserPassword(String var1) {
        this.userPassword = Sm4Util.encryptByEcb(var1);
    }

    public String decryptUserPassword() {
        return Sm4Util.decryptByEcb(this.userPassword);
    }
}
