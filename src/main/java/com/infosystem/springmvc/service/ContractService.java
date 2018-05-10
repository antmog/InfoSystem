package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddContractDto;
import com.infosystem.springmvc.dto.ContractOptionsDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.dto.SwitchTariffDto;
import com.infosystem.springmvc.model.Contract;

import java.util.List;

public interface ContractService {

    Contract findById(int id);

    void saveContract(Contract contract);

    void updateContract(Contract contract);


    List<Contract> findAllContracts();

    void deleteContractById(int id);

    void setStatus(SetNewStatusDto setNewStatusDto);

    Contract findByPhoneNumber(String phoneNumber);

    boolean addOptions(ContractOptionsDto contractOptionsDto);
    boolean adminAddOptions(ContractOptionsDto contractOptionsDto);
    boolean customerAddOptions(ContractOptionsDto contractOptionsDto);

    boolean delOptions(ContractOptionsDto contractOptionsDto);
    boolean adminDelOptions(ContractOptionsDto contractOptionsDto);
    boolean customerDelOptions(ContractOptionsDto contractOptionsDto);

    void switchTariff(SwitchTariffDto switchTariffDto);
    void adminSwitchTariff(SwitchTariffDto switchTariffDto);
    void customerSwitchTariff(SwitchTariffDto switchTariffDto);

    void newContract(AddContractDto addContractDto);
}
