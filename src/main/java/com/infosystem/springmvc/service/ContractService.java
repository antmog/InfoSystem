package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddContractDto;
import com.infosystem.springmvc.dto.EditContractDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.dto.SwitchTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.Contract;

import java.util.List;

public interface ContractService {

    Contract findById(int id) throws DatabaseException;

    void saveContract(Contract contract);

    void updateContract(Contract contract) throws DatabaseException;


    List<Contract> findAllContracts();

    void deleteContractById(int id) throws DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException;

    Contract findByPhoneNumber(String phoneNumber);

    void adminAddOptions(EditContractDto editContractDto) throws DatabaseException, LogicException;
    void customerAddOptions() throws DatabaseException, LogicException;

    void adminDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException;
    void customerDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException;

    void adminSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException;
    void customerSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException;

    void newContract(AddContractDto addContractDto) throws LogicException, DatabaseException;
}
