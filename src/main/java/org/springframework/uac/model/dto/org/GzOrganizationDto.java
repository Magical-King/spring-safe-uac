package org.springframework.uac.model.dto.org;

import lombok.Data;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Valid1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sir.D
 */
@Data
public class GzOrganizationDto implements Serializable {
    private static final long serialVersionUID = -3385971785265488527L;

    @NotNull(groups = {Valid1.class}, message = "organization.orgId.notnull")
    String[] ids;

}
