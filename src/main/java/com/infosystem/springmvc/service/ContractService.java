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

    Contract findById(int id) throws DatabaseException;

    void saveContract(Contract contract);

    void updateContract(Contract contract) throws DatabaseException;


    List<Contract> findAllContracts();

    void deleteContractById(int id) throws DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException;

    Contract findByPhoneNumber(String phoneNumber);

    void addOptions(EditContractDto editContractDto) throws DatabaseException;
    void adminAddOptions(EditContractDto editContractDto) throws DatabaseException;
    void customerAddOptions(EditContractDto editContractDto) throws DatabaseException;

    void delOptions(EditContractDto editContractDto) throws DatabaseException;
    void adminDelOptions(EditContractDto editContractDto) throws DatabaseException;
    void customerDelOptions(EditContractDto editContractDto) throws DatabaseException;

    void switchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException;
    void adminSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException;
    void customerSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException;

    void newContract(AddContractDto addContractDto) throws LogicException;
}
