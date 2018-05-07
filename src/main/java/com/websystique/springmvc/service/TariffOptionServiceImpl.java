package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.TariffOptionDao;
import com.websystique.springmvc.model.Contract;
import com.websystique.springmvc.model.Tariff;
import com.websystique.springmvc.model.TariffOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("tariffOptionService")
@Transactional
public class TariffOptionServiceImpl implements TariffOptionService{

    @Autowired
    private ContractService contractService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private TariffOptionDao dao;

    public TariffOption findById(int id) {
        return dao.findById(id);
    }

    public void saveTariffOption(TariffOption tariffOption) {
        dao.save(tariffOption);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTariffOption(TariffOption tariffOption) {
        TariffOption entity = dao.findById(tariffOption.getId());
        if(entity!=null){
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

    public String deleteTariffOptionById(int id) {
        TariffOption tariffOption = dao.findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            for(TariffOption option : contract.getActiveOptions()){
                if(tariffOption.equals(option)){
                    return "This option is still used.";
                }
            }
        }
        for(Tariff tariff: tariffService.findAllTariffs()){
            for(TariffOption option : tariff.getAvailableOptions()){
                if(tariffOption.equals(option)){
                    return "This option is still used.";
                }
            }
        }
        // TBD: delete from RULES table ALSO
        dao.deleteById(id);
        return "ok";
    }
}
