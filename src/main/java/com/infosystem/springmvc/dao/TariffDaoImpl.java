package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.Status;
import com.infosystem.springmvc.model.Tariff;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TariffDao")
public class TariffDaoImpl extends AbstractDao<Integer, Tariff> implements TariffDao {

    public Tariff findById(int id) {
        Tariff tariff = getByKey(id);
        if(tariff!=null){
            Hibernate.initialize(tariff);
        }
        return tariff;
    }

    @Override
    public Tariff findByName(String name) {
        List tariffList = getSession()
                .createQuery("SELECT t FROM Tariff t WHERE t.name LIKE :name")
                .setParameter("name",name)
                .getResultList();
        if(!tariffList.isEmpty()){
            return (Tariff) tariffList.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Tariff> findAllTariffs() {
        List<Tariff> tariff = getSession()
                .createQuery("SELECT t FROM Tariff t")
                .getResultList();
        return tariff;
    }

    @SuppressWarnings("unchecked")
    public List<Tariff> findAllActiveTariffs() {
        List<Tariff> tariff = getSession()
                .createQuery("SELECT t FROM Tariff t WHERE t.status LIKE :status")
                .setParameter("status", Status.ACTIVE)
                .getResultList();
        return tariff;
    }

    public void save(Tariff tariff) {
        persist(tariff);
    }

    /**
     * @param id
     * @throws DatabaseException if entity doesnt exist
     */
    public void deleteById(int id){
        List tariffs = getSession()
                .createQuery("SELECT t FROM Tariff t WHERE t.id LIKE :Id")
                .setParameter("Id", id)
                .getResultList();
        delete((Tariff)tariffs.get(0));
    }

}
