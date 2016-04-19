package org.t.stock.web.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.model.TransactionStatusEnum;
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

    @RequestMapping(value = {"/stock/buy"}, method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String buyStock(@RequestParam long stockId, @RequestParam long stockAmount, HttpServletResponse httpServletResponse) {

        TransactionStatusEnum transactionStatus;
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
                transactionStatus = TransactionStatusEnum.ERROR;
            } else if (stockAmount > 0) {
                String username = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
                try {
                    transactionStatus = stockExchangeServiceImpl.buyStock(username, stockId, stockAmount);
                } catch (Exception e) {
                    LOGGER.catching(e);
                    transactionStatus = TransactionStatusEnum.ERROR;
                }
            } else {
                transactionStatus = TransactionStatusEnum.NEGATIVE;
            }
        } else {
            transactionStatus = TransactionStatusEnum.ERROR;
        }
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionStatus.getDescription());
        } catch (IOException ex) {
            LOGGER.catching(ex);
        }
        return result;
    }

    @RequestMapping(value = {"/stock/sell"}, method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String sellStock(@RequestParam long stockId, @RequestParam long stockAmount) {
        TransactionStatusEnum transactionStatus;
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
                transactionStatus = TransactionStatusEnum.ERROR;
            } else if (stockAmount > 0) {
                String username = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
                try {
                    transactionStatus = stockExchangeServiceImpl.sellStock(username, stockId, stockAmount);
                } catch (Exception e) {
                    LOGGER.catching(e);
                    transactionStatus = TransactionStatusEnum.ERROR;
                }
            } else {
                transactionStatus = TransactionStatusEnum.NEGATIVE;
            }
        } else {
            transactionStatus = TransactionStatusEnum.ERROR;
        }
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionStatus.getDescription());
        } catch (IOException ex) {
            LOGGER.catching(ex);
        }
        return result;
    }

}
