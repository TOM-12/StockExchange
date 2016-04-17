
package org.t.stock.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.t.stock.dao.PublicationsDAO;

/**
 *
 * @author TOM
 */
@Controller
public class MainController {

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        System.out.println("org.t.stock.web.controller.MainController.defaultPage()");
        ModelAndView model = new ModelAndView();
        model.setViewName("startPageDefinition");
        return model;

    }

}
