package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Status;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
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
    private ContractDao dao;

    @Autowired
    private TariffOptionService tariffOptionService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private UserService userService;

    public Contract findById(int id) {
        return dao.findById(id);
    }

    public void saveContract(Contract contract) {
        dao.save(contract);
    }



    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateContract(Contract contract) {
        Contract entity = dao.findById(contract.getId());
        if(entity!=null){

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
        return dao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean addOptions(ContractOptionsDto contractOptionsDto) {
        List<Integer> optionIdList = new ArrayList<>();
        for( TariffOptionDto tariffOptionDto : contractOptionsDto.getTariffOptionDtoList()){
            optionIdList.add(tariffOptionDto.getId());
        }
        Set<TariffOption> contractOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Contract contract = dao.findById(contractOptionsDto.getContractId());
        contractOptionList.addAll(contract.getActiveOptions());
        contract.setActiveOptions(contractOptionList);

        Double price = contract.getTariff().getPrice();
        for(TariffOption tariffOption : contractOptionList){
            price+=tariffOption.getPrice();
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
        if(dao.findById(contractOptionsDto.getContractId()).getStatus().equals(Status.ACTIVE)){
            addOptions(contractOptionsDto);
        }
        return false;
    }

    @Override
    public boolean delOptions(ContractOptionsDto contractOptionsDto) {
        List<Integer> optionIdList = new ArrayList<>();
        for( TariffOptionDto tariffOptionDto : contractOptionsDto.getTariffOptionDtoList()){
            optionIdList.add(tariffOptionDto.getId());
        }
        Set<TariffOption> contractOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Contract contract = dao.findById(contractOptionsDto.getContractId());

        Set<TariffOption> newTariffOptionList = contract.getActiveOptions();
        if(newTariffOptionList.removeAll(contractOptionList)){
            System.out.println("YES");
        }
        contract.setActiveOptions(newTariffOptionList);
        Double price = contract.getTariff().getPrice();
        for(TariffOption tariffOption : newTariffOptionList){
            price+=tariffOption.getPrice();
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
        if(dao.findById(contractOptionsDto.getContractId()).getStatus().equals(Status.ACTIVE)){
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
        if(contract.getStatus().equals(Status.ACTIVE)){
            switchTariff(switchTariffDto);
        }
    }

    @Override
    public void newContract(AddContractDto addContractDto) {
        Tariff tariff = tariffService.findById(addContractDto.getContractDto().getTariffId());
        Double price = tariff.getPrice();
        Contract contract = new Contract();
        contract.setPhoneNumber(addContractDto.getContractDto().getPhoneNumber());
        contract.setUser(userService.findById(addContractDto.getContractDto().getUserId()));
        contract.setTariff(tariff);
        List<Integer> optionIdList = new ArrayList<>();
        for( TariffOptionDto tariffOptionDto : addContractDto.getTariffOptionDtoList()){
            optionIdList.add(tariffOptionDto.getId());
        }
        Set<TariffOption> tariffOptionList = tariffOptionService.selectListByIdList(optionIdList);
        for(TariffOption tariffOption : tariffOptionList){
            price+=tariffOption.getPrice();
        }
        // also add COST here later (cost of adding options)
        // i mean just take funds from user :D
        contract.setPrice(price);
        contract.setActiveOptions(tariffOptionList);
        dao.save(contract);
    }

}
