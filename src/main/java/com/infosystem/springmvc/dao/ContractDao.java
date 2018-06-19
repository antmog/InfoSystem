package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.Contract;

import java.util.List;

public interface ContractDao {

    Contract findById(int id) throws DatabaseException;

    void save(Contract contract);

    List<Contract> findAllContracts();

    void deleteById(int id);

    Contract findByPhoneNumber(String phoneNumber);

    @SuppressWarnings("unchecked")
    List<Contract> findListOfContracts(int startIndex, int count);

    int contractCount();
}
