package org.ng.undp.vdms.dto;

import lombok.Data;

import org.ng.undp.vdms.domains.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by macbook on 6/13/17.
 */

@Data
public class CustomUserDetails extends org.springframework.security.core.userdetails.User{




    private User user;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {

        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public CustomUserDetails(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
