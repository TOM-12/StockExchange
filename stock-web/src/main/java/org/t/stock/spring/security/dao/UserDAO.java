package org.t.stock.spring.security.dao;

import java.math.BigDecimal;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author TOM
 */
public interface UserDAO {

    UserDetails loadUserByUsername(final String username);

    public boolean checkIUserExists(final String login);

    public void createUser(String firstName, String lastName, String login, String password);

    public long createWallet(String login, BigDecimal money);
}
