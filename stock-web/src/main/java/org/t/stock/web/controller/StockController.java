package org.t.stock.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;
import org.t.stock.service.publication.PublicationServiceImpl;

/**
 *
 * @author TOM
 */
@Controller
public class StockController {


    @RequestMapping(value = {"/stock"}, method = {RequestMethod.GET})
    public ModelAndView stockPage() {
        System.out.println("org.t.stock.web.controller.MainController.defaultPage()");
        ModelAndView model = new ModelAndView();
        model.setViewName("stockPageDefinition");
        return model;

    }

}
