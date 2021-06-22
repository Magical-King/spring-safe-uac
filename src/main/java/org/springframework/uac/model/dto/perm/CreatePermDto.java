package org.springframework.uac.model.dto.perm;

import lombok.Data;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Delete;
import org.springframework.uac.model.validate.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mayato
 */
@Data
public class CreatePermDto {

    @NotNull(groups = {Update.class,Delete.class}, message = "perm.permId.notnull")
    private Integer permId;
    @NotNull(groups = {Create.class}, message = "perm.permParentId.notnull")
    private Integer permParentId;
    @NotNull(groups = {Create.class}, message = "perm.permType.notnull")
    private Integer permType;
    @NotBlank(groups = {Create.class}, message = "perm.permName.notnull")
    private String permName;

    private String component;

    private String iframeUrl;

    private String permUrlPrefix;

    private String permUrl;

}
