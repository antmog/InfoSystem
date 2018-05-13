package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        findById(setNewStatusDto.getEntityId()).setStatus(setNewStatusDto.getEntityStatus());
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
        Set<TariffOption> contractAvailableOptions = contract.getTariff().getAvailableOptions();
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionList(editContractDto.getTariffOptionDtoList());
        Set<TariffOption> expectedOptionsList = Stream.of(contractActiveOptions, toBeAddedOptionsList).flatMap(Collection::stream).collect(Collectors.toSet());

        if (!contractAvailableOptions.containsAll(toBeAddedOptionsList)) {
            toBeAddedOptionsList.removeAll(contractAvailableOptions);
            StringBuilder sb = new StringBuilder("Current tariff doesn't allow these options:\n");
            for (TariffOption tariffOption : toBeAddedOptionsList) {
                sb.append(tariffOption.getName()).append("\n");
            }
            throw new LogicException(sb.toString());
        }

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
        contract.getActiveOptions().addAll(toBeAddedOptionsList);
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
     * @param editContractDto
     * @throws DatabaseException
     */
    @Override
    public void customerAddOptions(EditContractDto editContractDto) throws DatabaseException, LogicException {
        if (findById(editContractDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            addOptions(editContractDto);
        }
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
        Set<TariffOption> contractActiveOptions = contract.getActiveOptions();
        Set<TariffOption> toBeDeletedOptionsList = modelMapperWrapper.mapToTariffOptionList(editContractDto.getTariffOptionDtoList());

        Set<TariffOption> expectedOptionsList = new HashSet<>(contractActiveOptions);
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
        if (findById(editContractDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            delOptions(editContractDto);
        }
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
        if (contract.getStatus().equals(Status.ACTIVE)) {
            switchTariff(switchTariffDto);
        }
    }

    /**
     * Adds new contract with data from DTO.
     *
     * @param addContractDto data for new contract
     * @throws LogicException if number already exists
     */
    public void newContract(AddContractDto addContractDto) throws LogicException {
        if (doesPhoneNumberExist(addContractDto.getContractDto().getPhoneNumber())) {
            throw new LogicException("Contract with that phone number already exists.");
        }
        Contract contract = modelMapperWrapper.mapToContract(addContractDto);
        Set<TariffOption> contractAvailableOptions = contract.getTariff().getAvailableOptions();
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionList(addContractDto.getTariffOptionDtoList());


        if (!addContractDto.getTariffOptionDtoList().isEmpty()) {
            //todo refactor, common methods
            if (!contractAvailableOptions.containsAll(toBeAddedOptionsList)) {
                toBeAddedOptionsList.removeAll(contractAvailableOptions);
                StringBuilder sb = new StringBuilder("Current1 tariff doesn't allow these options:\n");
                for (TariffOption tariffOption : toBeAddedOptionsList) {
                    sb.append(tariffOption.getName()).append("\n");
                }
                throw new LogicException(sb.toString());
            }
            //todo


            contract.setActiveOptions(toBeAddedOptionsList);
        }

        contract.setPrice(contract.countPrice());
        // also add COST here later (cost of adding options)
        // i mean just take funds from user :D
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
