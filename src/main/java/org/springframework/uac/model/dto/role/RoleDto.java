package org.springframework.uac.model.dto.role;

import lombok.Data;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Update;
import org.springframework.uac.model.validate.Valid1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


/**
 * @author mayato
 */
@Data
public class RoleDto {

    @NotBlank(groups = {Update.class,Valid1.class}, message = "role.roleId.notnull")
    private String roleId;
    @NotBlank(groups = {Create.class}, message = "role.orgId.notnull")
    private String roleOrgId;
    @NotNull(groups = {Create.class}, message = "role.roleTypeId.notnull")
    private Integer roleTypeId;
    @NotBlank(groups = {Create.class}, message = "role.roleName.notnull")
    private String roleName;
    private String roleRemarks;
}
