package com.infosystem.springmvc.controller;


import com.infosystem.springmvc.dto.AddContractDto;
import com.infosystem.springmvc.dto.AddTariffOptionDto;
import com.infosystem.springmvc.dto.AddUserDto;
import com.infosystem.springmvc.dto.AdminPanelDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.*;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.DataService.DataService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.validators.UserFormValidator;
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
    UserFormValidator userFormValidator;

    @Autowired
    UserService userService;

    @Autowired
    DataService dataService;

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

    /**
     * Returns view of main admin panel.
     *
     * @param model
     * @return view of main admin panel
     */
    @RequestMapping("/adminPanel")
    public String adminPanel(ModelMap model) {
        AdminPanelDto adminPanelDto = dataService.getAdminPanelData();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users", adminPanelDto.getUsers());
        model.addAttribute("tariffOptions", adminPanelDto.getTariffOptions());
        model.addAttribute("tariffs", adminPanelDto.getTariffs());
        return adminPath + "adminPanel";
    }

    /**
     * Returns view with list of all users.
     *
     * @param model
     * @return view with list of all users
     */
    @RequestMapping("/adminPanel/allUsers")
    public String adminPanelAllUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users", users);
        return adminPath + "allUsers";
    }

    /**
     * Returns view of all contracts.
     *
     * @param model
     * @return view of all contracts
     */
    @RequestMapping("/adminPanel/allContracts")
    public String adminPanelAllContracts(ModelMap model) {
        List<Contract> contracts = contractService.findAllContracts();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contracts", contracts);
        return adminPath + "allContracts";
    }

    /**
     * Returns view with all tariffs.
     *
     * @param model
     * @return
     */
    @RequestMapping("/adminPanel/allTariffs")
    public String adminPanelAllTariffs(ModelMap model) {
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffs", tariffs);
        return adminPath + "allTariffs";
    }

    /**
     * Returns view with all tariffOptions.
     *
     * @param model
     * @return
     */
    @RequestMapping("/adminPanel/allOptions")
    public String adminPanelAllOptions(ModelMap model) {
        List<TariffOption> tariffOptions = tariffOptionService.findAllTariffOptions();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("options", tariffOptions);
        return adminPath + "allOptions";
    }

    /**
     * Returns view with addUser submit form.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.GET)
    public String addUser(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        AddUserDto addUserDto = new AddUserDto();
        model.addAttribute("addUserDto", addUserDto);
        model.addAttribute("roles", Role.getAllRoles());
        return adminPath + "addUser";
    }

    /**
     * Validates and saves user if data is correct
     *
     * @param addUserDto
     * @param result     validation result
     * @param model
     * @return success page on success or addUser view
     */
    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.POST)
    public String saveUser(@Valid AddUserDto addUserDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return adminPath + "addUser";
        }
        userFormValidator.validate(addUserDto,result);
        if (result.hasErrors()) {
            return adminPath + "addUser";
        }
        userService.addUser(addUserDto);
        model.addAttribute("success", "User " + addUserDto.getFirstName() + " " +
                addUserDto.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return adminPath + "addSuccess";
    }

    /**
     * Returns view with addContract custom form.
     *
     * @param model
     * @return view with addContract custom form
     */
    @RequestMapping(value = "/adminPanel/addContract", method = RequestMethod.GET)
    public String addContract(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        AddContractDto addContractDto = new AddContractDto();
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("contractUserIdDto", addContractDto);
        return adminPath + "addContract";
    }

    /**
     * Returns view with addContract custom form with selected userId
     *
     * @param user_id
     * @param model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addContractToUser/{user_id}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "user_id") Integer user_id, ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        AddContractDto addContractDto = new AddContractDto();
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("user_id", user_id);
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("contractUserIdDto", addContractDto);
        return adminPath + "addContract";
    }

    /**
     * Returns view with addOption custom form with selected userId
     *
     * @param model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.GET)
    public String addOption(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        AddTariffOptionDto addTariffOptionDto = new AddTariffOptionDto();
        model.addAttribute("tariffOption", addTariffOptionDto);
        return adminPath + "addOption";
    }

    /**
     * Validates and saves tariffOption if data is correct
     *
     * @param addTariffOptionDto
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.POST)
    public String saveOption(@Valid AddTariffOptionDto addTariffOptionDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return adminPath + "addOption";
        }
        tariffOptionService.addTariffOption(addTariffOptionDto);
        model.addAttribute("success", "Option " + addTariffOptionDto.getName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return adminPath + "addSuccess";
    }


    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("edit", false);
        return adminPath + "addTariff";
    }

    @RequestMapping(value = "/adminPanel/user/{user_id}")
    public String user(@PathVariable(value = "user_id") Integer user_id, ModelMap model) throws DatabaseException {
        User user = userService.findById(user_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("user", user);
        return adminPath + "user";
    }

    @RequestMapping(value = "/adminPanel/contract/{contract_id}")
    public String contract(@PathVariable(value = "contract_id") Integer contract_id, ModelMap model) throws DatabaseException {
        Contract contract = contractService.findById(contract_id);
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contract", contract);
        return adminPath + "contract";
    }

    @RequestMapping(value = "/adminPanel/tariff/{tariff_id}")
    public String tariff(@PathVariable(value = "tariff_id") Integer tariff_id, ModelMap model) throws DatabaseException {
        Tariff tariff;
        tariff = tariffService.findById(tariff_id);
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariff", tariff);
        return adminPath + "tariff";
    }

    @RequestMapping(value = "/adminPanel/option/{option_id}")
    public String option(@PathVariable(value = "option_id") Integer option_id, ModelMap model) throws DatabaseException {
        TariffOption tariffOption = tariffOptionService.findById(option_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffOption", tariffOption);
        return adminPath + "option";
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
