package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.TariffOptionDao;
import com.websystique.springmvc.model.TariffOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service("tariffOptionService")
@Transactional
public class TariffOptionServiceImpl implements TariffOptionService{

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

    @Override
    public Set<TariffOption> selectListByIdList(List<Integer> optionIdList) {
        return dao.selectListByIdList(optionIdList);
    }

    public void deleteTariffOptionById(int id) {
        dao.deleteById(id);
    }
}
