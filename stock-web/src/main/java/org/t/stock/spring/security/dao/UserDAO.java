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

    public void createUser(final String firstName, final String lastName, final String login, final String password);

    public long createWallet(final String login, final BigDecimal money);
}
