package org.springframework.uac.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户修改密码实例
 * @author Sir.D
 */
@Data
public class ModifyPwdDto implements Serializable {

	private static final long serialVersionUID = -3933378415083541145L;

	@NotBlank(message = "user.userIdEncoded.notnull")
	private String userIdEncoded;

	@NotBlank(message = "user.currentPwd.notnull")
	private String currentPwd;

	@NotBlank(message = "user.newPassword.notnull")
	private String newPassword;

	@NotBlank(message = "user.confirmPwd.notnull")
	private String confirmPwd;

}
