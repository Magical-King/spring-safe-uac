package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@Data
@TableName("gz_user_back")
public class GzUserBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String userId;

    @TableField("org_id")
    private String orgId;

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

}
