package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.model.entity.Tariff;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("TariffDao")
public class TariffDaoImpl extends AbstractDao<Integer, Tariff> implements TariffDao {

    public Tariff findById(int id) throws DatabaseException {
        Tariff tariff = getByKey(id);
        if (tariff == null) {
            throw new DatabaseException("Tariff doesn't exist.");
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

    public void deleteById(int id){
        List tariffs = getSession()
                .createQuery("SELECT t FROM Tariff t WHERE t.id LIKE :Id")
                .setParameter("Id", id)
                .getResultList();
        delete((Tariff)tariffs.get(0));
    }

    @SuppressWarnings("unchecked")
    public List<Tariff> findListOfTariffs(int startIndex, int count) {
        if (count == 0) {
            return new ArrayList<>();
        }
        List<Tariff> tariffs = getSession()
                .createQuery("SELECT u FROM Tariff u ORDER BY u.id")
                .setFirstResult(startIndex - 1)
                .setMaxResults(count)
                .getResultList();
        return new ArrayList<>(tariffs);
    }

    @Override
    public int tariffCount() {
        int count = ((Long) getSession()
                .createQuery("select count(*) from Tariff")
                .uniqueResult()).intValue();
        return count;
    }

}
