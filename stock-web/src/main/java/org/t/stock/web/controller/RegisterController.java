package org.t.stock.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;
import org.t.stock.model.stock.WalletStock;
import org.t.stock.service.publication.PublicationService;
import org.t.stock.web.model.RegisterForm;

/**
 *
 * @author TOM
 */
@Controller
public class RegisterController {

    @Autowired
    private PublicationService publicationServiceImpl;

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public ModelAndView registerGet() {

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

        ModelAndView model = new ModelAndView("registerPageDefinition",
                "registerForm",
                new RegisterForm.RegisterFormBuilder()
                .setMoney(BigDecimal.ZERO)
                .setPublicationDate(currentExchangeRate.getPublicationDate())
                .setWalletStocks(walletStocks)
                .createRegisterForm());
        return model;

    }

//    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
//    public ModelAndView registerPost(@ModelAttribute("registerForm")) {
//
//    }
}
