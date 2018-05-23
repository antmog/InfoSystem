package com.infosystem.springmvc.util;

import com.infosystem.springmvc.sessioncart.SessionCart;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OptionsRulesChecker {

    @Autowired
    ContractService contractService;

    private final CustomModelMapper modelMapperWrapper;
    private final SessionCart sessionCart;

    @Autowired
    public OptionsRulesChecker(CustomModelMapper modelMapperWrapper, SessionCart sessionCart) {
        this.modelMapperWrapper = modelMapperWrapper;
        this.sessionCart = sessionCart;
    }

    /**
     * Throws logic exception if conditions are not fulfilled
     * @param contractId contractId
     * @param toBeAddedOptions toBeAddedOptions
     * @throws DatabaseException no such contract
     * @throws LogicException if some options from toBeAddedOptionsList exclude options from currentOptions for this
     * contract(contractId) and options from cart for this contract(contractId); or options are not allowed for this
     * contracts tariff; or result list (toBeAddedOptions+current+cart) doesn't contain all related options for
     * toBeAddedOptions
     */
    public void checkAddToContractCustomer(Integer contractId, Set<TariffOption> toBeAddedOptions) throws DatabaseException, LogicException {
        Contract contract = contractService.findById(contractId);

        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();
        checkIfAllowedByTariff(toBeAddedOptions, contract.getTariff());
        checkAddExcludingCustomer(toBeAddedOptions, contractActiveOptions, contractId);
        checkAddRelatedCustomer(toBeAddedOptions, contractActiveOptions, contractId);
    }

    /**
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @param tariff tariff
     * @throws LogicException if not allowed by tariff
     */
    public void checkIfAllowedByTariff(Set<TariffOption> toBeAddedOptionsList, Tariff tariff) throws LogicException {
        Set<TariffOption> contractAvailableOptions = tariff.getAvailableOptions();

        if (!contractAvailableOptions.containsAll(toBeAddedOptionsList)) {
            toBeAddedOptionsList.removeAll(contractAvailableOptions);
            StringBuilder sb = new StringBuilder("Current tariff doesn't allow these options:\n");
            toBeAddedOptionsList.forEach(tariffOption -> sb.append(tariffOption.getName()).append("\n"));
            throw new LogicException(sb.toString());
        }
    }

    /**
     * Checking if there are no excluding options in: current contract options,
     * toBeAddedOptionsList and options in cart for this contractId
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @param currentOptions currentOptions
     * @param contractId contractId
     * @throws LogicException if some options from toBeAddedOptionsList exclude options from currentOptions and
     * options from cart for this contractId
     */
    private void checkAddExcludingCustomer(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions, Integer contractId) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList, contractId);
        checkAddExcluding(expectedOptionsList, toBeAddedOptionsList);
    }

    /**
     *  Checking if there are all related options in: current contract options,
     *  toBeAddedOptionsList and options in cart for options from toBeAddedOptionsList for this contractId
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @param currentOptions currentOptions
     * @param contractId contractId
     * @throws LogicException if there are no all related options for toBeAddedOptionsList in expected options list:
     * (toBeAddedOptionsList + currentOptions + options in cart)
     */
    private void checkAddRelatedCustomer(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions, Integer contractId) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList, contractId);
        checkAddRelated(expectedOptionsList, toBeAddedOptionsList);
    }

    /**
     * Generates list of expected options from currentOptions, toBeAddedOptionsList
     * and options from cart for this contractId
     * @param currentOptions currentOptions
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @param contractId contractId
     * @return list of expected options
     */
    private Set<TariffOption> expectedOptionsListAfterAdd(Set<TariffOption> currentOptions, Set<TariffOption> toBeAddedOptionsList, Integer contractId) {
        Set<TariffOption> cartItems = new HashSet<>();
        //&&!sessionCart.getOptions().get(contractId).isEmpty()
        if (sessionCart.getOptions().containsKey(contractId)) {
            cartItems = modelMapperWrapper.mapToSet(TariffOption.class, sessionCart.getOptions().get(contractId));
        }
        return Stream.of(cartItems, currentOptions, toBeAddedOptionsList).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    /**
     * Checking if there are options in expectedOptionsList which are excluded by options from toBeAddedOptionsList
     * @param expectedOptionsList expectedOptionsList
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @throws LogicException if some options from toBeAddedOptionsList exclude options from expectedOptionsList
     */
    private void checkAddExcluding(Set<TariffOption> expectedOptionsList, Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> optionExcludingOptions;
        for (TariffOption expectedActiveTariffOption : expectedOptionsList) {
            optionExcludingOptions = new HashSet<>(expectedActiveTariffOption.getExcludingTariffOptions());
            optionExcludingOptions.retainAll(toBeAddedOptionsList);
            if (!optionExcludingOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                optionExcludingOptions.forEach(tariffOption -> sb.append(expectedActiveTariffOption.getName())
                        .append(" excludes ").append(tariffOption.getName()).append(".\n"));
                throw new LogicException(sb.toString());
            }
        }
    }

    /**
     * Checking if expected list contains all related options for options from toBeAddedOptionsList
     * @param expectedOptionsList expectedOptionsList
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @throws LogicException if expectedOptionsList doesn't contain all options related for options from toBeAddedOptionsList
     */
    private void checkAddRelated(Set<TariffOption> expectedOptionsList, Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> optionRelatedOptions;
        for (TariffOption toBeAddedOption : toBeAddedOptionsList) {
            optionRelatedOptions = toBeAddedOption.getRelatedTariffOptions();
            if (!expectedOptionsList.containsAll(optionRelatedOptions)) {
                StringBuilder sb = new StringBuilder();
                optionRelatedOptions.forEach(tariffOption ->  sb.append(toBeAddedOption.getName()).append(" related with ")
                        .append(tariffOption.getName()).append(".\n"));
                throw new LogicException(sb.toString());
            }
        }
    }

    /**
     * Throws logic exception if conditions are not fulfilled
     * @param contract contract
     * @param toBeAddedOptions toBeAddedOptions
     * @throws LogicException if some options from toBeAddedOptionsList exclude options from currentOptions for this
     * contract(contractId) ; or options are not allowed for this contracts tariff; or result list
     * (toBeAddedOptions+current) doesn't contain all related options for toBeAddedOptions
     */
    public void checkAddToContract(Contract contract, Set<TariffOption> toBeAddedOptions) throws LogicException {
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();

        checkIfAllowedByTariff(toBeAddedOptions, contract.getTariff());
        checkAddExcludingAdmin(toBeAddedOptions, contractActiveOptions);
        checkAddRelatedAdmin(toBeAddedOptions, contractActiveOptions);
    }

    /**
     * Checking if there are no excluding options in: current contract options, toBeAddedOptionsList
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @param currentOptions currentOptions
     * @throws LogicException if some options from toBeAddedOptionsList exclude options from currentOptions
     */
    private void checkAddExcludingAdmin(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList);
        checkAddExcluding(expectedOptionsList, toBeAddedOptionsList);
    }

    /**
     * Generates expected option list from currentOptions and toBeAddedOptionsList
     * @param currentOptions currentOptions
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @return expectedOptionsListAfterAdd
     */
    private Set<TariffOption> expectedOptionsListAfterAdd(Set<TariffOption> currentOptions, Set<TariffOption> toBeAddedOptionsList) {
        return Stream.of(currentOptions, toBeAddedOptionsList).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    /**
     * Checking if expected list contains all related options for options from toBeAddedOptionsList
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @param currentOptions currentOptions
     * @throws LogicException if expectedOptionsList doesn't contain all options related for options from toBeAddedOptionsList
     */
    public void checkAddRelatedAdmin(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList);
        checkAddRelated(expectedOptionsList, toBeAddedOptionsList);
    }

    /**
     * Checking if there are no related options with current contract options in toBeDeletedList
     * @param contractId contractId
     * @param toBeDeletedList toBeDeletedList
     * @throws DatabaseException of contract doesn't exist
     * @throws LogicException if options from toBeDeletedList are related for options from contract(contractId) options
     */
    public void checkDelFromContract(Integer contractId, Set<TariffOption> toBeDeletedList) throws DatabaseException, LogicException {
        Contract contract = contractService.findById(contractId);
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();
        checkDelRalated(toBeDeletedList, contractActiveOptions);
    }

    /**
     * Checking if it is possible to delete toBeDeletedOptionsList from currentOptions (options in currentOptions
     * related with options from toBeDeletedOptionsList
     * @param toBeDeletedOptionsList toBeDeletedOptionsList
     * @param currentOptions currentOptions
     * @throws LogicException if some options in currentOptions are related with options from toBeDeletedOptionsList
     */
    public void checkDelRalated(Set<TariffOption> toBeDeletedOptionsList, Set<TariffOption> currentOptions) throws LogicException {
        Set<TariffOption> expectedOptionsList = new HashSet<>(currentOptions);
        expectedOptionsList.removeAll(toBeDeletedOptionsList);

        Set<TariffOption> optionRelatedOptions;
        for (TariffOption expectedActiveTariffOption : expectedOptionsList) {
            optionRelatedOptions = new HashSet<>(expectedActiveTariffOption.getRelatedTariffOptions());
            optionRelatedOptions.retainAll(toBeDeletedOptionsList);
            if (!optionRelatedOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                optionRelatedOptions.forEach(tariffOption -> sb.append(expectedActiveTariffOption.getName())
                        .append(" related with ").append(tariffOption.getName()).append(".\n"));
                throw new LogicException(sb.toString());
            }
        }
    }

    /**
     * Checking if contract already have options from toBeAddedOptionsList
     * @param contract contract
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @throws LogicException if contract already have options from toBeAddedOptionsList
     */
    public void checkIfContractAlreadyHave(Contract contract, Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> currentOptions = new HashSet<>(contract.getActiveOptions());
        currentOptions.retainAll(toBeAddedOptionsList);
        if (!currentOptions.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            currentOptions.forEach(tariffOption -> sb.append("Contract ").append(contract.getId()).append(" already has ")
                    .append(tariffOption.getName()).append(" option.\n"));
            throw new LogicException(sb.toString());
        }
    }

    /**
     * Checking if tariff already have options from toBeAddedOptionsList
     * @param tariff tariff
     * @param toBeAddedOptionsList toBeAddedOptionsList
     * @throws LogicException if contract already have options from toBeAddedOptionsList
     */
    public void checkIfTariffAlreadyHave(Tariff tariff, Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> currentOptions = new HashSet<>(tariff.getAvailableOptions());
        currentOptions.retainAll(toBeAddedOptionsList);
        if (!currentOptions.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            currentOptions.forEach(tariffOption -> sb.append("Tariff ").append(tariff.getId()).append(" already has ")
                    .append(tariffOption.getName()).append(" option.\n"));
            throw new LogicException(sb.toString());
        }
    }
}
