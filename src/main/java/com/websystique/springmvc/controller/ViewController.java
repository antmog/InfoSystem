package com.websystique.springmvc.controller;


import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    /**
     * Starting page controller.
     */
    @RequestMapping("/")
    public String startPage() {
        return "index";
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
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }

}
