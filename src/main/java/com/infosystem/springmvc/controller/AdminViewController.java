package com.infosystem.springmvc.controller;


import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.model.enums.Role;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.DataService.DataService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.validators.TariffOptionFormValidator;
import com.infosystem.springmvc.validators.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AdminViewController extends ViewControllerTemplate{

    public AdminViewController(){
        super("admin/");
    }

    @Autowired
    UserFormValidator userFormValidator;

    @Autowired
    TariffOptionFormValidator tariffOptionFormValidator;

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
        return path + "adminPanel";
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
        return path + "allUsers";
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
        return path + "allContracts";
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
        return path + "allTariffs";
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
        return path + "allOptions";
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
        return path + "addUser";
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
            return path + "addUser";
        }
        userFormValidator.validate(addUserDto, result);
        if (result.hasErrors()) {
            return path + "addUser";
        }
        userService.addUser(addUserDto);
        model.addAttribute("success", "User " + addUserDto.getFirstName() + " " +
                addUserDto.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return path + "addSuccess";
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
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("tariffs", tariffs);
        return path + "addContract";
    }

    /**
     * Returns view with addContract custom form with selected userId
     *
     * @param userId
     * @param model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addContractToUser/{userId}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "userId") Integer userId, ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("userId", userId);
        model.addAttribute("tariffs", tariffs);
        return path + "addContract";
    }

    /**
     * Returns view with addOption custom form with selected userId.
     *
     * @param model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.GET)
    public String addOption(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        AddTariffOptionDto addTariffOptionDto = new AddTariffOptionDto();
        model.addAttribute("addTariffOptionDto", addTariffOptionDto);
        return path + "addOption";
    }

    /**
     * Validates and saves tariffOption if data is correct.
     *
     * @param addTariffOptionDto
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.POST)
    public String saveOption(@Valid AddTariffOptionDto addTariffOptionDto, BindingResult result, ModelMap model) {
        tariffOptionFormValidator.validate(addTariffOptionDto, result);
        if (result.hasErrors()) {
            return path + "addOption";
        }
        tariffOptionService.addTariffOption(addTariffOptionDto);
        model.addAttribute("success", "Option " + addTariffOptionDto.getName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return path + "addSuccess";
    }

    /**
     * Returns view with addTariff custom form.
     *
     * @param model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<TariffOption> tariffOptions = tariffOptionService.findAllTariffOptions();
        model.addAttribute("options", tariffOptions);
        return path + "addTariff";
    }

    /**
     * Returns user page view.
     * @param userId
     * @param model
     * @return error page view if user doesn't exist
     */
    @RequestMapping(value = "/adminPanel/user/{userId}")
    public String user(@PathVariable(value = "userId") Integer userId, ModelMap model)  {
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("user", user);
        return path + "user";
    }

    /**
     * Returns contract page view.
     * @param contractId
     * @param model
     * @return error page view if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") Integer contractId, ModelMap model)  {
        ContractPageDto contractPageDto;
        try {
            contractPageDto = dataService.getContractPageData(contractId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("contractPageDto", contractPageDto);
        model.addAttribute("loggedinuser", getPrincipal());
        return path + "contract";
    }

    /**
     * Returns tariff page view.
     * @param tariffId
     * @param model
     * @return error page view if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/{tariffId}")
    public String tariff(@PathVariable(value = "tariffId") Integer tariffId, ModelMap model){
        TariffPageDto tariffPageDto;
        try {
            tariffPageDto = dataService.getTariffPageData(tariffId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffPageDto", tariffPageDto);
        return path + "tariff";
    }

    /**
     * Returns option page view.
     * @param optionId
     * @param model
     * @return error page view if option doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/{optionId}")
    public String option(@PathVariable(value = "optionId") Integer optionId, ModelMap model) {
        TariffOptionPageDto tariffOptionPageDto;
        try {
            tariffOptionPageDto = dataService.getTariffOptionPageData(optionId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffOptionPageDto", tariffOptionPageDto);
        return path + "option";
    }




}
