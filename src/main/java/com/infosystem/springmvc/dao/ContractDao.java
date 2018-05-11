package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.Contract;

import java.util.List;

public interface ContractDao {

    Contract findById(int id);

    void save(Contract contract);

    List<Contract> findAllContracts();

    void deleteById(int id) throws DatabaseException;

    Contract findByPhoneNumber(String phoneNumber);
}
