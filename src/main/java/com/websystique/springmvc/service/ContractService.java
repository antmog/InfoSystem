package com.websystique.springmvc.service;

import com.websystique.springmvc.dto.ContractUserIdDto;
import com.websystique.springmvc.dto.GetContractAsJsonDtoById;
import com.websystique.springmvc.dto.NewStatusDto;
import com.websystique.springmvc.dto.SwitchTariffDto;
import com.websystique.springmvc.model.Contract;

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

    boolean delOptions(GetContractAsJsonDtoById getContractAsJsonDtoById);

    void switchTariff(SwitchTariffDto switchTariffDto);

    void newContract(ContractUserIdDto contractUserIdDto);
}
