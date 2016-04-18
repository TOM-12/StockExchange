package org.t.stock.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.t.stock.spring.security.dao.UserDAO;
import org.t.stock.web.model.RegisterForm;

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

    public boolean createUser(RegisterForm registerForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
