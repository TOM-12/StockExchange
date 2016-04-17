package org.t.stock.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.t.stock.spring.security.dao.UserDAO;

/**
 *
 * @author TOM
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAOImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAOImpl.loadUserByUsername(username);
    }

}
