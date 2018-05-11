package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.User;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.infosystem.springmvc.service.UserService;
import sun.rmi.runtime.Log;

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
     * This method is called on submit of adding contract from allContracts page and user page.
     */
    @RequestMapping(value = {"/adminPanel/addContract", "/adminPanel/addContractToUser/{user_id}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public String addContract(@Valid @RequestBody AddContractDto addContractDto, BindingResult result) throws LogicException {
        if(result.hasErrors()){
            throw new LogicException("Enter phone number and chose tariff please.");
        }
        contractService.newContract(addContractDto);
        return "Contract added successfully.";
    }

    /**
     * This method is called on adding tariff from adminPanel and allTariffs pages.
     */
    @RequestMapping(value = "/adminPanel/addTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String saveTariff(@RequestBody AddTariffDto addTariffDto) {
        tariffService.saveTariff(addTariffDto);
        return "ok";
    }

    /**
     * This method is called while searching user by number from adminPanel and allUsers pages.
     */
    @RequestMapping(value = "/adminPanel/user/searchUserByNumber", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public @ResponseBody
    User searchUserByNumber(@Valid @RequestBody SearchByNumber searchByNumber, BindingResult result) {
        return userService.findByPhoneNumber(searchByNumber);
    }

    /**
     * This method is called on deleting user from user page.
     */
    @RequestMapping(value = "/adminPanel/user/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestBody @Valid String user_id, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        return userService.deleteUserById(Integer.parseInt(user_id));
    }

    /**
     * This method is called on deleting tariff from tariff page.
     */
    @RequestMapping(value = "/adminPanel/tariff/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(@RequestBody @Valid String tariff_id, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        return tariffService.deleteTariffById(Integer.parseInt(tariff_id));
    }

    /**
     * This method is adding selected options to the tariff.
     */
    @RequestMapping(value = "/adminPanel/tariff/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffAddOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) {
        tariffService.addOptions(editTariffDto);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    /**
     * This method is deleting selected options from the tariff.
     */
    @RequestMapping(value = "/adminPanel/tariff/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffDelOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) {
        tariffService.delOptions(editTariffDto);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/adminPanel/contract/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptions(@RequestBody @Valid ContractOptionsDto contractOptionsDto, BindingResult result) {
        contractService.adminAddOptions(contractOptionsDto);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/adminPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid ContractOptionsDto contractOptionsDto, BindingResult result) {
        contractService.adminDelOptions(contractOptionsDto);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }




    @RequestMapping(value = "/adminPanel/contract/switchTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String switchTariff(@RequestBody @Valid SwitchTariffDto switchTariffDto, BindingResult result) {
        //check if user auth is admin if unblock (protection agaisnt HACKERS!!!11 (direct requests) )
        contractService.adminSwitchTariff(switchTariffDto);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/adminPanel/contract/deleteContract", method = RequestMethod.POST)
    public String deleteContract(@RequestBody @Valid String contract_id, BindingResult result) {
        contractService.deleteContractById(Integer.parseInt(contract_id));
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }



    @RequestMapping(value = "/adminPanel/option/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestBody @Valid String option_id, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        return tariffOptionService.deleteTariffOptionById(Integer.parseInt(option_id));
    }






}

