package com.infosystem.springmvc.util;

import com.infosystem.springmvc.dto.SessionCart;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OptionsRulesChecker {

    @Autowired
    CustomModelMapper modelMapperWrapper;
    @Autowired
    ContractService contractService;
    @Autowired
    SessionCart sessionCart;

    private Set<TariffOption> expectedOptionsListAfterAdd(Set<TariffOption> currentOptions, Set<TariffOption> toBeAddedOptionsList) {
        return Stream.of(currentOptions, toBeAddedOptionsList).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private Set<TariffOption> expectedOptionsListAfterAdd(Set<TariffOption> currentOptions, Set<TariffOption> toBeAddedOptionsList, Integer contractId) {
        Set<TariffOption> cartItems = new HashSet<>();
        if(sessionCart.getOptions().containsKey(contractId)&&!sessionCart.getOptions().get(contractId).isEmpty()){
            cartItems = modelMapperWrapper.mapToTariffOptionSet(sessionCart.getOptions().get(contractId));
        }
        return Stream.of(cartItems, currentOptions, toBeAddedOptionsList).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void checkAddExcludingAdmin(Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> emptyList = new HashSet<>();
        checkAddExcludingAdmin(toBeAddedOptionsList, emptyList);
    }

    public void checkAddRelatedAdmin(Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> emptyList = new HashSet<>();
        checkAddRelated(toBeAddedOptionsList, emptyList);
    }

    public void checkAddExcludingCustomer(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions, Integer contractId) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList, contractId);
        checkAddExcluding(expectedOptionsList, toBeAddedOptionsList);
    }


    public void checkAddExcludingAdmin(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList);
        checkAddExcluding(expectedOptionsList, toBeAddedOptionsList);
    }

    private void checkAddExcluding(Set<TariffOption> expectedOptionsList, Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> optionExcludingOptions;
        for (TariffOption expectedActiveTariffOption : expectedOptionsList) {
            optionExcludingOptions = new HashSet<>(expectedActiveTariffOption.getExcludingTariffOptions());
            optionExcludingOptions.retainAll(toBeAddedOptionsList);
            if (!optionExcludingOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : optionExcludingOptions) {
                    sb.append(expectedActiveTariffOption.getName()).append(" excludes ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }
    }

    private void checkAddRelated(Set<TariffOption> expectedOptionsList, Set<TariffOption> toBeAddedOptionsList) throws LogicException {
        Set<TariffOption> optionRelatedOptions;
        for (TariffOption toBeAddedOption : toBeAddedOptionsList) {
            optionRelatedOptions = toBeAddedOption.getRelatedTariffOptions();
            if (!expectedOptionsList.containsAll(optionRelatedOptions)) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : optionRelatedOptions) {
                    sb.append(toBeAddedOption.getName()).append(" related with ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }
    }

    public void checkAddRelatedAdmin(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList);
        checkAddRelated(expectedOptionsList,toBeAddedOptionsList);
    }

    public void checkAddRelatedCustomer(Set<TariffOption> toBeAddedOptionsList, Set<TariffOption> currentOptions, Integer contractId) throws LogicException {
        Set<TariffOption> expectedOptionsList = expectedOptionsListAfterAdd(currentOptions, toBeAddedOptionsList,contractId);
        checkAddRelated(expectedOptionsList,toBeAddedOptionsList);

    }

    public void checkDelRalated(Set<TariffOption> toBeDeletedOptionsList, Set<TariffOption> currentOptions) throws LogicException {
        Set<TariffOption> expectedOptionsList = new HashSet<>(currentOptions);

        expectedOptionsList.removeAll(toBeDeletedOptionsList);
        Set<TariffOption> optionRelatedOptions;
        for (TariffOption expectedActiveTariffOption : expectedOptionsList) {
            optionRelatedOptions = new HashSet<>(expectedActiveTariffOption.getRelatedTariffOptions());
            optionRelatedOptions.retainAll(toBeDeletedOptionsList);
            if (!optionRelatedOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : expectedActiveTariffOption.getRelatedTariffOptions()) {
                    sb.append(expectedActiveTariffOption.getName()).append(" related with ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }
    }

    public void checkIfAllowedByTariff(Set<TariffOption> toBeAddedOptionsList, Tariff tariff) throws LogicException {
        Set<TariffOption> contractAvailableOptions = tariff.getAvailableOptions();

        if (!contractAvailableOptions.containsAll(toBeAddedOptionsList)) {
            toBeAddedOptionsList.removeAll(contractAvailableOptions);
            StringBuilder sb = new StringBuilder("Current tariff doesn't allow these options:\n");
            for (TariffOption tariffOption : toBeAddedOptionsList) {
                sb.append(tariffOption.getName()).append("\n");
            }
            throw new LogicException(sb.toString());
        }
    }

    public void checkAddToContract(Integer contractId, Set<TariffOption> toBeAddedOptions) throws DatabaseException, LogicException {
        Contract contract = contractService.findById(contractId);
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();

        checkIfAllowedByTariff(toBeAddedOptions, contract.getTariff());
        checkAddExcludingAdmin(toBeAddedOptions, contractActiveOptions);
        checkAddRelatedAdmin(toBeAddedOptions, contractActiveOptions);
    }

    public void checkAddToContractCustomer(Integer contractId, Set<TariffOption> toBeAddedOptions) throws DatabaseException, LogicException {
        Contract contract = contractService.findById(contractId);
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();

        checkIfAllowedByTariff(toBeAddedOptions, contract.getTariff());
        checkAddExcludingCustomer(toBeAddedOptions, contractActiveOptions,contractId);
        checkAddRelatedCustomer(toBeAddedOptions, contractActiveOptions,contractId);
    }

    public void checkDelFromContract(Integer contractId, Set<TariffOption> tariffOptions) throws DatabaseException, LogicException {
        Contract contract = contractService.findById(contractId);
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();

        checkDelRalated(tariffOptions, contractActiveOptions);
    }
}
