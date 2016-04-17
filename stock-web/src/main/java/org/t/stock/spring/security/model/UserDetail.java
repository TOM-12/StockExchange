package org.t.stock.spring.security.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author TOM
 */
public class UserDetail implements UserDetails {

    private static final long serialVersionUID = 314111555759448289L;

    private String firstName;
    private String lastName;
    private final boolean enabled;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authoritys;

    public UserDetail(String firstName, String lastName, boolean enabled, String username, String password, Collection<? extends GrantedAuthority> authoritys) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.username = username;
        this.password = password;
        this.authoritys = authoritys;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritys;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
