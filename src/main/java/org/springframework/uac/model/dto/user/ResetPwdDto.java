package org.springframework.uac.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户重置密码实例
 * @author Sir.D
 */
@Data
public class ResetPwdDto {
    @NotEmpty(message = "user.userIdEncoded.notnull")
    private String[] userIdEncodeds;
}
