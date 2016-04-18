
package org.t.stock.spring.security.service;

import org.springframework.transaction.annotation.Transactional;
import org.t.stock.web.model.RegisterForm;

/**
 *
 * @author TOM
 */
public interface UserService {

    @Transactional(rollbackFor = Exception.class)
    void createUser(RegisterForm registerForm) throws Exception;

}
