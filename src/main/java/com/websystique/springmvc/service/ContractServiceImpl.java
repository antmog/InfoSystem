package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.ContractDao;
import com.websystique.springmvc.dto.*;
import com.websystique.springmvc.model.Contract;
import com.websystique.springmvc.model.Status;
import com.websystique.springmvc.model.Tariff;
import com.websystique.springmvc.model.TariffOption;
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
    public void setStatus(NewStatusDto newStatusDto) {
        dao.findById(newStatusDto.getEntityId()).setStatus(newStatusDto.getEntityStatus());
    }

    @Override
    public Contract findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean addOptions(GetContractAsJsonDtoById getContractAsJsonDtoById) {
        List<Integer> optionIdList = new ArrayList<>();
        for( GetOptionsAsJsonDto getOptionsAsJsonDto : getContractAsJsonDtoById.getGetOptionsAsJsonDtoList()){
            optionIdList.add(getOptionsAsJsonDto.getId());
        }
        Set<TariffOption> contractOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Contract contract = dao.findById(getContractAsJsonDtoById.getContractId());
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
    public boolean adminAddOptions(GetContractAsJsonDtoById getContractAsJsonDtoById) {
        addOptions(getContractAsJsonDtoById);
        return false;
    }

    @Override
    public boolean customerAddOptions(GetContractAsJsonDtoById getContractAsJsonDtoById) {
        if(dao.findById(getContractAsJsonDtoById.getContractId()).getStatus().equals(Status.ACTIVE)){
            addOptions(getContractAsJsonDtoById);
        }
        return false;
    }

    @Override
    public boolean delOptions(GetContractAsJsonDtoById getContractAsJsonDtoById) {
        List<Integer> optionIdList = new ArrayList<>();
        for( GetOptionsAsJsonDto getOptionsAsJsonDto : getContractAsJsonDtoById.getGetOptionsAsJsonDtoList()){
            optionIdList.add(getOptionsAsJsonDto.getId());
        }
        Set<TariffOption> contractOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Contract contract = dao.findById(getContractAsJsonDtoById.getContractId());

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
    public boolean adminDelOptions(GetContractAsJsonDtoById getContractAsJsonDtoById) {
        delOptions(getContractAsJsonDtoById);
        return false;
    }

    @Override
    public boolean customerDelOptions(GetContractAsJsonDtoById getContractAsJsonDtoById) {
        if(dao.findById(getContractAsJsonDtoById.getContractId()).getStatus().equals(Status.ACTIVE)){
            delOptions(getContractAsJsonDtoById);
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
    public void newContract(ContractUserIdDto contractUserIdDto) {
        Tariff tariff = tariffService.findById(contractUserIdDto.getContractDto().getTariffId());
        Double price = tariff.getPrice();
        Contract contract = new Contract();
        contract.setPhoneNumber(contractUserIdDto.getContractDto().getPhoneNumber());
        contract.setUser(userService.findById(contractUserIdDto.getContractDto().getUserId()));
        contract.setTariff(tariff);
        List<Integer> optionIdList = new ArrayList<>();
        for( GetOptionsAsJsonDto getOptionsAsJsonDto : contractUserIdDto.getGetOptionsAsJsonDtoList()){
            optionIdList.add(getOptionsAsJsonDto.getId());
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
