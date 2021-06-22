package org.springframework.uac.model.dto.config;

import lombok.Data;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Delete;
import org.springframework.uac.model.validate.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author mayato
 */
@Data
public class IpWhiteDto {

    @NotNull(groups = {Update.class}, message = "ipWhite.id.notnull")
    private Integer id;

    @NotBlank(groups = {Create.class, Update.class}, message = "ipWhite.ipSegment.notnull")
    @Pattern(groups = {Create.class, Update.class}, regexp = "^(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))-(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))$", message = "ipWhite.ipSegment.pattern" )
    private String ipSegment;

    @NotEmpty(groups = {Delete.class}, message = "ipWhite.id.notnull")
    private Integer[] ids;

}
