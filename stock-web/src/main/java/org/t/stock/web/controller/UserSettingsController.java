package org.t.stock.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author TOM
 */
@Controller
public class UserSettingsController {

    @RequestMapping(value = {"/stock/settings"}, method = {RequestMethod.GET})
    public ModelAndView settings() {
        System.out.println("org.t.stock.web.controller.MainController.settings()");
        ModelAndView model = new ModelAndView();
        model.setViewName("settingsPageDefinition");
        return model;

    }
}
