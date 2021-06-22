package org.springframework.uac.model.dto.perm;

import lombok.Data;
import org.springframework.uac.model.validate.Valid1;
import org.springframework.uac.model.validate.Valid2;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author mayato
 */
@Data
public class RolePermDto {
    @NotNull(groups = {Valid1.class,Valid2.class}, message = "role.roleId.notnull")
    private String roleId;
    @NotEmpty(groups = {Valid2.class}, message = "perm.permId.notnull")
    private Integer[] permIds;

}
