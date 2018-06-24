package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.util.OptionsRulesChecker;

import java.util.List;

public interface ContractService {

    Contract findById(int id) throws DatabaseException;

    void saveContract(Contract contract);

    List<Contract> findAllContracts();

    void deleteContractById(int id) throws DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException, ValidationException;

    Contract findByPhoneNumber(String phoneNumber);

    void adminAddOptions(EditContractDto editContractDto) throws DatabaseException, LogicException;

    void customerAddOptions(AddOptionsDto addOptionsDto) throws DatabaseException, LogicException, ValidationException;

    void adminDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException;

    void customerDelOptions(EditContractDto editContractDto) throws DatabaseException, LogicException, ValidationException;

    void adminSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException;

    void customerSwitchTariff(SwitchTariffDto switchTariffDto) throws DatabaseException, LogicException, ValidationException;

    void newContract(AddContractDto addContractDto) throws LogicException, DatabaseException;

    List<Contract> findListOfContracts(int startIndex, int count);

    int getPagesCount(int itemsPerPage);

    void setOptionsRulesChecker(OptionsRulesChecker optionsRulesChecker);
}
