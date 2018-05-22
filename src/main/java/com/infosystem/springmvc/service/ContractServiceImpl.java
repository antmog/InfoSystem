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
import com.infosystem.springmvc.sessioncart.SessionCart;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    UserService userService;

    @Autowired
    TariffService tariffService;

    @Autowired
    OptionsRulesChecker optionsRulesChecker;

    @Autowired
    SessionCart sessionCart;

    @Autowired
    CustomModelMapper modelMapperWrapper;

    private final ContractDao dao;

    @Autowired
    public ContractServiceImpl(ContractDao dao) {
        this.dao = dao;
    }

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

    public List<Contract> findAllContracts() {
        return dao.findAllContracts();
    }

    /**
     * Deletes contract.
     *
     * @param id id
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
     * @param setNewStatusDto setNewStatusDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException {
        findById(setNewStatusDto.getEntityId()).setStatus(modelMapperWrapper.mapToStatus(setNewStatusDto.getEntityStatus()));
    }

    /**
     * @param phoneNumber phoneNumber
     * @return contract
     */
    @Override
    public Contract findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber);
    }

    /**
     * Add selected options to selected contract.
     *
     * @param editContractDto editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    public void addOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        Contract contract = findById(editContractDto.getContractId());
        User user = contract.getUser();
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionSet(editContractDto.getTariffOptionDtoList());

        optionsRulesChecker.checkIfContractAlreadyHave(contract, toBeAddedOptionsList);
        optionsRulesChecker.checkAddToContract(contract, toBeAddedOptionsList);
        Amount amount = new Amount();
        toBeAddedOptionsList.forEach(option -> amount.add(option.getCostOfAdd()));
        if (user.getBalance() < amount.getAmount()) {
            throw new LogicException("Not enough funds.");
        }
        contract.getActiveOptions().addAll(toBeAddedOptionsList);
        user.spendFunds(amount.getAmount());
        contract.setPrice(contract.countPrice());
    }

    /**
     * Add options to any contract.
     *
     * @param editContractDto editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void adminAddOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        addOptions(editContractDto);
    }

    /**
     * Add options only to active contracts.
     *
     * @throws DatabaseException if no such user
     */
    @Override
    public void customerAddOptions(AddOptionsDto addOptionsDto) throws DatabaseException, LogicException {
        Contract contract;
        Set<TariffOption> toBeAddedOptionsList;
        User user = userService.findById(addOptionsDto.getUserId());
        Double amount = 0.0;
        if (sessionCart.getOptions().isEmpty()) {
            throw new LogicException("Cart is empty.");
        }
        for (Map.Entry<Integer, Set<TariffOptionDto>> entry : sessionCart.getOptions().entrySet()) {
            contract = findById(entry.getKey());
            if (!contract.getStatus().equals(Status.ACTIVE)) {
                throw new LogicException("Contract " + contract.getId() + " is NOT active, sorry.");
            }
            toBeAddedOptionsList = modelMapperWrapper.mapToSet(TariffOption.class,entry.getValue());
            optionsRulesChecker.checkIfAllowedByTariff(toBeAddedOptionsList, contract.getTariff());
            optionsRulesChecker.checkAddToContract(contract, toBeAddedOptionsList);
            optionsRulesChecker.checkIfContractAlreadyHave(contract, toBeAddedOptionsList);
            for (TariffOption tariffOption : toBeAddedOptionsList) {
                amount += tariffOption.getCostOfAdd();
            }
            if (user.getBalance() < amount) {
                throw new LogicException("Not enough funds.");
            }
            contract.getActiveOptions().addAll(toBeAddedOptionsList);
        }
        sessionCart.getOptions().clear();
        user.spendFunds(amount);
        sessionCart.countItems();
    }

    /**
     * Delete selected options from selected contract.
     *
     * @param editContractDto editContractDto
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
     * @param editContractDto editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void adminDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        delOptions(editContractDto);
    }

    /**
     * Delete options from active contract.
     *
     * @param editContractDto editContractDto
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
     * @param switchTariffDto switchTariffDto
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
     * @param switchTariffDto switchTariffDto
     * @throws DatabaseException
     */
    @Override
    public void adminSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException {
        switchTariff(switchTariffDto);
    }

    /**
     * Set active contract's tariff to selected.
     *
     * @param switchTariffDto switchTariffDto
     * @throws DatabaseException if no such contract
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
        if (doesPhoneNumberExist(addContractDto.getPhoneNumber())) {
            throw new LogicException("Contract with that phone number already exists.");
        }
        User user = userService.findById(addContractDto.getUserId());
        Tariff tariff = tariffService.findById(addContractDto.getTariffId());
        Contract contract = new Contract();
        contract.setUser(user);
        contract.setTariff(tariff);
        contract.setPhoneNumber(addContractDto.getPhoneNumber());

        Set<TariffOption> toBeAddedOptionsList;
        if (!addContractDto.getTariffOptionDtoList().isEmpty()) {
            toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionSet(addContractDto.getTariffOptionDtoList());
            optionsRulesChecker.checkAddToContract(contract, toBeAddedOptionsList);
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

    @Override
    public List<Contract> findListOfContracts(int startIndex, int count) {
        return dao.findListOfContracts(startIndex, count);
    }

    @Override
    public int getPagesCount(int itemsPerPage) {
        return (dao.contractCount()-1)/itemsPerPage + 1;
    }


    /**
     * @param phoneNumber phoneNumber
     * @return true is exist
     */
    private boolean doesPhoneNumberExist(String phoneNumber) {
        Contract contract = findByPhoneNumber(phoneNumber);
        return (contract != null);
    }
}
