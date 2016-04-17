
package org.t.stock.spring.security.dao;

import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author TOM
 */
public interface UserDAO {

    UserDetails loadUserByUsername(final String username);
    
}
