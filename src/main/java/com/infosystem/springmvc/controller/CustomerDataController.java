package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
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
public class CustomerDataController extends ControllerTemplate {

    private final UserService userService;

    private final ContractService contractService;

    private final SessionCart sessionCart;

    @Autowired
    public CustomerDataController(UserService userService, ContractService contractService, SessionCart sessionCart) {
        this.userService = userService;
        this.contractService = contractService;
        this.sessionCart = sessionCart;
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
     * @param editContractDto editContractDto
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
     * @param editContractDto editContractDto
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
     * @param deleteFromCartDto deleteFromCartDto
     * @param result            validation result
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
     * @throws LogicException    if no options selected
     * @throws DatabaseException if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String addOptions(@Valid @RequestBody AddOptionsDto addOptionsDto, BindingResult result) throws DatabaseException, LogicException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong input.");
        }
        contractService.customerAddOptions(addOptionsDto);
        return "Bought all successfully.";
    }

    /**
     * Adding funds to user.
     *
     * @return message
     * @throws ValidationException if amount value is null
     * @throws DatabaseException   if user doesn't exist
     */
    @RequestMapping(value = "/customerPanel/addFunds", method = RequestMethod.POST)
    public String addFunds(@RequestBody @Valid FundsDto fundsDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Chose the amount of money you want to add.");
        }
        userService.addFunds(fundsDto, getPrincipal());
        return fundsDto.getAmount() + " funds added.";
    }

    /**
     * Adding funds to user.
     *
     * @return message
     * @throws ValidationException if amount value is null
     * @throws DatabaseException   if user doesn't exist
     */
    @RequestMapping(value = "/customerPanel/getBalance", method = RequestMethod.POST)
    public String getBalance(@RequestBody @Valid GetBalanceDto getBalanceDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong input!");
        }
        return userService.getBalance(getBalanceDto);
    }

    /**
     * Adding funds to user.
     *
     * @return message
     */
    @RequestMapping(value = "/customerPanel/getCartItemsCount", method = RequestMethod.GET)
    public String getCartItemsCount() {
        return String.valueOf(sessionCart.getCount());
    }
}
