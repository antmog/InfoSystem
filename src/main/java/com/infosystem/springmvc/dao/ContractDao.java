package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.Contract;

import java.util.List;

public interface ContractDao {

    Contract findById(int id);

    void save(Contract contract);

    List<Contract> findAllContracts();

    void deleteById(int id);

    Contract findByPhoneNumber(String phoneNumber);
}
