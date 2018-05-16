package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class GlobalController extends ControllerTemplate {

    /**
     * Mapping to login screen.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String onLogin(@Valid User user, BindingResult result,
                          ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/index";
        }
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    /**
     * Redirects to LK(main menu of account interface) admin/customer.
     * @param model
     * @return adminPanel if current user is ADMIN, customerPanel if CUSTOMER
     */
    @RequestMapping("/lk")
    public String lk(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedinuser", getPrincipal());

        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        }
        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/adminPanel";
        }
        return "redirect:/customerPanel";
    }

    /**
     * Index page.
     * @param model
     * @return startPage
     */
    @RequestMapping("/")
    public String startPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "index";
    }

}
