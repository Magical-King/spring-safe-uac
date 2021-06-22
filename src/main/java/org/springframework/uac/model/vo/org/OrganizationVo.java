package org.springframework.uac.model.vo.org;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * @author Sir.D
 */
@Data
@FieldNameConstants(prefix = "")
public class OrganizationVo {

    private String orgId;

    private String orgName;

    private Boolean orgIsInvalid;
}
