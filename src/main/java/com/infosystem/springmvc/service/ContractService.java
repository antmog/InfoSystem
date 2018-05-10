package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.ContractUserIdDto;
import com.infosystem.springmvc.dto.GetContractAsJsonDtoById;
import com.infosystem.springmvc.dto.NewStatusDto;
import com.infosystem.springmvc.dto.SwitchTariffDto;
import com.infosystem.springmvc.model.Contract;

import java.util.List;

public interface ContractService {

    Contract findById(int id);

    void saveContract(Contract contract);

    void updateContract(Contract contract);


    List<Contract> findAllContracts();

    void deleteContractById(int id);

    void setStatus(NewStatusDto newStatusDto);

    Contract findByPhoneNumber(String phoneNumber);

    boolean addOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);
    boolean adminAddOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);
    boolean customerAddOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);

    boolean delOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);
    boolean adminDelOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);
    boolean customerDelOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);

    void switchTariff(SwitchTariffDto switchTariffDto);
    void adminSwitchTariff(SwitchTariffDto switchTariffDto);
    void customerSwitchTariff(SwitchTariffDto switchTariffDto);

    void newContract(ContractUserIdDto contractUserIdDto);
}
