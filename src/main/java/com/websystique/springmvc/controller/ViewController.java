package com.websystique.springmvc.controller;


import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class ViewController {

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    TariffOptionService tariffOptionService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TariffService tariffService;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;




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

    @RequestMapping("/adminPanel")
    public String adminPanel(ModelMap model) {
        List<User> users = userService.findFirstUsers();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        return "adminPanel";
    }

    @RequestMapping("/adminPanel/allUsers")
    public String adminPanelAllUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        System.out.println("ss");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        return "allUsers";
    }

    @RequestMapping("/customerPanel")
    public String customerPanel(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());

        return "customerPanel";
    }


    @RequestMapping("/")
    public String startPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "index";
    }

    @RequestMapping(value = {"/adminPanel/addUser"}, method = RequestMethod.GET)
    public String startwPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        HashSet<String> roles = new HashSet<>(Arrays.asList(Role.CUSTOMER.getRole(), Role.ADMIN.getRole()));
        model.addAttribute("roles", roles);
        return "addUser";
    }


    @RequestMapping(value = {"/adminPanel/addUser"}, method = RequestMethod.POST)
    public String saveUser( @Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "userlist";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return "addSuccess";
    }

    /**
     * Mapping to login screen.
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String onLogin(@Valid User user, BindingResult result,
                          ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";
        }

    }

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        List<User> users = userService.findAllUsers();
        List<Contract> contracts = contractService.findAllContracts();
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("options", options);
        model.addAttribute("users", users);
        model.addAttribute("contracts", contracts);
        model.addAttribute("tariffs", tariffs);

        return "userslist";
    }


    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        HashSet<String> roles = new HashSet<>(Arrays.asList(Role.CUSTOMER.getRole(), Role.ADMIN.getRole()));
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("roles", roles);
        return "registration";
    }

    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = {"/edit-user-{id}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable int id, ModelMap model) {
        User user = userService.findById(id);
        HashSet<String> roles = new HashSet<>(Arrays.asList(Role.CUSTOMER.getRole(), Role.ADMIN.getRole()));
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("roles", roles);
        return "registration";
    }


    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        return "accessdnd";
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


}
