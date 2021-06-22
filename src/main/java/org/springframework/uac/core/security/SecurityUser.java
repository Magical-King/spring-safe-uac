package org.springframework.uac.core.security;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.safe.utils.DateUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.uac.model.domain.GzUser;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
 * @author Sir.D
 */
@Data
public class SecurityUser extends GzUser implements UserDetails {
    Collection<? extends GrantedAuthority> authorities;

    boolean accountNonExpired;

    boolean accountNonLocked;

    boolean credentialsNonExpired;

    boolean enabled;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getUserOrgId()).append(getUserId()).append(getUsername())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj != null && SecurityUser.class.isAssignableFrom(obj.getClass())) {
            SecurityUser userInfo = (SecurityUser) obj;
            isEqual = new EqualsBuilder().append(getUserOrgId(), userInfo.getUserOrgId())
                    .append(getUserId(), userInfo.getUserId()).append(getUsername(), userInfo.getUsername()).isEquals();
        }
        return isEqual;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return getUserPassword();
    }
    @Override
    public String getUsername() {
        return getUserId();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        if (this.getUserStatus() != 0 && DateUtil.getTimeMillis() <= DateUtil.getTimeMillis(this.getUserLockDate())) {
            return false;
        } else {
            return !this.getUserIsTemporary() || !LocalDateTime.now().isAfter(LocalDateTime.of(this.getUserEndDate(), LocalTime.MAX));
        }
    }
}
