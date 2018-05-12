package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class GlobalViewController extends ViewControllerTemplate {

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

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

    @RequestMapping("/")
    public String startPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "index";
    }

}
