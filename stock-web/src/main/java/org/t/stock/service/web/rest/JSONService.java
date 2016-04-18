package org.t.stock.service.web.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.t.stock.model.AjaxResponse;
import org.t.stock.model.Publication;
import org.t.stock.model.Wallet;
import org.t.stock.model.stock.Stock;
import org.t.stock.service.exchange.rate.ExchangeRateService;
import org.t.stock.service.exchange.stock.StockExchangeService;
import org.t.stock.service.publication.PublicationService;
import org.t.stock.spring.security.model.UserDetail;

/**
 *
 * @author TOM
 */
@RestController
public class JSONService {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(JSONService.class.getName());

    @Autowired
    private PublicationService publicationServiceImpl;
    @Autowired
    private StockExchangeService stockExchangeServiceImpl;

    @RequestMapping(value = "/stock/test", method = RequestMethod.GET)
    public String getTime() {

        ObjectMapper mapper = new ObjectMapper();
        String string = null;
        Publication<Stock> currentExchangeRate = publicationServiceImpl.getCurrentExchangeRate();
        currentExchangeRate.setPublicationDate(null);
        try {
            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentExchangeRate);
        } catch (IOException ex) {
            LOGGER.catching(ex);
        }
        return string;
    }

    @RequestMapping(value = "/stock/currentStock", method = RequestMethod.GET)
    public String getCurrentStock() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat());

        String string = null;
        try {
            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicationServiceImpl.getCurrentExchangeRate());
        } catch (IOException ex) {
            LOGGER.catching(ex);
        }
        return string;
    }

    @RequestMapping(value = "/stock/ajax", method = RequestMethod.GET)
    public String getForUser() {

        Wallet wallet;
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
                wallet = null;
            } else {
                String username = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
                LOGGER.debug(username);
                try {
                    wallet = stockExchangeServiceImpl.getUserWallet(username);
                } catch (Exception e) {
                    wallet = null;
                }
            }
        } else {
            wallet = null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat());

        AjaxResponse ajaxResponse = new AjaxResponse(ExchangeRateService.getStatus(), publicationServiceImpl.getCurrentExchangeRate(), wallet);
        String string = null;
        try {
            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ajaxResponse);
        } catch (IOException ex) {
            LOGGER.catching(ex);
        }
        return string;
    }
}
