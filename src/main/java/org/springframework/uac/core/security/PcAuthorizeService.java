package org.springframework.uac.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 *
 * @author Sir.D
 */
@Component("pcAuthorizeService")
public class PcAuthorizeService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 判断某用户是否拥有该资源访问权限
     * @param request
     * @param authentication
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(e -> antPathMatcher.match(((GrantedAuthority) e).getAuthority(), request.getRequestURI()));
    }

}
