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

    private final UserService userService;
    private final ContractService contractService;
    private final TariffOptionService tariffOptionService;
    private final TariffService tariffService;

    @Autowired
    public AdminDataController(UserService userService, ContractService contractService,
                               TariffOptionService tariffOptionService, TariffService tariffService) {
        this.userService = userService;
        this.contractService = contractService;
        this.tariffOptionService = tariffOptionService;
        this.tariffService = tariffService;
    }

    /**
     * Called on submit of adding contract (allContracts page and user page).
     *
     * @param addContractDto addContractDto
     * @param result         validation result
     * @return message
     * @throws LogicException      if pohne number already exists
     * @throws ValidationException if data in fields is not valid (phone number length(min = 6, max = 32))
     */
    @RequestMapping(value = {"/adminPanel/addContract", "/adminPanel/addContractToUser/{userId}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public String addContract(@Valid @RequestBody AddContractDto addContractDto, BindingResult result) throws LogicException, ValidationException, DatabaseException {
        if (result.hasErrors()) {
            throw new ValidationException("Enter correct phone number and chose tariff please.");
        }
        contractService.newContract(addContractDto);
        return "Contract added successfully.";
    }

    /**
     * Called on adding tariff (adminPanel and allTariffs pages).
     *
     * @param addTariffDto addTariffDto
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
     * @param searchByNumber searchByNumber
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
     * @param userId userId
     * @return message
     * @throws LogicException    if user still have contracts
     * @throws DatabaseException if user with @userId doesn't exist
     */
    @RequestMapping(value = "/adminPanel/user/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestBody String userId) throws LogicException, DatabaseException {
        userService.deleteUserById(Integer.parseInt(userId));
        return "User (id:" + userId + ") successfully deleted.";
    }

    /**
     * Called on deleting tariff (tariff page).
     *
     * @param tariffId tariffId
     * @return message
     * @throws LogicException    if tariff is still used
     * @throws DatabaseException if tariff with @tariffId doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(@RequestBody @Valid String tariffId) throws DatabaseException, LogicException {
        tariffService.deleteTariffById(Integer.parseInt(tariffId));
        return "Tariff (id:" + tariffId + ") successfully deleted.";
    }

    /**
     * Called on deleting contract (tariff page).
     *
     * @param contractId contractId
     * @return message
     * @throws DatabaseException if contract with @contractId doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/deleteContract", method = RequestMethod.POST)
    public String deleteContract(@RequestBody String contractId) throws DatabaseException {
        contractService.deleteContractById(Integer.parseInt(contractId));
        return "Contract (id:" + contractId + ") successfully deleted.";
    }


    /**
     * Deleting option.
     *
     * @param optionId optionId
     * @return message
     * @throws DatabaseException if option with @optionId doesn't exist
     * @throws LogicException    if options is still used somewhere.
     */
    @RequestMapping(value = "/adminPanel/option/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestBody String optionId) throws DatabaseException, LogicException {
        tariffOptionService.deleteTariffOptionById(Integer.parseInt(optionId));
        return "Option (id:" + optionId + ") successfully deleted.";
    }

    /**
     * Adding selected options to the tariff.
     *
     * @param editTariffDto editTariffDto
     * @param result        validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffAddOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        tariffService.addOptions(editTariffDto);
        return "Options added.";
    }

    /**
     * Deleting selected options from the tariff.
     *
     * @param editTariffDto editTariffDto
     * @param result        validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws ValidationException if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffDelOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        tariffService.delOptions(editTariffDto);
        return "Options deleted.";

    }

    /**
     * Deleting selected options from the contract.
     *
     * @param editContractDto editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        contractService.adminAddOptions(editContractDto);
        return "Options added.";
    }

    /**
     * Adding selected options to the contract.
     *
     * @param editContractDto editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        contractService.adminDelOptions(editContractDto);
        return "Options deleted.";
    }

    /**
     * Changes tariff of current contract to selected.
     *
     * @param switchTariffDto switchTariffDto
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
     * @param tariffOptionRulesDto tariffOptionRulesDto
     * @param result               validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String optionAddOptions(@RequestBody @Valid TariffOptionRulesDto tariffOptionRulesDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);
        return "Options added.";
    }

    /**
     * Deleting selected options from the tariff.
     *
     * @param tariffOptionRulesDto tariffOptionRulesDto
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
        tariffOptionService.delRuleTariffOptions(tariffOptionRulesDto);
        return "Options deleted.";

    }
}



