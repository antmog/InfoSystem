package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.ForgotPasswordDto;
import com.infosystem.springmvc.dto.ResponseDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.dto.TariffOptionDtoShort;
import com.infosystem.springmvc.dto.editUserDto.EditAddressDto;
import com.infosystem.springmvc.dto.editUserDto.EditMailDto;
import com.infosystem.springmvc.dto.editUserDto.EditPassportDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.service.*;
import com.infosystem.springmvc.service.security.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.TreeSet;


@RestController
public class GlobalDataController {

    private final UserService userService;

    private final ContractService contractService;

    private final TariffService tariffService;

    @Autowired
    SmsService smsService;

    @Autowired
    public GlobalDataController(UserService userService, ContractService contractService, TariffOptionService tariffOptionService, MessageSource messageSource, TariffService tariffService) {
        this.userService = userService;
        this.contractService = contractService;
        this.tariffService = tariffService;
    }

    /**
     * Send sms with new password.
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public ResponseDto sendSms(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto, BindingResult result) throws ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong phone number format.");
        }
        smsService.sendNewPassword(forgotPasswordDto.getPhoneNumber());
        return new ResponseDto("ok");
    }

    /**
     * Returns options available for tariff.
     *
     * @param tariffId tariffId
     * @param result   validation result
     * @return message
     * @throws DatabaseException   if tariff doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/tariffOptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    TreeSet<TariffOptionDtoShort> tariffOptions(@RequestBody @NotNull String tariffId, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong tariff ID!";
            throw new ValidationException(exceptionMessage);
        }
        return tariffService.getAvailableOptionsForTariff(Integer.parseInt(tariffId));
    }

    /**
     * Set new status for contract.
     *
     * @param setNewStatusDto setNewStatusDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if contract doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/contract/setStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String setContractStatus(@RequestBody @Valid SetNewStatusDto setNewStatusDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong contract id or status!";
            throw new ValidationException(exceptionMessage);
        }
        contractService.setStatus(setNewStatusDto);
        return "Contract is now " + setNewStatusDto.getEntityStatus() + ".";
    }

    /**
     * Set new status for user.
     *
     * @param setNewStatusDto setNewStatusDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/setStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String setUserStatus(@RequestBody @Valid SetNewStatusDto setNewStatusDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong contract id or status!";
            throw new ValidationException(exceptionMessage);
        }
        userService.setStatus(setNewStatusDto);
        return "User is now " + setNewStatusDto.getEntityStatus() + ".";
    }

    /**
     * Set new status for tariff.
     *
     * @param setNewStatusDto setNewStatusDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if tariff doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/tariff/setStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String setTariffStatus(@RequestBody @Valid SetNewStatusDto setNewStatusDto, BindingResult result)
            throws DatabaseException, ValidationException, LogicException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            String exceptionMessage = "You are not admin to do this, naughty hacker.";
            throw new ValidationException(exceptionMessage);
        }
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong contract id or status!";
            throw new ValidationException(exceptionMessage);
        }
        tariffService.setStatus(setNewStatusDto);
        return "Tariff is now " + setNewStatusDto.getEntityStatus() + ".";
    }

    /**
     * Modifies selected value of user.
     *
     * @param editMailDto editMailDto
     * @param result      validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/editUserMail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String editUserMail(@RequestBody @Valid EditMailDto editMailDto, BindingResult result) throws DatabaseException, ValidationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            String exceptionMessage = "You are not admin to do this.";
            throw new ValidationException(exceptionMessage);
        }
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong input!";
            throw new ValidationException(exceptionMessage);
        }
        userService.updateUserMail(editMailDto);
        return "Email modified successfully.";
    }

    /**
     * Modifies selected value of user.
     *
     * @param editPassportDto editPassportDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/editUserPassport", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String editUserPassport(@RequestBody @Valid EditPassportDto editPassportDto, BindingResult result) throws DatabaseException, ValidationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            String exceptionMessage = "You are not admin to do this.";
            throw new ValidationException(exceptionMessage);
        }
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong input!";
            throw new ValidationException(exceptionMessage);
        }
        userService.updateUserPassport(editPassportDto);
        return "Passport modified successfully.";
    }

    /**
     * Modifies selected value of user.
     *
     * @param editAddressDto editAddressDto
     * @param result         validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/editUserAddress", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String editUserAddress(@RequestBody @Valid EditAddressDto editAddressDto, BindingResult result) throws DatabaseException, ValidationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            String exceptionMessage = "You are not admin to do this.";
            throw new ValidationException(exceptionMessage);
        }
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong input!";
            throw new ValidationException(exceptionMessage);
        }
        userService.updateUserAddress(editAddressDto);
        return "Address modified successfully.";
    }
}
