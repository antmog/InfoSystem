package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.service.*;
import com.infosystem.springmvc.validators.SearchUserByNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;


@RestController
public class AdminDataController extends ControllerTemplate {

    private final UserService userService;
    private final ContractService contractService;
    private final TariffOptionService tariffOptionService;
    private final TariffService tariffService;
    private final SearchUserByNumberValidator searchUserByNumberValidator;
    private final MessageSource messageSource;
    private final AdvProfileService advProfileService;

    @Autowired
    public AdminDataController(UserService userService, ContractService contractService,
                               TariffOptionService tariffOptionService, TariffService tariffService,
                               SearchUserByNumberValidator searchUserByNumberValidator,
                               MessageSource messageSource, AdvProfileService advProfileService) {
        this.userService = userService;
        this.contractService = contractService;
        this.tariffOptionService = tariffOptionService;
        this.tariffService = tariffService;
        this.searchUserByNumberValidator = searchUserByNumberValidator;
        this.messageSource = messageSource;
        this.advProfileService = advProfileService;
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
    public ResponseDto addContract(@Valid @RequestBody AddContractDto addContractDto, BindingResult result) throws LogicException, ValidationException, DatabaseException {
        if (result.hasErrors()) {
            throw new ValidationException("Enter correct phone number (length: 6-32) and chose tariff please.");
        }
        contractService.newContract(addContractDto);
        return new ResponseDto("Contract added successfully.");
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
    public ResponseDto saveTariff(@Valid @RequestBody AddTariffDto addTariffDto, BindingResult result) throws LogicException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Enter correct name and price for new tariff please (name length between 2 and 32, min price 1).");
        }
        tariffService.addTariff(addTariffDto);
        return new ResponseDto("Tariff added successfully.");
    }

    /**
     * Called while searching user by number (adminPanel and allUsers pages).
     *
     * @param phoneNumber phoneNumber
     * @return message
     * @throws LogicException      if no such number
     * @throws ValidationException if data in fields is not valid (phone number length(min = 6, max = 32))
     */
    @RequestMapping(value = "/adminPanel/user/searchUserByNumber/{phoneNumber}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SearchByNumberResponseDto searchUserByNumber(@PathVariable String phoneNumber) throws ValidationException, LogicException {
        if (!pathVariableIsANumber(phoneNumber)) {
            throw new ValidationException("Wrong path variable.");
        }
        SearchByNumberDto searchByNumberDto = new SearchByNumberDto(phoneNumber);
        Errors errors = new BeanPropertyBindingResult(searchByNumberDto, "searchByNumberDto");
        searchUserByNumberValidator.validate(searchByNumberDto, errors);
        if (errors.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            errors.getAllErrors().forEach(err -> sb.append(messageSource.getMessage(err, Locale.ENGLISH)).append(System.lineSeparator()));
            throw new ValidationException(sb.toString());
        }
        return new SearchByNumberResponseDto(userService.findByPhoneNumber(searchByNumberDto));
    }

    /**
     * Called on deleting user (user page).
     *
     * @param userId userId
     * @return message
     * @throws LogicException    if user still have contracts
     * @throws DatabaseException if user with @userId doesn't exist
     */
    @PostMapping(value = "/adminPanel/user/deleteUser/{userId}")
    public ResponseDto deleteUser(@PathVariable String userId) throws LogicException, DatabaseException, ValidationException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException("Wrong path variable.");
        }
        userService.deleteUserById(Integer.parseInt(userId));
        return new ResponseDto("User (id:" + userId + ") successfully deleted.");
    }

    /**
     * Called on deleting tariff (tariff page).
     *
     * @param tariffId tariffId
     * @return message
     * @throws LogicException    if tariff is still used
     * @throws DatabaseException if tariff with @tariffId doesn't exist
     */
    @PostMapping(value = "/adminPanel/tariff/deleteTariff/{tariffId}")
    public ResponseDto deleteTariff(@PathVariable String tariffId) throws DatabaseException, LogicException, ValidationException {
        if (!pathVariableIsANumber(tariffId)) {
            throw new ValidationException("Wrong path variable.");
        }
        tariffService.deleteTariffById(Integer.parseInt(tariffId));
        return new ResponseDto("Tariff (id:" + tariffId + ") successfully deleted.");

    }

    /**
     * Called on deleting contract (tariff page).
     *
     * @param contractId contractId
     * @return message
     * @throws DatabaseException if contract with @contractId doesn't exist
     */
    @PostMapping(value = "/adminPanel/contract/deleteContract/{contractId}")
    public ResponseDto deleteContract(@PathVariable String contractId) throws DatabaseException, ValidationException {
        if (!pathVariableIsANumber(contractId)) {
            throw new ValidationException("Wrong path variable.");
        }
        contractService.deleteContractById(Integer.parseInt(contractId));
        return new ResponseDto("Contract (id:" + contractId + ") successfully deleted.");
    }

    /**
     * Deleting option.
     *
     * @param optionId optionId
     * @return message
     * @throws DatabaseException if option with @optionId doesn't exist
     * @throws LogicException    if options is still used somewhere.
     */
    @PostMapping(value = "/adminPanel/option/deleteOption/{optionId}")
    public ResponseDto deleteOption(@PathVariable String optionId) throws DatabaseException, LogicException, ValidationException {
        if (!pathVariableIsANumber(optionId)) {
            throw new ValidationException("Wrong path variable.");
        }
        tariffOptionService.deleteTariffOptionById(Integer.parseInt(optionId));
        return new ResponseDto("Option (id:" + optionId + ") successfully deleted.");
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
    public ResponseDto tariffAddOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        tariffService.addOptions(editTariffDto);
        return new ResponseDto("Options added.");
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
    @PostMapping(value = "/adminPanel/tariff/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDto tariffDelOptions(@RequestBody @Valid EditTariffDto editTariffDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        tariffService.delOptions(editTariffDto);
        return new ResponseDto("Options deleted.");
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
    public ResponseDto contractAddOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        contractService.adminAddOptions(editContractDto);
        return new ResponseDto("Options added.");
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
    @PostMapping(value = "/adminPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDto contractDelOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        contractService.adminDelOptions(editContractDto);
        return new ResponseDto("Options deleted.");
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
    @PostMapping(value = "/adminPanel/contract/switchTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDto switchTariff(@RequestBody @Valid SwitchTariffDto switchTariffDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select tariff.");
        }
        contractService.adminSwitchTariff(switchTariffDto);
        return new ResponseDto("Switched to tariff (id:" + switchTariffDto.getTariffId() + ").");
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
    public ResponseDto optionAddOptions(@RequestBody @Valid TariffOptionRulesDto tariffOptionRulesDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to add.");
        }
        tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);
        return new ResponseDto("Options added.");
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
    public ResponseDto optionDelOptions(@RequestBody @Valid TariffOptionRulesDto tariffOptionRulesDto, BindingResult result) throws ValidationException, DatabaseException, LogicException {
        if (result.hasErrors()) {
            throw new ValidationException("Select options to delete.");
        }
        tariffOptionService.delRuleTariffOptions(tariffOptionRulesDto);
        return new ResponseDto("Options deleted.");
    }

    /**
     * Adding funds to user.
     *
     * @return message
     * @throws ValidationException if amount value is null
     * @throws DatabaseException   if user doesn't exist
     */
    @RequestMapping(value = "/adminPanel/addFunds", method = RequestMethod.POST)
    public ResponseDto addFunds(@RequestBody @Valid UserFundsDto userFundsDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Chose the amount of money you want to add (min - 10).");
        }
        userService.addFunds(userFundsDto);
        return new ResponseDto(userFundsDto.getAmount() + " funds added.");
    }

    /**
     * Getting current balance value.
     *
     * @return message
     * @throws ValidationException if amount value is null
     * @throws DatabaseException   if user doesn't exist
     */
    @RequestMapping(value = "/adminPanel/getBalance/{userId}", method = RequestMethod.GET)
    public String getBalance(@PathVariable String userId) throws DatabaseException, ValidationException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException("Wrong path variable.");
        }
        return userService.getBalance(Integer.parseInt(userId));
    }

    @RequestMapping(value = "/adminPanel/advProfiles/{advProfileId}", method = RequestMethod.GET)
    public AdvProfileDto getAdvProfile(@PathVariable String advProfileId) throws DatabaseException, ValidationException {
        if (!pathVariableIsANumber(advProfileId)) {
            throw new ValidationException("Wrong path variable.");
        }
        return advProfileService.getProfileById(Integer.parseInt(advProfileId));
    }

    @RequestMapping(value = "/adminPanel/advProfile/addTariff", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDto advProfileAddTariff(@RequestBody @Valid AdvProfileTariffDto advProfileTariffDto, BindingResult result)
            throws DatabaseException, ValidationException, LogicException {
        if(result.hasErrors()){
            throw new ValidationException("Incorrect input data.");
        }
        advProfileService.addTariffToProfile(advProfileTariffDto);
        return new ResponseDto("Tariff " + advProfileTariffDto.getTariffName() +
                " added to profile " + advProfileTariffDto.getAdvProfileId() + ".");
    }

    @RequestMapping(value = "/adminPanel/advProfile/editTariff", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDto advProfileEditTariff(@RequestBody @Valid AdvProfileTariffDto advProfileTariffDto, BindingResult result)
            throws DatabaseException, ValidationException, LogicException {
        if(result.hasErrors()){
            throw new ValidationException("Incorrect input data.");
        }
        advProfileService.advProfileEditTariff(advProfileTariffDto);
        return new ResponseDto("Tariff " + advProfileTariffDto.getTariffName() +
                " edited for " + advProfileTariffDto.getAdvProfileId() + ".");
    }

    @RequestMapping(value = "/adminPanel/advProfile/deleteTariff", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDto advProfileDeleteTariff(@RequestBody @Valid AdvProfileTariffDto advProfileTariffDto, BindingResult result)
            throws DatabaseException, ValidationException, LogicException {
        if(result.hasErrors()){
            throw new ValidationException("Incorrect input data.");
        }
        advProfileService.advProfileDeleteTariff(advProfileTariffDto);
        return new ResponseDto("Tariff " + advProfileTariffDto.getTariffName() +
                " deleted for " + advProfileTariffDto.getAdvProfileId() + ".");
    }


    @RequestMapping(value = "/adminPanel/advProfiles/activate/{advProfileId}", method = RequestMethod.POST)
    public ResponseDto advProfileActivate(@PathVariable String advProfileId)
            throws DatabaseException, ValidationException, LogicException {
        if (!pathVariableIsANumber(advProfileId)) {
            throw new ValidationException("Wrong path variable.");
        }
        advProfileService.activate(Integer.parseInt(advProfileId));
        return new ResponseDto("Profile " + advProfileId +" is activated.");
    }
}



