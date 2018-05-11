package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.Contract;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDao<Integer, Contract> implements ContractDao {

    public Contract findById(int id) {
        Contract contract = getByKey(id);
        if (contract != null) {
            Hibernate.initialize(contract);
        }
        return contract;
    }

    @SuppressWarnings("unchecked")
    public List<Contract> findAllContracts() {
        List<Contract> contracts = getSession()
                .createQuery("SELECT c FROM Contract c")
                .getResultList();
        return contracts;
    }

    public void save(Contract contract) {
        persist(contract);
    }

    public void deleteById(int id) {
        Contract contract = (Contract) getSession()
                .createQuery("SELECT c FROM Contract c WHERE c.id LIKE :Id")
                .setParameter("Id", id)
                .getSingleResult();
        delete(contract);
    }


    /**
     * Searching for contract in DB with selected phoneNumber.
     * @param phoneNumber
     * @return contract with phoneNumber or null if there is no contract with that number.
     */
    @Override
    public Contract findByPhoneNumber(String phoneNumber) {
        List contractList = getSession()
                .createQuery("SELECT c FROM Contract c WHERE c.phoneNumber LIKE :phoneNumber")
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
        if (!contractList.isEmpty()) {
            return (Contract)contractList.get(0);
        }
        return null;
    }
}