package org.t.stock.web.validator;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;
import org.t.stock.model.stock.WalletStock;
import org.t.stock.service.publication.PublicationService;
import org.t.stock.spring.security.dao.UserDAO;
import org.t.stock.web.model.RegisterForm;

/**
 *
 * @author TOM
 */
@Component
public class RegisterFormValidator implements Validator {

    @Autowired
    UserDAO userDAOImpl;
    @Autowired
    PublicationService publicationServiceImpl;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm registerForm = (RegisterForm) target;

        if (userDAOImpl.checkIUserExists(registerForm.getLogin())) {
            errors.rejectValue("login", "user.exists");
            return;
        }
        if ((null != registerForm.getPassword() && null != registerForm.getConfirmPassword()) && !registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            errors.rejectValue("password", "password.not.same");
            errors.rejectValue("confirmPassword", "password.not.same");
        }

        if (!registerForm.getWalletStocks().isEmpty()) {
            int i = -1;
            for (WalletStock walletStock : registerForm.getWalletStocks()) {
                ++i;
                if (walletStock.getWalletAmount() < 0) {
                    errors.rejectValue("walletStocks[" + i + "]", "walletStocks.amount.below.zero");
                } else if (walletStock.getWalletAmount() > walletStock.getAvailable()) {
                    errors.rejectValue("walletStocks[" + i + "]", "walletStocks.amount.too.much");
                }

            }
        }
        if (registerForm.getMoney().compareTo(BigDecimal.ZERO) == -1) {
            errors.rejectValue("money", "money.available.below.zero");
        }
    }

}
