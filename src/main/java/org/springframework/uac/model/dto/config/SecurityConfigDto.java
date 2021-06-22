package org.springframework.uac.model.dto.config;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author mayato
 */
@Data
public class SecurityConfigDto {

    @NotNull(message = "securityConfig.configId.notnull")
    private String configId;

    private String configValue;

    private String remake;
}
