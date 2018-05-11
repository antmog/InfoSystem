package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddContractDto;
import com.infosystem.springmvc.dto.EditContractDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.dto.SwitchTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Contract;

import java.util.List;

public interface ContractService {

    Contract findById(int id);

    void saveContract(Contract contract);

    void updateContract(Contract contract);


    List<Contract> findAllContracts();

    void deleteContractById(int id) throws DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto);

    Contract findByPhoneNumber(String phoneNumber);

    boolean addOptions(EditContractDto editContractDto);
    boolean adminAddOptions(EditContractDto editContractDto);
    boolean customerAddOptions(EditContractDto editContractDto);

    boolean delOptions(EditContractDto editContractDto);
    boolean adminDelOptions(EditContractDto editContractDto);
    boolean customerDelOptions(EditContractDto editContractDto);

    void switchTariff(SwitchTariffDto switchTariffDto);
    void adminSwitchTariff(SwitchTariffDto switchTariffDto);
    void customerSwitchTariff(SwitchTariffDto switchTariffDto);

    void newContract(AddContractDto addContractDto) throws LogicException;
}
