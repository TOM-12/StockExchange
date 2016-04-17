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

    private final String firstName;
    private final String lastName;
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

    public String getLastName() {
        return lastName;
    }

    public static class UserDetailBuilder {

        private String firstName;
        private String lastName;
        private boolean enabled;
        private String username;
        private String password;
        private Collection<? extends org.springframework.security.core.GrantedAuthority> authoritys;

        public UserDetailBuilder() {
        }

        public UserDetailBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDetailBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDetailBuilder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserDetailBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDetailBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDetailBuilder setAuthoritys(Collection<? extends org.springframework.security.core.GrantedAuthority> authoritys) {
            this.authoritys = authoritys;
            return this;
        }

        public UserDetail createUserDetail() {
            return new UserDetail(firstName, lastName, enabled, username, password, authoritys);
        }

    }
}
