package com.infosystem.springmvc.controller;


import com.infosystem.springmvc.dto.AddContractDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.*;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        List<TariffOption> options = tariffOptionService.findFirstTariffOptions();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        model.addAttribute("options",options);
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

    @RequestMapping("/adminPanel/allOptions")
    public String adminPanelAllOptions(ModelMap model) {
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("options",options);
        return adminPath+"allOptions";
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
        AddContractDto addContractDto = new AddContractDto();
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("tariffs",tariffs);
        model.addAttribute("contractUserIdDto", addContractDto);
        model.addAttribute("edit", false);
        return adminPath+"addContract";
    }

    @RequestMapping(value ="/adminPanel/addContractToUser/{user_id}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "user_id") Integer user_id,ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        AddContractDto addContractDto = new AddContractDto();
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("user_id",user_id);
        model.addAttribute("tariffs",tariffs);
        model.addAttribute("contractUserIdDto", addContractDto);
        model.addAttribute("edit", false);
        return adminPath+"addContract";
    }


    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.GET)
    public String addOption(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        TariffOption tariffOption = new TariffOption();
        model.addAttribute("tariffOption", tariffOption);
        model.addAttribute("edit", false);
        return adminPath+"addOption";
    }


    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.POST)
    public String saveOption( @Valid TariffOption tariffOption, BindingResult result,
                            ModelMap model) {

        if (result.hasErrors()) {
            return adminPath+"addOption";
        }

        tariffOptionService.saveTariffOption(tariffOption);

        model.addAttribute("success", "User " + tariffOption.getName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return adminPath+"addSuccess";
    }


    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("edit", false);
        return adminPath+"addTariff";
    }

    @RequestMapping(value = "/adminPanel/user/{user_id}")
    public String user(@PathVariable(value = "user_id") Integer user_id, ModelMap model) throws DatabaseException {
        User user = userService.findById(user_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("user", user);
        return adminPath+"user";
    }

    @RequestMapping(value = "/adminPanel/contract/{contract_id}")
    public String contract(@PathVariable(value = "contract_id") Integer contract_id, ModelMap model) throws DatabaseException {
        Contract contract = contractService.findById(contract_id);
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contract", contract);
        return adminPath+"contract";
    }

    @RequestMapping(value = "/adminPanel/tariff/{tariff_id}")
    public String tariff(@PathVariable(value = "tariff_id") Integer tariff_id, ModelMap model) throws DatabaseException {
        Tariff tariff;
        tariff = tariffService.findById(tariff_id);
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariff", tariff);
        return adminPath+"tariff";
    }

    @RequestMapping(value = "/adminPanel/option/{option_id}")
    public String option(@PathVariable(value = "option_id") Integer option_id, ModelMap model) throws DatabaseException {
        TariffOption tariffOption = tariffOptionService.findById(option_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffOption", tariffOption);
        return adminPath+"option";
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
