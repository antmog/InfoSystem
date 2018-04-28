package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Contract;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
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
}