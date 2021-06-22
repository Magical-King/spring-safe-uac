package org.springframework.uac.core.security;

import org.springframework.core.annotation.Order;
import org.springframework.safe.security.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author Sir.D
 */
@Order
@Component
public class PermissionAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config
				.anyRequest()
				.access("@pcAuthorizeService.hasPermission(request,authentication)");
		return true;
	}

}
