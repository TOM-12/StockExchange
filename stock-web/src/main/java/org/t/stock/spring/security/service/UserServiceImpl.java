package org.t.stock.spring.security.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t.stock.dao.stockexchange.StockExchangeDAO;
import org.t.stock.spring.security.dao.UserDAO;
import org.t.stock.web.model.RegisterForm;

/**
 *
 * @author TOM
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserDAO userDAOImpl;

    @Autowired
    private StockExchangeDAO stockExchangeDAOImpl;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userDAOImpl.loadUserByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUser(final RegisterForm registerForm) throws Exception {

        if (!userDAOImpl.checkIUserExists(registerForm.getLogin())) {
            try {
                userDAOImpl.createUser(registerForm.getFirstName(), registerForm.getLastName(), registerForm.getLogin(), registerForm.getPassword());

                long walletId = userDAOImpl.createWallet(registerForm.getLogin(), registerForm.getMoney());

                stockExchangeDAOImpl.createWalletStocks(walletId, registerForm.getWalletStocks());

            } catch (Exception e) {
                LOGGER.catching(e);
                throw e;
            }
        }

    }

}
