package org.springframework.uac.model.dto.perm;

import lombok.Data;
import org.springframework.uac.model.validate.Delete;
import org.springframework.uac.model.validate.Valid2;

import javax.validation.constraints.NotEmpty;

/**
 * @author mayato
 */
@Data

public class DeletePermDto {
    @NotEmpty(groups = {Delete.class}, message = "perm.permId.notnull")
    private Integer[] permIds;
}
