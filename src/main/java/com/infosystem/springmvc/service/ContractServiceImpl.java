package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Status;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
     * @param editContractDto
     * @return
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void addOptions(EditContractDto editContractDto) throws DatabaseException {
        Contract contract = findById(editContractDto.getContractId());
        Set<TariffOption> contractOptionList = modelMapperWrapper.mapToTariffOptionList(editContractDto.getTariffOptionDtoList());
        contractOptionList.addAll(contract.getActiveOptions());
        contract.setActiveOptions(contractOptionList);

        Double price = contract.getTariff().getPrice();
        for (TariffOption tariffOption : contractOptionList) {
            price += tariffOption.getPrice();
        }
        contract.setPrice(price);
        // LOGIC RULES ETC
    }

    /**
     * Add options to any contract.
     * @param editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void adminAddOptions(EditContractDto editContractDto) throws DatabaseException {
        addOptions(editContractDto);
    }

    /**
     * Add options only to active contracts.
     * @param editContractDto
     * @throws DatabaseException
     */
    @Override
    public void customerAddOptions(EditContractDto editContractDto) throws DatabaseException {
        if (findById(editContractDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            addOptions(editContractDto);
        }
    }

    /**
     * Delete selected options from selected contract.
     * @param editContractDto
     * @return
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void delOptions(EditContractDto editContractDto) throws DatabaseException {
        Contract contract = findById(editContractDto.getContractId());
        Set<TariffOption> contractOptionList = modelMapperWrapper.mapToTariffOptionList(editContractDto.getTariffOptionDtoList());
        Set<TariffOption> newTariffOptionList = contract.getActiveOptions();
        newTariffOptionList.removeAll(contractOptionList);
        contract.setActiveOptions(newTariffOptionList);

        Double price = contract.getTariff().getPrice();
        for (TariffOption tariffOption : newTariffOptionList) {
            price += tariffOption.getPrice();
        }
        contract.setPrice(price);
        // LOGIC RULES ETC
    }

    /**
     * Delete options from any contract.
     * @param editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void adminDelOptions(EditContractDto editContractDto) throws DatabaseException {
        delOptions(editContractDto);
    }

    /**
     * Delete options from active contract.
     * @param editContractDto
     * @throws DatabaseException if contract doesn't exist
     */
    @Override
    public void customerDelOptions(EditContractDto editContractDto) throws DatabaseException {
        if (findById(editContractDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            delOptions(editContractDto);
        }
    }

    /**
     * Set contract's tariff to selected.
     * @param switchTariffDto
     * @throws DatabaseException if tariff/contract doesn't exist
     */
    @Override
    public void switchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException {
        Contract contract = findById(switchTariffDto.getContractId());
        Tariff newTariff = tariffService.findById(switchTariffDto.getTariffId());
        Double newPrice = contract.getPrice() - contract.getTariff().getPrice() + newTariff.getPrice();
        contract.setTariff(newTariff);
        contract.setPrice(newPrice);
    }

    /**
     * Set any contract's tariff to selected.
     * @param switchTariffDto
     * @throws DatabaseException
     */
    @Override
    public void adminSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException {
        switchTariff(switchTariffDto);
    }

    /**
     * Set active contract's tariff to selected.
     * @param switchTariffDto
     * @throws DatabaseException
     */
    @Override
    public void customerSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException {
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

        Double price = contract.getTariff().getPrice();
        if (!addContractDto.getTariffOptionDtoList().isEmpty()) {
            Set<TariffOption> tariffOptionList = modelMapperWrapper.mapToTariffOptionList(addContractDto.getTariffOptionDtoList());
            for (TariffOption tariffOption : tariffOptionList) {
                price += tariffOption.getPrice();
            }
            contract.setActiveOptions(tariffOptionList);
        }
        contract.setPrice(price);

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
