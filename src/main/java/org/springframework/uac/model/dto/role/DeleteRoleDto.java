package org.springframework.uac.model.dto.role;

import lombok.Data;
import org.springframework.uac.model.validate.Valid1;

import javax.validation.constraints.NotEmpty;

/**
 * @author mayato
 */
@Data
public class DeleteRoleDto {

    @NotEmpty(groups = {Valid1.class}, message = "role.roleId.notnull")
    String[] ids;
}
