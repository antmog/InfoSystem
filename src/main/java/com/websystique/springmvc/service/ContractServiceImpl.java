package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.ContractDao;
import com.websystique.springmvc.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao dao;

    public Contract findById(int id) {
        return dao.findById(id);
    }

    public void saveContract(Contract contract) {
        dao.save(contract);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateContract(Contract contract) {
        Contract entity = dao.findById(contract.getId());
        if(entity!=null){

        }
    }


    public List<Contract> findAllContracts() {
        return dao.findAllContracts();
    }

    @Override
    public void deleteContractById(int id) {
        dao.deleteById(id);
    }

}
