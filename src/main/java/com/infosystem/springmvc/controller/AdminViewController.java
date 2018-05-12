package com.infosystem.springmvc.controller;


import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.*;
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
        model.addAttribute("user", addUserDto);
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
     * @param user_id
     * @param model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addContractToUser/{user_id}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "user_id") Integer user_id, ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<Tariff> tariffs = tariffService.findAllActiveTariffs();
        model.addAttribute("user_id", user_id);
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
     * @param user_id
     * @param model
     * @return error page view if user doesn't exist
     */
    @RequestMapping(value = "/adminPanel/user/{user_id}")
    public String user(@PathVariable(value = "user_id") Integer user_id, ModelMap model)  {
        User user = null;
        try {
            user = userService.findById(user_id);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("user", user);
        return path + "user";
    }

    /**
     * Returns contract page view.
     * @param contract_id
     * @param model
     * @return error page view if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/{contract_id}")
    public String contract(@PathVariable(value = "contract_id") Integer contract_id, ModelMap model)  {
        ContractPageDto contractPageDto;
        try {
            contractPageDto = dataService.getContractPageData(contract_id);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("contractPageDto", contractPageDto);
        model.addAttribute("loggedinuser", getPrincipal());
        return path + "contract";
    }

    /**
     * Returns tariff page view.
     * @param tariff_id
     * @param model
     * @return error page view if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/{tariff_id}")
    public String tariff(@PathVariable(value = "tariff_id") Integer tariff_id, ModelMap model){
        TariffPageDto tariffPageDto;
        try {
            tariffPageDto = dataService.getTariffPageData(tariff_id);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffPageDto", tariffPageDto);
        return path + "tariff";
    }

    /**
     * Returns option page view.
     * @param option_id
     * @param model
     * @return error page view if option doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/{option_id}")
    public String option(@PathVariable(value = "option_id") Integer option_id, ModelMap model) {
        TariffOption tariffOption;
        try {
            tariffOption = tariffOptionService.findById(option_id);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffOption", tariffOption);
        return path + "option";
    }




}
