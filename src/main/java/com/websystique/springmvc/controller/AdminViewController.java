package com.websystique.springmvc.controller;


import com.websystique.springmvc.dto.ContractUserIdDto;
import com.websystique.springmvc.dto.GetOptionsAsJsonDto;
import com.websystique.springmvc.dto.GetTarifAsJsonDto;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AdminViewController {

    private String adminPath = "admin/";

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


    @RequestMapping("/adminPanel")
    public String adminPanel(ModelMap model) {
        List<User> users = userService.findFirstUsers();
        List<Tariff> tariffs =tariffService.findFirstTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        model.addAttribute("tariffs",tariffs);
        return adminPath+"adminPanel";
    }

    @RequestMapping("/adminPanel/allUsers")
    public String adminPanelAllUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        return adminPath+"allUsers";
    }

    @RequestMapping("/adminPanel/allContracts")
    public String adminPanelAllContracts(ModelMap model) {
        List<Contract> contracts = contractService.findAllContracts();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contracts",contracts);
        return adminPath+"allContracts";
    }

    @RequestMapping("/adminPanel/allTariffs")
    public String adminPanelAllTariffs(ModelMap model) {
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffs",tariffs);
        return adminPath+"allTariffs";
    }


    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.GET)
    public String addUser(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        HashSet<String> roles = new HashSet<>(Arrays.asList(Role.CUSTOMER.getRole(), Role.ADMIN.getRole()));
        model.addAttribute("roles", roles);
        return adminPath+"addUser";
    }


    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.POST)
    public String saveUser( @Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return adminPath+"addUser";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return adminPath+"addSuccess";
    }

    @RequestMapping(value ="/adminPanel/addContract", method = RequestMethod.GET)
    public String addContract(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        ContractUserIdDto contractUserIdDto = new ContractUserIdDto();
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("tariffs",tariffs);
        model.addAttribute("contractUserIdDto", contractUserIdDto);
        model.addAttribute("edit", false);
        return adminPath+"addContract";
    }

    @RequestMapping(value ="/adminPanel/addContractToUser/{user_id}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "user_id") Integer user_id,ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        ContractUserIdDto contractUserIdDto = new ContractUserIdDto();
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("user_id",user_id);
        model.addAttribute("tariffs",tariffs);
        model.addAttribute("contractUserIdDto", contractUserIdDto);
        model.addAttribute("edit", false);
        return adminPath+"addContract";
    }

//    @RequestMapping(value = {"/adminPanel/addContract","/adminPanel/addContractToUser/{user_id}"},consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//            method = RequestMethod.POST)
//    public ModelAndView saveContract(@RequestBody @Valid  ContractUserIdDto contractUserIdDto, BindingResult result,
//                               ModelMap model) {
//
//        if (result.hasErrors()) {
//            return new ModelAndView(adminPath+"addContract");
//        }
//        contractService.newContract(contractUserIdDto);
//        ModelAndView mav = new ModelAndView(adminPath+"addSuccess");
//        mav.addObject("loggedinuser", getPrincipal());
//        mav.addObject("success", "Contract registered successfully");
//        return mav;
//    }


    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("edit", false);
        return adminPath+"addTariff";
    }

    @RequestMapping(value = "/adminPanel/user/{user_id}")
    public String user(@PathVariable(value = "user_id") Integer user_id, ModelMap model) {
        User user = userService.findById(user_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("user", user);
        return adminPath+"user";
    }

    @RequestMapping(value = "/adminPanel/contract/{contract_id}")
    public String contract(@PathVariable(value = "contract_id") Integer contract_id, ModelMap model) {
        Contract contract = contractService.findById(contract_id);
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contract", contract);
        return adminPath+"contract";
    }

    @RequestMapping(value = "/adminPanel/tariff/{tariff_id}")
    public String tariff(@PathVariable(value = "tariff_id") Integer tariff_id, ModelMap model) {
        Tariff tariff = tariffService.findById(tariff_id);
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariff", tariff);
        return adminPath+"tariff";
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
