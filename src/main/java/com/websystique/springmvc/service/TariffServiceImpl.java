package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.TariffDao;
import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.Tariff;
import com.websystique.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService{

    @Autowired
    private TariffDao dao;

    public Tariff findById(int id) {
        return dao.findById(id);
    }

    public void saveTariff(Tariff tariff) {
        dao.save(tariff);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTariff(Tariff tariff) {
        Tariff entity = dao.findById(tariff.getId());
        if(entity!=null){

        }
    }


    public List<Tariff> findAllTariffs() {
        return dao.findAllTariffs();
    }

    public void deleteTariffById(int id) {
        dao.deleteById(id);
    }

}
