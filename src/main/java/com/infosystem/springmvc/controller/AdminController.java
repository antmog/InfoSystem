package com.infosystem.springmvc.controller;


import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.enums.Role;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.validators.EditUserValidator;
import com.infosystem.springmvc.validators.TariffOptionFormValidator;
import com.infosystem.springmvc.validators.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AdminController extends ControllerTemplate {

    private final int ITEMS_PER_PAGE = 10;

    private final UserFormValidator userFormValidator;
    private final TariffOptionFormValidator tariffOptionFormValidator;
    private final EditUserValidator editUserValidator;
    private final UserService userService;
    private final DataService dataService;
    private final ContractService contractService;
    private final TariffOptionService tariffOptionService;
    private final TariffService tariffService;

    @Autowired
    public AdminController(UserFormValidator userFormValidator, TariffOptionFormValidator tariffOptionFormValidator,
                           EditUserValidator editUserValidator, UserService userService, DataService dataService, ContractService contractService,
                           TariffOptionService tariffOptionService, TariffService tariffService) {
        super("admin/");
        this.userFormValidator = userFormValidator;
        this.tariffOptionFormValidator = tariffOptionFormValidator;
        this.editUserValidator = editUserValidator;
        this.userService = userService;
        this.dataService = dataService;
        this.contractService = contractService;
        this.tariffOptionService = tariffOptionService;
        this.tariffService = tariffService;
    }

    /**
     * Returns view of main admin panel.
     *
     * @param model model
     * @return view of main admin panel
     */
    @RequestMapping("/adminPanel")
    public String adminPanel(ModelMap model) {
        UserDto userDto = null;
        try {
            userDto = dataService.getAdminPanelData(getPrincipal());
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("userDto", userDto);
        return path + "adminPanel";
    }

    /**
     * Returns view with list of all users.
     *
     * @param model model
     * @return view with list of all users
     */
    @RequestMapping("/adminPanel/allUsers/{pageNumber}")
    public String adminPanelAllUsers(@PathVariable(value = "pageNumber") Integer pageNumber, ModelMap model) {
        AllEntitiesDto<UserDto> allUsersDto = dataService.getAllEntityPageData(UserDto.class, pageNumber, ITEMS_PER_PAGE);
        if (illegalPage(allUsersDto.getPageCount(), pageNumber, model)) {
            return prepareErrorPage(model, "No such page.");
        }
        allUsersDto.setPageNumber(pageNumber);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("allUsersDto", allUsersDto);
        return path + "allUsers";
    }

    /**
     * Returns view of all contracts.
     *
     * @param model model
     * @return view of all contracts
     */
    @RequestMapping("/adminPanel/allContracts/{pageNumber}")
    public String adminPanelAllContracts(@PathVariable(value = "pageNumber") Integer pageNumber, ModelMap model) {
        AllEntitiesDto<ContractDto> allContractsDto = dataService.getAllEntityPageData(ContractDto.class, pageNumber, ITEMS_PER_PAGE);
        if (illegalPage(allContractsDto.getPageCount(), pageNumber, model)) {
            return prepareErrorPage(model, "No such page.");
        }
        allContractsDto.setPageNumber(pageNumber);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("allContractsDto", allContractsDto);
        return path + "allContracts";
    }

    /**
     * Returns view with all tariffs.
     *
     * @param model model
     * @return view of all tariffs
     */
    @RequestMapping("/adminPanel/allTariffs/{pageNumber}")
    public String adminPanelAllTariffs(@PathVariable(value = "pageNumber") Integer pageNumber, ModelMap model) {
        AllEntitiesDto<TariffDto> allTariffsDto = dataService.getAllEntityPageData(TariffDto.class, pageNumber, ITEMS_PER_PAGE);
        if (illegalPage(allTariffsDto.getPageCount(), pageNumber, model)) {
            return prepareErrorPage(model, "No such page.");
        }
        allTariffsDto.setPageNumber(pageNumber);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("allTariffsDto", allTariffsDto);
        return path + "allTariffs";
    }

    /**
     * Returns view with all tariffOptions.
     *
     * @param model model
     * @return view of all options
     */
    @RequestMapping("/adminPanel/allOptions/{pageNumber}")
    public String adminPanelAllOptions(@PathVariable(value = "pageNumber") Integer pageNumber, ModelMap model) {
        AllEntitiesDto<TariffOptionDtoShort> allTariffOptionsDto =
                dataService.getAllEntityPageData(TariffOptionDtoShort.class, pageNumber, ITEMS_PER_PAGE);
        if (illegalPage(allTariffOptionsDto.getPageCount(), pageNumber, model)) {
            return prepareErrorPage(model, "No such page.");
        }
        allTariffOptionsDto.setPageNumber(pageNumber);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("allTariffOptionsDto", allTariffOptionsDto);
        return path + "allOptions";
    }

    /**
     * Returns view with addUser submit form.
     *
     * @param model model
     * @return add user page
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
     * @param addUserDto addUserDto
     * @param result     validation result
     * @param model      model
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
     * @param model model
     * @return view with addContract custom form
     */
    @RequestMapping(value = "/adminPanel/addContract", method = RequestMethod.GET)
    public String addContract(ModelMap model) {
        List<TariffDto> tariffDtoList = dataService.findAllActiveTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffs", tariffDtoList);
        return path + "addContract";
    }

    /**
     * Returns view with addContract custom form with selected userId
     *
     * @param userId userId
     * @param model  model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addContractToUser/{userId}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "userId") Integer userId, ModelMap model) {
        List<TariffDto> tariffDtoList = dataService.findAllActiveTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("userId", userId);
        model.addAttribute("tariffs", tariffDtoList);
        return path + "addContract";
    }

    /**
     * Returns view with addOption custom form with selected userId.
     *
     * @param model model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.GET)
    public String addOption(ModelMap model) {
        AddTariffOptionDto addTariffOptionDto = new AddTariffOptionDto();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("addTariffOptionDto", addTariffOptionDto);
        return path + "addOption";
    }

    /**
     * Validates and saves tariffOption if data is correct.
     *
     * @param addTariffOptionDto addTariffOptionDto
     * @param result             validation result
     * @param model              model
     * @return result
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
     * @param model model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        List<TariffOptionDtoShort> tariffOptionDtoShortList = dataService.findAllTariffOptions();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("options", tariffOptionDtoShortList);
        return path + "addTariff";
    }

    //todo
    /**
     * Returns user page view.
     *
     * @param userId userId
     * @param model  model
     * @return error page view if user doesn't exist
     */
    @RequestMapping(value = "/adminPanel/user/{userId}")
    public String user(@PathVariable(value = "userId") Integer userId, ModelMap model) {
        UserPageDto userPageDto;
        try {
            userPageDto = dataService.getUserPageDto(userId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("userPageDto", userPageDto);
        return path + "user";
    }

    /**
     * Returns contract page view.
     *
     * @param contractId contractId
     * @param model      model
     * @return error page view if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") Integer contractId, ModelMap model) {
        ContractPageDto contractPageDto;
        try {
            contractPageDto = dataService.getContractPageData(contractId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("contractPageDto", contractPageDto);
        model.addAttribute("loggedinuser", getPrincipal());
        return path + "contract";
    }

    /**
     * Returns tariff page view.
     *
     * @param tariffId tariffId
     * @param model    model
     * @return error page view if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/{tariffId}")
    public String tariff(@PathVariable(value = "tariffId") Integer tariffId, ModelMap model) {
        TariffPageDto tariffPageDto;
        try {
            tariffPageDto = dataService.getTariffPageData(tariffId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffPageDto", tariffPageDto);
        return path + "tariff";
    }

    /**
     * Returns option page view.
     *
     * @param optionId optionId
     * @param model    model
     * @return error page view if option doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/{optionId}")
    public String option(@PathVariable(value = "optionId") Integer optionId, ModelMap model) {
        TariffOptionPageDto tariffOptionPageDto;
        try {
            tariffOptionPageDto = dataService.getTariffOptionPageData(optionId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffOptionPageDto", tariffOptionPageDto);
        return path + "option";
    }

    /**
     * Returns addFunds view.
     *
     * @return view
     */
    @RequestMapping("/adminPanel/addFunds/{userId}")
    public String addFunds(@PathVariable(value = "userId") Integer userId, ModelMap model) {
        AdminFundsDto adminFundsDto = new AdminFundsDto();
        try {
            adminFundsDto = dataService.getAdminAddPageData(userId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("adminFundsDto", adminFundsDto);
        return path + "addFunds";
    }

    /**
     * Returns addFunds view.
     *
     * @return view
     */
    @RequestMapping("/adminPanel/editUser{userId}")
    public String editUser(@PathVariable(value = "userId") Integer userId, ModelMap model) {
        EditUserDto editUserDto = new EditUserDto();
        try {
            editUserDto = dataService.getEditUserData(userId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("editUserDto", editUserDto);
        return path + "editUser";
    }

    /**
     * Validates and saves user data if it is correct.
     *
     * @param editUserDto editUserDto
     * @param result             validation result
     * @param model              model
     * @return result
     */
    @RequestMapping(value = "/adminPanel/editUser{userId}", method = RequestMethod.POST)
    public String editUserSubmit(@Valid EditUserDto editUserDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return path + "editUser";
        }
        editUserValidator.validate(editUserDto, result);
        if (result.hasErrors()) {
            return path + "editUser";
        }
        try {
            userService.editUser(editUserDto);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("success", "User " + editUserDto.getFirstName() + " " +
                editUserDto.getLastName()+" edited successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return path + "addSuccess";
    }

    private boolean illegalPage(int pagesCount, int pageNumber, ModelMap model) {
        return pageNumber > pagesCount || pageNumber < 1;
    }

}
