package org.t.stock.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.service.exchange.stock.StockExchangeService;
import org.t.stock.spring.security.model.UserDetail;

/**
 *
 * @author TOM
 */
@Controller
public class StockController {

    private static final Logger LOGGER = LogManager.getLogger(StockController.class.getName());

    @Autowired
    StockExchangeService stockExchangeServiceImpl;

    @RequestMapping(value = {"/stock"}, method = {RequestMethod.GET})
    public ModelAndView stockPage() {
        System.out.println("org.t.stock.web.controller.MainController.defaultPage()");
        ModelAndView model = new ModelAndView();
        model.setViewName("stockPageDefinition");
        return model;

    }

    @RequestMapping(value = {"/stock/buy"}, method = {RequestMethod.POST})
    public String buyStock(@RequestParam long stockId, @RequestParam long stockAmount) {

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
//                wallet = null;
            } else {
                String username = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
                try {
                    stockExchangeServiceImpl.buyStock(username, stockId, stockAmount);
                } catch (Exception e) {
                    LOGGER.catching(e);
//                    wallet = null;
                }
            }
        } else {
//            wallet = null;
        }

        return "ok";
    }

    @RequestMapping(value = {"/stock/sell"}, method = {RequestMethod.POST})
    public String sellStock(@RequestParam long stockId, @RequestParam long stockAmount) {
        System.out.println("org.t.stock.web.controller.StockController.sellStock()");

        String username = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        try {
            stockExchangeServiceImpl.sellStock(username, stockId, stockAmount);
        } catch (Exception e) {
            LOGGER.catching(e);
//                    wallet = null;
        }

        return "ok";
    }

}
