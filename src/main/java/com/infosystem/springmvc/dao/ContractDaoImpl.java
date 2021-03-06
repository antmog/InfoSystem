package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.service.TariffOptionServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDao<Integer, Contract> implements ContractDao {

    public Contract findById(int id) throws DatabaseException {
        Contract contract = getByKey(id);
        if (contract == null) {
            String exceptionMessage = "Contract doesn't exist.";
            throw new DatabaseException(exceptionMessage);
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
        List contracts = getSession()
                .createQuery("SELECT c FROM Contract c WHERE c.id LIKE :Id")
                .setParameter("Id", id)
                .getResultList();
        delete((Contract) contracts.get(0));
    }


    /**
     * Searching for contract in DB with selected phoneNumber.
     *
     * @param phoneNumber phoneNumber
     * @return contract with phoneNumber or null if there is no contract with that number.
     */
    @Override
    public Contract findByPhoneNumber(String phoneNumber) {
        List contractList = getSession()
                .createQuery("SELECT c FROM Contract c WHERE c.phoneNumber LIKE :phoneNumber")
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
        if (!contractList.isEmpty()) {
            return (Contract) contractList.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Contract> findListOfContracts(int startIndex, int count) {
        if (count == 0) {
            return new ArrayList<>();
        }
        List<Contract> contracts = getSession()
                .createQuery("SELECT u FROM Contract u ORDER BY u.id")
                .setFirstResult(startIndex - 1)
                .setMaxResults(count)
                .getResultList();
        return new ArrayList<>(contracts);
    }

    @Override
    public int contractCount() {
        int count = ((Long) getSession()
                .createQuery("select count(*) from Contract")
                .uniqueResult()).intValue();
        return count;
    }
}
