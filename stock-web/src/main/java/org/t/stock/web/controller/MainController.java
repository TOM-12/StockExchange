package org.t.stock.web.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
                return new ModelAndView("startPageDefinition");
            } else {
                return new ModelAndView("stockPageDefinition");
            }
        } else {
            return new ModelAndView("startPageDefinition");
        }

    }

}
