package org.t.stock.service.web.rest;

import org.t.stock.web.controller.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;
import org.t.stock.service.publication.PublicationServiceImpl;

/**
 *
 * @author TOM
 */
@RestController
public class JSONService {

    @Autowired
    private PublicationServiceImpl publicationServiceImpl;

    @RequestMapping(value = "/stock/test", method = RequestMethod.GET)
    public String getTime() {

        ObjectMapper mapper = new ObjectMapper();
        String string = null;
        Publication<Stock> currentExchangeRate = publicationServiceImpl.getCurrentExchangeRate();
        currentExchangeRate.setPublicationDate(null);
        try {
            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentExchangeRate);
        } catch (IOException ex) {
            Logger.getLogger(JSONService.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(JSONService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println(string);
        return string;
    }
}
