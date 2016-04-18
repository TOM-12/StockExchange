package org.t.stock.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;
import org.t.stock.model.stock.WalletStock;
import org.t.stock.service.publication.PublicationService;
import org.t.stock.spring.security.service.UserService;
import org.t.stock.web.model.RegisterForm;
import org.t.stock.web.validator.RegisterFormValidator;

/**
 *
 * @author TOM
 */
@Controller
@SessionAttributes("registerForm")
@Scope
public class RegisterController {

    @Autowired
    private PublicationService publicationServiceImpl;
    @Autowired
    RegisterFormValidator registerFormValidator;
    @Autowired
    UserService userServiceImpl;

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public ModelAndView registerForm() {

        Publication<Stock> currentExchangeRate = publicationServiceImpl.getCurrentExchangeRate();
        ArrayList<WalletStock> walletStocks = new ArrayList<>(0);
        for (Stock stock : currentExchangeRate.getItems()) {
            walletStocks.add(new WalletStock.WalletStockBuilder()
                    .setCode(stock.getCode())
                    .setAvailable(stock.getAvailable())
                    .setName(stock.getName())
                    .setPrice(stock.getPrice())
                    .setUnit(stock.getUnit())
                    .createWalletStock()
            );
        }
        RegisterForm registerForm = new RegisterForm.RegisterFormBuilder()
                .setMoney(BigDecimal.ZERO)
                .setPublicationDate(currentExchangeRate.getPublicationDate())
                .setWalletStocks(walletStocks)
                .createRegisterForm();

        ModelAndView modelAndView = new ModelAndView("registerPageDefinition");
        modelAndView.addObject("registerForm", registerForm);
        return modelAndView;

    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public ModelAndView registerSubmit(
            @Valid RegisterForm registerForm, BindingResult result, SessionStatus status) {

        Publication<Stock> currentExchangeRate = publicationServiceImpl.getCurrentExchangeRate();
        if (currentExchangeRate.getPublicationDate().getMillis() != registerForm.getPublicationDate().getMillis()) {
            ArrayList<WalletStock> walletStocks = new ArrayList<>(0);
            for (Stock stock : currentExchangeRate.getItems()) {
                walletStocks.add(new WalletStock.WalletStockBuilder()
                        .setCode(stock.getCode())
                        .setAvailable(stock.getAvailable())
                        .setName(stock.getName())
                        .setPrice(stock.getPrice())
                        .setUnit(stock.getUnit())
                        .createWalletStock()
                );
            }
            registerForm.setWalletStocks(walletStocks);
            registerForm.setPublicationDate(currentExchangeRate.getPublicationDate());
            result.rejectValue("walletStocks", "register.stocks.refreshed");
        }

        if (!result.hasErrors()) {
            registerFormValidator.validate(registerForm, result);
            if (!result.hasErrors()) {
                if (userServiceImpl.createUser(registerForm)) {
                    status.setComplete();
                    return new ModelAndView("registerSuccessPageDefinition")
                            .addObject("firstName", registerForm.getFirstName())
                            .addObject("lastName", registerForm.getLastName())
                            .addObject("login", registerForm.getLogin());
                } else {
                    result.addError(new ObjectError("registerForm", "User couldnt be created. Please try again later."));
                }
            }
        }

        return new ModelAndView("registerPageDefinition",
                "registerForm", registerForm);

    }

}
