package org.springframework.uac.core.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.security.excep.UserLoginException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.service.user.GzUserService;
import org.springframework.uac.utils.MessageSourceUtil;

import java.util.List;

/**
 * @author Sir.D
 */
@Component
public class UacUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private GzUserService gzUserService;

    @Autowired
    private MessageSourceUtil sourceUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GzUser user = this.gzUserService.getById(username);
        if (null == user) {
            throw new UsernameNotFoundException("11001");
        } else {
            SecurityUser securityUser = (new ModelMapper()).map(user, SecurityUser.class);
            if (!securityUser.isEnabled()) {
                throw new UserLoginException(sourceUtil.message(12007));
            } else {
                List<PcGrantedAuthority> authorties = this.gzUserService.findAuthority(username);
                if (null == authorties) {
                    throw new UserLoginException(sourceUtil.message(11002));
                }

                securityUser.setAuthorities(authorties);
                return securityUser;
            }
        }
    }
}
