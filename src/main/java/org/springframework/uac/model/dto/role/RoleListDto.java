package org.springframework.uac.model.dto.role;

import lombok.Data;
import org.springframework.uac.model.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * @author mayato
 */

@Data
public class RoleListDto extends BaseEntity {

    private String roleOrgId;

    private String roleName;

}
