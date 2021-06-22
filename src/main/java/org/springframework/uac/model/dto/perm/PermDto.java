package org.springframework.uac.model.dto.perm;

import lombok.Data;
import org.springframework.uac.model.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * @author mayato
 */
@Data
public class PermDto extends BaseEntity {
    private Integer permType;

    private Integer permParentId;

}
