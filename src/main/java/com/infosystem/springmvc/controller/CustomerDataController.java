package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.DeleteFromCartDto;
import com.infosystem.springmvc.dto.EditContractDto;
import com.infosystem.springmvc.dto.SessionCart;
import com.infosystem.springmvc.dto.SwitchTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class CustomerDataController {

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

    @Autowired
    SessionCart sessionCart;


    /**
     * Changes tariff of current contract to selected.
     *
     * @param switchTariffDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if tariff is not selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/switchTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String switchTariff(@RequestBody @Valid SwitchTariffDto switchTariffDto, BindingResult result) throws DatabaseException, ValidationException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select tariff.");
        }
        contractService.customerSwitchTariff(switchTariffDto);
        return "Switched to tariff (id:" + switchTariffDto.getTariffId() + ").";
    }

    /**
     * Adding selected options to cart.
     *
     * @param editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/addOptionsToCart", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptionsToCart(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws DatabaseException, ValidationException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        sessionCart.addCartItems(editContractDto);
        return "Options added to cart.";
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
    @RequestMapping(value = "/customerPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws DatabaseException, ValidationException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        contractService.customerDelOptions(editContractDto);
        return "Options deleted.";
    }

    /**
     * Deleting selected options from cart.
     *
     * @param deleteFromCartDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/delOptionsFromCart", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptionsFromCart(@RequestBody @Valid DeleteFromCartDto deleteFromCartDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong delete data.");
        }
        sessionCart.delCartItems(deleteFromCartDto);
        return "Options deleted from cart.";
    }

    /**
     * Adding options to contract (buying).
     *
     * @return message
     * @throws LogicException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/addOptions")
    public String contractAddOptions() throws DatabaseException, LogicException {
        contractService.customerAddOptions();
        return "Bought all successfully.";
    }

}
