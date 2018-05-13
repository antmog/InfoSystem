package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.model.enums.TariffOptionRule;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.infosystem.springmvc.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/")
public class AdminDataController {
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
//
//    @Autowired
//    public AdminDataController(UserService userService, ContractService contractService,
//                               TariffOptionService tariffOptionService, MessageSource messageSource,
//                               TariffService tariffService) {
//        this.userService = userService;
//        this.contractService = contractService;
//        this.tariffOptionService = tariffOptionService;
//        this.messageSource = messageSource;
//        this.tariffService = tariffService;
//    }

    /**
     * Called on submit of adding contract (allContracts page and user page).
     *
     * @param addContractDto
     * @param result         validation result
     * @return message
     * @throws LogicException      if pohne number already exists
     * @throws ValidationException if data in fields is not valid (phone number length(min = 6, max = 32))
     */
    @RequestMapping(value = {"/adminPanel/addContract", "/adminPanel/addContractToUser/{user_id}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public String addContract(@Valid @RequestBody AddContractDto addContractDto, BindingResult result) throws LogicException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Enter correct phone number and chose tariff please.");
        }
        contractService.newContract(addContractDto);
        return "Contract added successfully.";
    }

    /**
     * Called on adding tariff (adminPanel and allTariffs pages).
     *
     * @param addTariffDto
     * @param result       validation result
     * @return message
     * @throws LogicException      if tariff with selected name alrdy exists
     * @throws ValidationException if data in fields is not valid (tariff name length(min = 2, max = 32), min price = 1)
     */
    @RequestMapping(value = "/adminPanel/addTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String saveTariff(@Valid @RequestBody AddTariffDto addTariffDto, BindingResult result) throws LogicException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Enter correct name and price for new tariff please.");
        }
        tariffService.addTariff(addTariffDto);
        return "Tariff added successfully.";
    }

    /**
     * Called while searching user by number (adminPanel and allUsers pages).
     *
     * @param searchByNumber
     * @param result         validation result
     * @return message
     * @throws LogicException      if no such number
     * @throws ValidationException if data in fields is not valid (phone number length(min = 6, max = 32))
     */
    @RequestMapping(value = "/adminPanel/user/searchUserByNumber", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public @ResponseBody
    User searchUserByNumber(@Valid @RequestBody SearchByNumber searchByNumber, BindingResult result) throws ValidationException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Incorrect input!");
        }
        return userService.findByPhoneNumber(searchByNumber);
    }

    /**
     * Called on deleting user (user page).
     *
     * @param user_id
     * @return message
     * @throws LogicException    if user still have contracts
     * @throws DatabaseException if user with @user_id doesn't exist
     */
    @RequestMapping(value = "/adminPanel/user/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestBody String user_id) throws LogicException, DatabaseException {
        userService.deleteUserById(Integer.parseInt(user_id));
        return "User (id:" + user_id + ") successfully deleted.";
    }

    /**
     * Called on deleting tariff (tariff page).
     *
     * @param tariff_id
     * @return message
     * @throws LogicException    if tariff is still used
     * @throws DatabaseException if tariff with @tariff_id doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(@RequestBody @Valid String tariff_id) throws DatabaseException, LogicException {
        tariffService.deleteTariffById(Integer.parseInt(tariff_id));
        return "Tariff (id:" + tariff_id + ") successfully deleted.";
    }

    /**
     * Called on deleting contract (tariff page).
     *
     * @param contract_id
     * @return message
     * @throws DatabaseException if contract with @contract_id doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/deleteContract", method = RequestMethod.POST)
    public String deleteContract(@RequestBody String contract_id) throws DatabaseException {
        contractService.deleteContractById(Integer.parseInt(contract_id));
        return "Contract (id:" + contract_id + ") successfully deleted.";
    }


    /**
     * Deleting option.
     *
     * @param option_id
     * @return message
     * @throws DatabaseException if option with @option_id doesn't exist
     * @throws LogicException    if options is still used somewhere.
     */
    @RequestMapping(value = "/adminPanel/option/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestBody String option_id) throws DatabaseException, LogicException {
        tariffOptionService.deleteTariffOptionById(Integer.parseInt(option_id));
        return "Option (id:" + option_id + ") successfully deleted.";
    }

    /**
     * Adding selected options to the tariff.
     *
     * @param editTariffDto
     * @param result        validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffAddOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) throws ValidationException, DatabaseException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        tariffService.addOptions(editTariffDto);
        return "Options added.";
    }

    /**
     * Deleting selected options from the tariff.
     *
     * @param editTariffDto
     * @param result        validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws ValidationException if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffDelOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) throws ValidationException, DatabaseException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        tariffService.delOptions(editTariffDto);
        return "Options deleted.";

    }

    /**
     * Deleting selected options from the contract.
     *
     * @param editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws ValidationException, DatabaseException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        contractService.adminAddOptions(editContractDto);
        return "Options added.";
    }

    /**
     * Adding selected options to the contract.
     *
     * @param editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws ValidationException, DatabaseException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        contractService.adminDelOptions(editContractDto);
        return "Options deleted.";
    }

    /**
     * Changes tariff of current contract to selected.
     *
     * @param switchTariffDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if tariff is not selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/switchTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String switchTariff(@RequestBody @Valid SwitchTariffDto switchTariffDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select tariff.");
        }
        contractService.adminSwitchTariff(switchTariffDto);
        return "Switched to tariff (id:" + switchTariffDto.getTariffId() + ").";
    }


    /**
     * Adding selected options to the tariff.
     *
     * @param tariffOptionRulesDto
     * @param result               validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String optionAddOptions(@RequestBody @Valid TariffOptionRulesDto tariffOptionRulesDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        System.out.println(tariffOptionRulesDto);
        System.out.println(tariffOptionRulesDto);
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto.getTariffOptionId(),
                tariffOptionRulesDto.getTariffOptionDtoList(), TariffOptionRule.valueOf(tariffOptionRulesDto.getRule()));
        return "Options added.";
    }

    /**
     * Deleting selected options from the tariff.
     *
     * @param tariffOptionRulesDto
     * @param result               validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws ValidationException if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String optionDelOptions(@RequestBody @Valid TariffOptionRulesDto tariffOptionRulesDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        tariffOptionService.delRuleTariffOptions(tariffOptionRulesDto.getTariffOptionId(),
                tariffOptionRulesDto.getTariffOptionDtoList(),TariffOptionRule.valueOf(tariffOptionRulesDto.getRule()));
        return "Options deleted.";

    }
}



