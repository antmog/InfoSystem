package com.websystique.springmvc.service;

import com.websystique.springmvc.dto.NewStatusDto;
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
}
