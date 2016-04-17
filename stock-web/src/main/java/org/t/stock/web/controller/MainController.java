/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class MainController {

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        System.out.println("org.t.stock.web.controller.MainController.defaultPage()");
        ModelAndView model = new ModelAndView();
        model.setViewName("startPageDefinition");
        return model;

    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public ModelAndView register() {
        System.out.println("org.t.stock.web.controller.MainController.settings()");
        ModelAndView model = new ModelAndView();
        model.setViewName("registerPageDefinition");
        return model;

    }
}
