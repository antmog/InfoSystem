package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.CustomerEditUserDto;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.validators.ChangePasswordValidator;
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
public class CustomerController extends ControllerTemplate {

    public final UserService userService;
    private final DataService dataService;
    private final EditUserValidator editUserValidator;
    private final ChangePasswordValidator changePasswordValidator;
    private final CustomModelMapper customModelMapper;

    @Autowired
    public CustomerController(UserService userService, DataService dataService, EditUserValidator editUserValidator, ChangePasswordValidator changePasswordValidator, CustomModelMapper customModelMapper) {
        super("customer/");
        this.userService = userService;
        this.dataService = dataService;
        this.editUserValidator = editUserValidator;
        this.changePasswordValidator = changePasswordValidator;
        this.customModelMapper = customModelMapper;
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
        model.addAttribute("userPageDto", userPageDto);
        return path + "customerPanel";
    }

    /**
     * Returns view with contract page.
     *
     * @param contractId contractId
     * @param model      model
     * @return view
     */
    @RequestMapping(value = "/customerPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") String contractId, ModelMap model) {
        if (!pathVariableIsANumber(contractId)) {
            return prepareErrorPage(model, "Wrong path variable");
        }
        int contractIdInt = Integer.parseInt(contractId);
        ContractPageDto contractPageDto;
        try {
            contractPageDto = dataService.getContractPageData(contractIdInt);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
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
        model.addAttribute("user", userFundsDto);
        return path + "addFunds";
    }

    /**
     * @param userId userId
     * @param model  model
     * @return edit user view
     */
    @RequestMapping("/customerPanel/editUser/{userId}")
    public String editUser(@PathVariable(value = "userId") String userId, ModelMap model) {
        if (!pathVariableIsANumber(userId)) {
            return prepareErrorPage(model, "Wrong path variable.");
        }
        int userIdInt = Integer.parseInt(userId);
        CustomerEditUserDto customerEditUserDto;
        try {
            customerEditUserDto = dataService.getEditUserData(userIdInt);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("customerEditUserDto", customerEditUserDto);
        return path + "customerEditUser";
    }

    /**
     * Validates and saves user data if it is correct.
     *
     * @param customerEditUserDto customerEditUserDto
     * @param result      validation result
     * @param model       model
     * @return result
     */
    @RequestMapping(value = "/customerPanel/editUser/{userId}", method = RequestMethod.POST)
    public String editUserSubmit(@PathVariable(value = "userId") String userId, @Valid CustomerEditUserDto customerEditUserDto, BindingResult result, ModelMap model) {
        if (!pathVariableIsANumber(userId)) {
            return prepareErrorPage(model, "Wrong path variable.");
        }
        if (result.hasErrors()) {
            return path + "customerEditUser";
        }
        editUserValidator.validate(customModelMapper.mapToDto(EditUserDto.class, customerEditUserDto), result);
        if (result.hasErrors()) {
            return path + "customerEditUser";
        }
        try {
            userService.editUser(customModelMapper.mapToDto(EditUserDto.class, customerEditUserDto));
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("success", "User " + customerEditUserDto.getFirstName() + " " +
                customerEditUserDto.getLastName() + " edited successfully");
        return path + "addSuccess";
    }

    /**
     * @param userId userId
     * @param model  model
     * @return edit user view
     */
    @RequestMapping("/customerPanel/editUser/changePassword/{userId}")
    public String changePassword(@PathVariable(value = "userId") String userId, ModelMap model) {
        if (!pathVariableIsANumber(userId)) {
            return prepareErrorPage(model, "Wrong path variable.");
        }
        int userIdInt = Integer.parseInt(userId);
        ChangePasswordDto changePasswordDto;
        try {
            changePasswordDto = dataService.getChangePasswordData(userIdInt);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("changePasswordDto", changePasswordDto);
        return path + "customerChangePassword";
    }

    /**
     * Validates and saves user data if it is correct.
     *
     * @param changePasswordDto changePasswordDto
     * @param result            validation result
     * @param model             model
     * @return result
     */
    @RequestMapping(value = "/customerPanel/editUser/changePassword/{userId}", method = RequestMethod.POST)
    public String changePasswordSubmit(@PathVariable(value = "userId") String userId, @Valid ChangePasswordDto changePasswordDto, BindingResult result, ModelMap model) {
        if (!pathVariableIsANumber(userId)) {
            return prepareErrorPage(model, "Wrong path variable.");
        }
        if (result.hasErrors()) {
            return path + "customerChangePassword";
        }
        changePasswordValidator.validate(changePasswordDto, result);
        if (result.hasErrors()) {
            return path + "customerChangePassword";
        }
        try {
            userService.editUser(changePasswordDto);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("success", "Password changed successfully");
        return path + "addSuccess";
    }
}
