package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.entity.Contract;

import java.util.List;

public interface ContractDao {

    Contract findById(int id);

    void save(Contract contract);

    List<Contract> findAllContracts();

    void deleteById(int id);

    Contract findByPhoneNumber(String phoneNumber);

    @SuppressWarnings("unchecked")
    List<Contract> findListOfContracts(int startIndex, int count);

    int contractCount();
}
