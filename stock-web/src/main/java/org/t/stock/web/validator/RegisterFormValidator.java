package org.t.stock.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.t.stock.web.model.RegisterForm;

/**
 *
 * @author TOM
 */
@Component
public class RegisterFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm registerForm = (RegisterForm) target;

        if ((null != registerForm.getPassword() && null != registerForm.getConfirmPassword()) && registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            errors.rejectValue("password", "not same", "not same");
            errors.rejectValue("confirmPassword", "not same", "not same");
        }
    }

}
