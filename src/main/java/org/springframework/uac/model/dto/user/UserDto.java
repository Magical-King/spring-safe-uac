package org.springframework.uac.model.dto.user;

import lombok.Data;
import org.springframework.uac.model.validate.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户创建接受实例
 * @author Sir.D
 */
@Data
public class UserDto implements Serializable {
        private static final long serialVersionUID = -3385971785265488527L;

    @Size(min = 1, max = 50, message = "organization.orgId.size")
    @NotBlank(groups = {Create.class, Update.class}, message = "organization.orgId.notnull")
    private String orgId;

    @NotBlank(groups = {Create.class}, message = "user.userId.notnull")
    @Pattern(groups = {Create.class}, regexp = "([a-zA-Z][a-zA-Z0-9]{1,49})", message = "user.userId.pattern" )
    private String userId;

    @NotBlank(groups = {Update.class, Valid2.class}, message = "user.userIdEncoded.notnull")
    private String userIdEncoded;

    @Size(min = 1, max = 50, message = "user.userName.size")
    @NotBlank(groups = {Create.class, Update.class}, message = "user.userName.notnull")
    private String userName;

    private boolean userIsTemporary ;

    private LocalDate userEndDate;

    @NotBlank(groups = {Create.class, Update.class}, message = "user.userEmail.notnull")
    @Pattern(groups = {Create.class, Update.class}, regexp = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "user.userEmail.pattern" )
    private String userEmail;

    @Pattern(groups = {Create.class, Update.class}, regexp = "^1[3|4|5|7|8][0-9]\\d{4,8}$", message = "user.userMobile.pattern" )
    private String userMobile;

    @Pattern(groups = {Create.class, Update.class}, regexp = "(^\\d{18}$)|(^\\d{15}$)", message = "user.userIdentityNumber.pattern" )
    private String userIdentityNumber;

    @NotEmpty(groups = {Delete.class,Valid1.class}, message = "user.userIdEncoded.notnull")
    private String[] userIdEncodeds;

    private String roleId;

    @NotEmpty(groups = {Valid3.class}, message = "user.currentPwd.notnull")
    private String password;

}
