package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Amount;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.xml.crypto.Data;
import java.util.*;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractDao dao;
    @Autowired
    TariffOptionService tariffOptionService;
    @Autowired
    TariffService tariffService;
    @Autowired
    UserService userService;
    @Autowired
    CustomModelMapper modelMapperWrapper;
    @Autowired
    OptionsRulesChecker optionsRulesChecker;
    @Autowired
    SessionCart sessionCart;


//    @Autowired
//    public ContractServiceImpl(ContractDao dao, TariffOptionService tariffOptionService, TariffService tariffService,
//                               UserService userService, CustomModelMapper modelMapperWrapper) {
//        this.dao = dao;
//        this.tariffOptionService = tariffOptionService;
//        this.tariffService = tariffService;
//        this.userService = userService;
//        this.modelMapperWrapper = modelMapperWrapper;
//    }

    public Contract findById(int id) throws DatabaseException {
        Contract contract = dao.findById(id);
        if (contract == null) {
            throw new DatabaseException("Contract doesn't exist.");
        }
        return contract;
    }

    public void saveContract(Contract contract) {
        dao.save(contract);
    }

    public void updateContract(Contract contract) throws DatabaseException {
        Contract entity = findById(contract.getId());
        if (entity != null) {
            entity.setActiveOptions(contract.getActiveOptions());
            entity.setUser(contract.getUser());
            entity.setTariff(contract.getTariff());
            entity.setPhoneNumber(contract.getPhoneNumber());
            entity.setPrice(contract.getPrice());
            entity.setStatus(contract.getStatus());
        }
    }

    public List<Contract> findAllContracts() {
        return dao.findAllContracts();
    }

    /**
     * Deletes contract.
     *
     * @param id
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void deleteContractById(int id) throws DatabaseException {
        findById(id);
        dao.deleteById(id);
    }

    /**
     * Set selected status to contract with selected id.
     *
     * @param setNewStatusDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException {
        findById(setNewStatusDto.getEntityId()).setStatus(modelMapperWrapper.mapToStatus(setNewStatusDto.getEntityStatus()));
    }

    /**
     * @param phoneNumber
     * @return contract
     */
    @Override
    public Contract findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber);
    }

    /**
     * Add selected options to selected contract.
     *
     * @param editContractDto
     * @return
     * @throws DatabaseException if contract doesn't exist
     */
    public void addOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        Contract contract = findById(editContractDto.getContractId());
        User user = contract.getUser();
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionSet(editContractDto.getTariffOptionDtoList());

        optionsRulesChecker.checkAddToContract(editContractDto.getContractId(), toBeAddedOptionsList);
        Amount amount = new Amount();
        contract.getActiveOptions().addAll(toBeAddedOptionsList);
        toBeAddedOptionsList.forEach(option->amount.add(option.getCostOfAdd()));
        if (user.getBalance() < amount.getAmount()) {
            throw new LogicException("Not enough funds.");
        }
        user.spendFunds(amount.getAmount());
        contract.setPrice(contract.countPrice());
    }

    /**
     * Add options to any contract.
     *
     * @param editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void adminAddOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        addOptions(editContractDto);
    }

    /**
     * Add options only to active contracts.
     *
     * @throws DatabaseException
     */
    @Override
    public void customerAddOptions(AddOptionsDto addOptionsDto) throws DatabaseException, LogicException {
        Contract contract;
        Set<TariffOption> toBeAddedOptionsList;
        Double amount = 0.0;
        if (sessionCart.getOptions().isEmpty()) {
            throw new LogicException("Cart is empty.");
        }
        for (Map.Entry<Integer, Set<TariffOptionDto>> entry : sessionCart.getOptions().entrySet()) {
            contract = findById(entry.getKey());
            if (!contract.getStatus().equals(Status.ACTIVE)) {
                throw new LogicException("Contract " + contract.getId() + " is NOT active, sorry.");
            }
            toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionSet(entry.getValue());
            optionsRulesChecker.checkIfAllowedByTariff(toBeAddedOptionsList, contract.getTariff());
            optionsRulesChecker.checkAddToContract(contract.getId(), toBeAddedOptionsList);
            optionsRulesChecker.checkIfContractAlreadyHave(contract, toBeAddedOptionsList);
            for (TariffOption tariffOption : toBeAddedOptionsList) {
                amount += tariffOption.getCostOfAdd();
            }
            contract.getActiveOptions().addAll(toBeAddedOptionsList);
        }
        sessionCart.getOptions().clear();
        User user = userService.findById(addOptionsDto.getUserId());
        if (user.getBalance() < amount) {
            throw new LogicException("Not enough funds.");
        }
        user.spendFunds(amount);
        sessionCart.countItems();
    }

    /**
     * Delete selected options from selected contract.
     *
     * @param editContractDto
     * @return
     * @throws DatabaseException if contract doesn't exist
     */
    public void delOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        Contract contract = findById(editContractDto.getContractId());
        Set<TariffOption> toBeDeletedOptionsList = modelMapperWrapper.mapToTariffOptionSet(editContractDto.getTariffOptionDtoList());

        optionsRulesChecker.checkDelFromContract(editContractDto.getContractId(), toBeDeletedOptionsList);

        contract.getActiveOptions().removeAll(toBeDeletedOptionsList);
        contract.setPrice(contract.countPrice());
    }

    /**
     * Delete options from any contract.
     *
     * @param editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void adminDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        delOptions(editContractDto);
    }

    /**
     * Delete options from active contract.
     *
     * @param editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void customerDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        if (!findById(editContractDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            throw new LogicException("Sorry, contract is not active, refresh page.");
        }
        delOptions(editContractDto);
    }

    /**
     * Set contract's tariff to selected.
     *
     * @param switchTariffDto
     * @throws DatabaseException if tariff/contract doesn't exist
     */
    public void switchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException {
        Contract contract = findById(switchTariffDto.getContractId());
        Tariff newTariff = tariffService.findById(switchTariffDto.getTariffId());
        if (!newTariff.getAvailableOptions().containsAll(contract.getActiveOptions())) {
            List<TariffOption> contractOptions = new ArrayList<>(contract.getActiveOptions());
            contractOptions.removeAll(newTariff.getAvailableOptions());
            StringBuilder sb = new StringBuilder("New tariff doesn't include all current options. To switch remove options:\n");
            for (TariffOption tariffOption : contractOptions) {
                sb.append(tariffOption.getName()).append("\n");
            }
            throw new LogicException(sb.toString());
        }
        contract.setTariff(newTariff);
        contract.setPrice(contract.countPrice());
    }

    /**
     * Set any contract's tariff to selected.
     *
     * @param switchTariffDto
     * @throws DatabaseException
     */
    @Override
    public void adminSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException {
        switchTariff(switchTariffDto);
    }

    /**
     * Set active contract's tariff to selected.
     *
     * @param switchTariffDto
     * @throws DatabaseException
     */
    @Override
    public void customerSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException {
        Contract contract = findById(switchTariffDto.getContractId());
        if (!contract.getStatus().equals(Status.ACTIVE)) {
            throw new LogicException("Sorry, contract is not active, refresh page.");
        }
        switchTariff(switchTariffDto);
    }

    /**
     * Adds new contract with data from DTO.
     *
     * @param addContractDto data for new contract
     * @throws LogicException if number already exists
     */
    public void newContract(AddContractDto addContractDto) throws LogicException, DatabaseException {
        Amount amount = new Amount();
        if (doesPhoneNumberExist(addContractDto.getContractDto().getPhoneNumber())) {
            throw new LogicException("Contract with that phone number already exists.");
        }
        Contract contract = modelMapperWrapper.mapToContract(addContractDto);
        User user = contract.getUser();
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionSet(addContractDto.getTariffOptionDtoList());
        contract.setActiveOptions(new HashSet<>());
        if (!addContractDto.getTariffOptionDtoList().isEmpty()) {
            optionsRulesChecker.checkAddToContract(contract.getId(), toBeAddedOptionsList);
            contract.setActiveOptions(toBeAddedOptionsList);
        }
        contract.getActiveOptions().forEach(option -> amount.add(option.getCostOfAdd()));

        if (user.getBalance() < amount.getAmount()) {
            throw new LogicException("Not enough funds.");
        }
        user.spendFunds(amount.getAmount());
        contract.setPrice(contract.countPrice());
        dao.save(contract);
    }

    /**
     * @param phoneNumber
     * @return true is exist
     */
    private boolean doesPhoneNumberExist(String phoneNumber) {
        Contract contract = findByPhoneNumber(phoneNumber);
        return (contract != null);
    }
}
