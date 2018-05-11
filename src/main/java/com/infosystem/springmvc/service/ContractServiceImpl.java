package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Status;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public Contract findById(int id) {
        return dao.findById(id);
    }

    public void saveContract(Contract contract) {
        dao.save(contract);
    }

    public void updateContract(Contract contract) {
        Contract entity = dao.findById(contract.getId());
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

    @Override
    public void deleteContractById(int id) {
        dao.deleteById(id);
    }

    @Override
    public void setStatus(SetNewStatusDto setNewStatusDto) {
        dao.findById(setNewStatusDto.getEntityId()).setStatus(setNewStatusDto.getEntityStatus());
    }

    @Override
    public Contract findByPhoneNumber(String phoneNumber) {
        Contract contract = dao.findByPhoneNumber(phoneNumber);
        System.out.println(contract);
        return contract;
    }

    @Override
    public boolean addOptions(ContractOptionsDto contractOptionsDto) {
        List<Integer> optionIdList = new ArrayList<>();
        for (TariffOptionDto tariffOptionDto : contractOptionsDto.getTariffOptionDtoList()) {
            optionIdList.add(tariffOptionDto.getId());
        }
        Set<TariffOption> contractOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Contract contract = dao.findById(contractOptionsDto.getContractId());
        contractOptionList.addAll(contract.getActiveOptions());
        contract.setActiveOptions(contractOptionList);

        Double price = contract.getTariff().getPrice();
        for (TariffOption tariffOption : contractOptionList) {
            price += tariffOption.getPrice();
        }
        contract.setPrice(price);
        // LOGIC RULES ETC
        return false;
    }

    @Override
    public boolean adminAddOptions(ContractOptionsDto contractOptionsDto) {
        addOptions(contractOptionsDto);
        return false;
    }

    @Override
    public boolean customerAddOptions(ContractOptionsDto contractOptionsDto) {
        if (dao.findById(contractOptionsDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            addOptions(contractOptionsDto);
        }
        return false;
    }

    @Override
    public boolean delOptions(ContractOptionsDto contractOptionsDto) {
        List<Integer> optionIdList = new ArrayList<>();
        for (TariffOptionDto tariffOptionDto : contractOptionsDto.getTariffOptionDtoList()) {
            optionIdList.add(tariffOptionDto.getId());
        }
        Set<TariffOption> contractOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Contract contract = dao.findById(contractOptionsDto.getContractId());

        Set<TariffOption> newTariffOptionList = contract.getActiveOptions();
        if (newTariffOptionList.removeAll(contractOptionList)) {
            System.out.println("YES");
        }
        contract.setActiveOptions(newTariffOptionList);
        Double price = contract.getTariff().getPrice();
        for (TariffOption tariffOption : newTariffOptionList) {
            price += tariffOption.getPrice();
        }
        contract.setPrice(price);
        // LOGIC RULES ETC
        return false;
    }

    @Override
    public boolean adminDelOptions(ContractOptionsDto contractOptionsDto) {
        delOptions(contractOptionsDto);
        return false;
    }

    @Override
    public boolean customerDelOptions(ContractOptionsDto contractOptionsDto) {
        if (dao.findById(contractOptionsDto.getContractId()).getStatus().equals(Status.ACTIVE)) {
            delOptions(contractOptionsDto);
        }
        return false;
    }

    @Override
    public void switchTariff(SwitchTariffDto switchTariffDto) {
        Contract contract = dao.findById(switchTariffDto.getContractId());
        Tariff newTariff = tariffService.findById(switchTariffDto.getTariffId());
        Double newPrice = contract.getPrice() - contract.getTariff().getPrice() + newTariff.getPrice();
        contract.setTariff(newTariff);
        contract.setPrice(newPrice);
    }

    @Override
    public void adminSwitchTariff(SwitchTariffDto switchTariffDto) {
        switchTariff(switchTariffDto);
    }

    @Override
    public void customerSwitchTariff(SwitchTariffDto switchTariffDto) {
        Contract contract = dao.findById(switchTariffDto.getContractId());
        if (contract.getStatus().equals(Status.ACTIVE)) {
            switchTariff(switchTariffDto);
        }
    }


    /**
     * Adds new contract with data from DTO.
     * @param addContractDto data for new contract
     * @throws LogicException if number already exists
     */
    public void newContract(AddContractDto addContractDto) throws LogicException {
        if(doesPhoneNumberExist(addContractDto.getContractDto().getPhoneNumber())){
            throw new LogicException("Contract with that phone number already exists.");
        }
        Contract contract = modelMapperWrapper.mapToContract(addContractDto);

        Double price = contract.getTariff().getPrice();
        if(!addContractDto.getTariffOptionDtoList().isEmpty()){
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

    private boolean doesPhoneNumberExist(String phoneNumber){
        Contract contract = findByPhoneNumber(phoneNumber);
        return (contract!=null);
    }

}
