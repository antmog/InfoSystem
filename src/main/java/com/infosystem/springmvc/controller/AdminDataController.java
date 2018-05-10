package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
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

    @RequestMapping(value = "/adminPanel/tariff/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffAddOptions(@RequestBody @Valid GetTarifAsJsonDtoById getTarifAsJsonDtoById, BindingResult result) {
        tariffService.addOptions(getTarifAsJsonDtoById);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/adminPanel/tariff/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String tariffDelOptions(@RequestBody @Valid GetTarifAsJsonDtoById getTarifAsJsonDtoById, BindingResult result) {
        tariffService.delOptions(getTarifAsJsonDtoById);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/adminPanel/contract/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptions(@RequestBody @Valid GetContractAsJsonDtoById getContractAsJsonDtoById, BindingResult result) {
        contractService.adminAddOptions(getContractAsJsonDtoById);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/adminPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid GetContractAsJsonDtoById getContractAsJsonDtoById, BindingResult result) {
        contractService.adminDelOptions(getContractAsJsonDtoById);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }






    @RequestMapping(value = "/adminPanel/user/searchUserByNumber", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public @ResponseBody
    User searchUserByNumber(@Valid @RequestBody SearchUserByNumber searchUserByNumber, BindingResult result) {
        return userService.findByPhoneNumber(searchUserByNumber);
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

    @RequestMapping(value = "/adminPanel/user/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestBody @Valid String user_id, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        return userService.deleteUserById(Integer.parseInt(user_id));
    }

    @RequestMapping(value = "/adminPanel/user/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(@RequestBody @Valid String tariff_id, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        return tariffService.deleteTariffById(Integer.parseInt(tariff_id));
    }

    @RequestMapping(value = "/adminPanel/option/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestBody @Valid String option_id, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        return tariffOptionService.deleteTariffOptionById(Integer.parseInt(option_id));
    }

    @RequestMapping(value = {"/adminPanel/addContract","/adminPanel/addContractToUser/{user_id}"},consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public String saveContract(@RequestBody @Valid  ContractUserIdDto contractUserIdDto, BindingResult result) {
        //+check unique
        if (result.hasErrors()) {
            return "notok";
        }
        contractService.newContract(contractUserIdDto);
        return "ok";
    }


    @RequestMapping(value = "/adminPanel/addTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String saveTariff(@RequestBody @Valid GetTarifAsJsonDto getTarifAsJsonDto, BindingResult result) {

        if (result.hasErrors()) {
            return "notok";
        }
        tariffService.saveTariff(getTarifAsJsonDto);
        return "ok";
    }

}

