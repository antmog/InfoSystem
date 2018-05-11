package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffOptionDao;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("tariffOptionService")
@Transactional
public class TariffOptionServiceImpl implements TariffOptionService {

    @Autowired
    private ContractService contractService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private TariffOptionDao dao;

    public TariffOption findById(int id) throws DatabaseException {
        TariffOption tariffOption = dao.findById(id);
        if (tariffOption == null) {
            throw new DatabaseException("TariffOption doesn't exist.");
        }
        return tariffOption;
    }

    public void saveTariffOption(TariffOption tariffOption) {
        dao.save(tariffOption);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTariffOption(TariffOption tariffOption) throws DatabaseException {
        TariffOption entity = findById(tariffOption.getId());
        if (entity != null) {
            // logic
        }
    }


    public List<TariffOption> findAllTariffOptions() {
        return dao.findAllTariffOptions();
    }

    public List<TariffOption> findFirstTariffOptions() {
        return dao.findAllTariffOptions().stream().limit(5).collect(Collectors.toList());
    }

    @Override
    public Set<TariffOption> selectListByIdList(List<Integer> optionIdList) {
        return dao.selectListByIdList(optionIdList);
    }

    /**
     * Deletes tariffOption if its not used.
     * @param id
     * @throws DatabaseException if tariffOption doesn't exist
     * @throws LogicException if tariffOption is still used
     */
    public void deleteTariffOptionById(int id) throws DatabaseException, LogicException {
        TariffOption tariffOption = findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            for (TariffOption option : contract.getActiveOptions()) {
                if (tariffOption.equals(option)) {
                    throw new LogicException("This option is still used.");
                }
            }
        }
        for (Tariff tariff : tariffService.findAllTariffs()) {
            for (TariffOption option : tariff.getAvailableOptions()) {
                if (tariffOption.equals(option)) {
                    throw new LogicException("This option is still used.");
                }
            }
        }
        dao.deleteById(id);
    }
}
