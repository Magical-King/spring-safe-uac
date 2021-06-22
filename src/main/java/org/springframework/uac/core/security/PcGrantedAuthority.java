package org.springframework.uac.core.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Sir.D
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PcGrantedAuthority implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof PcGrantedAuthority ? this.role.equals(((PcGrantedAuthority)obj).role) : false;
        }
    }


    @Override
    public int hashCode() {
        return this.role.hashCode();
    }

    @Override
    public String toString() {
        return this.role;
    }
}
