package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.ContractPageDto;
import com.infosystem.springmvc.dto.UserDto;
import com.infosystem.springmvc.dto.UserFundsDto;
import com.infosystem.springmvc.dto.UserPageDto;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.validators.EditUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class CustomerController extends ControllerTemplate {

    public final UserService userService;
    private final DataService dataService;
    private final EditUserValidator editUserValidator;

    @Autowired
    public CustomerController(UserService userService, DataService dataService, EditUserValidator editUserValidator) {
        super("customer/");
        this.userService = userService;
        this.dataService = dataService;
        this.editUserValidator = editUserValidator;
    }

    /**
     * Returns customer panel view with user info.
     *
     * @param model model
     * @return view
     */
    @RequestMapping("/customerPanel")
    public String customerPanel(ModelMap model) {
        String login = getPrincipal();
        UserPageDto userPageDto;
        try {
            userPageDto = dataService.getCustomerPageData(login);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", login);
        model.addAttribute("userPageDto", userPageDto);
        return path + "customerPanel";
    }

    /**
     * Returns view with contract page.
     *
     * @param contractId contractId
     * @param model model
     * @return view
     */
    @RequestMapping(value = "/customerPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") Integer contractId, ModelMap model) {
        ContractPageDto contractPageDto;
        try {
            contractPageDto = dataService.getContractPageData(contractId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contractPageDto", contractPageDto);
        return path + "contract";
    }

    /**
     * Returns view with cart page.
     *
     * @return view
     */
    @RequestMapping(value = "/customerPanel/cart")
    public String cart(ModelMap model) {
        String login = getPrincipal();
        UserDto userDto = new UserDto();
        try {
            userDto = dataService.getUserInfo(login);
        } catch (DatabaseException e) {
            prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", userDto);
        return path + "cart";
    }

    /**
     * Returns addFunds view.
     *
     * @return view
     */
    @RequestMapping("/customerPanel/addFunds")
    public String addFunds(ModelMap model) {
        String login = getPrincipal();
        UserFundsDto userFundsDto = new UserFundsDto();
        try {
            userFundsDto = dataService.getUserAddFundsData(login);
        } catch (DatabaseException e) {
            prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", userFundsDto);
        return path + "addFunds";
    }

    /**
     * @param userId userId
     * @param model model
     * @return edit user view
     */
    @RequestMapping("/customerPanel/editUser{userId}")
    public String editUser(@PathVariable(value = "userId") Integer userId, ModelMap model) {
        EditUserDto editUserDto;
        try {
            editUserDto = dataService.getEditUserData(userId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("editUserDto", editUserDto);
        return "customerEditUser";
    }

    /**
     * Validates and saves user data if it is correct.
     *
     * @param editUserDto editUserDto
     * @param result             validation result
     * @param model              model
     * @return result
     */
    @RequestMapping(value = "/customerPanel/editUser{userId}", method = RequestMethod.POST)
    public String editUserSubmit(@Valid EditUserDto editUserDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return path + "customerEditUser";
        }
        editUserValidator.validate(editUserDto, result);
        if (result.hasErrors()) {
            return path + "customerEditUser";
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
}
