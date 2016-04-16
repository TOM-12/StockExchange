/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t.stock.web.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.dao.PublicationsDAO;

/**
 *
 * @author TOM
 */
@Controller
public class StockController {

    @Autowired
    PublicationsDAO publicationsDAOImpl;

    @RequestMapping(value = {"/stock"}, method = RequestMethod.GET)
    public ModelAndView stockPage() {
        System.out.println("org.t.stock.web.controller.MainController.defaultPage()");
        ModelAndView model = new ModelAndView();
        model.setViewName("stock");
        return model;

    }

    @RequestMapping(value = {"/stock/settings"}, method = RequestMethod.GET)
    public ModelAndView settings() {
        System.out.println("org.t.stock.web.controller.MainController.settings()");
        ModelAndView model = new ModelAndView();
        model.setViewName("settings");
        return model;

    }

    @RequestMapping(value = "/stock/test", method = RequestMethod.GET)
    public @ResponseBody
    String getTime() {

        String result = (publicationsDAOImpl.getCurrentExchangeRate().toString());
        return result;
    }

    @RequestMapping(value = "/stock/currentStock", method = RequestMethod.GET)
    public @ResponseBody
    String getCurrentStock() {

//        ObjectMapper mapper = new ObjectMapper();
        String string="?";
//        try {
//            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicationsDAOImpl.getCurrentExchangeRate());
//        } catch (IOException ex) {
//            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        publicationsDAOImpl.getCurrentExchangeRate();

        System.out.println(string);
        return string;
    }
}
