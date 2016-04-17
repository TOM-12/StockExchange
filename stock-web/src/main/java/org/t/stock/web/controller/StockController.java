package org.t.stock.web.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.service.publication.PublicationServiceImpl;

/**
 *
 * @author TOM
 */
@Controller
public class StockController {

    @Autowired
    private PublicationServiceImpl publicationServiceImpl;

    @RequestMapping(value = {"/stock"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView stockPage() {
        System.out.println("org.t.stock.web.controller.MainController.defaultPage()");
        ModelAndView model = new ModelAndView();
        model.setViewName("stockPageDefinition");
        return model;

    }

    @RequestMapping(value = {"/stock/settings"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView settings() {
        System.out.println("org.t.stock.web.controller.MainController.settings()");
        ModelAndView model = new ModelAndView();
        model.setViewName("settingsPageDefinition");
        return model;

    }

    @RequestMapping(value = "/stock/test", method = RequestMethod.GET)
    public @ResponseBody
    String getTime() {

        String result = (publicationServiceImpl.getCurrentExchangeRate().toString());
        return result;
    }

    @RequestMapping(value = "/stock/currentStock", method = RequestMethod.GET)
    public @ResponseBody
    String getCurrentStock() {

        ObjectMapper mapper = new ObjectMapper();
        String string = "?";
        try {
            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicationServiceImpl.getCurrentExchangeRate());
        } catch (IOException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        publicationServiceImpl.getCurrentExchangeRate();

        return string;
    }
}
