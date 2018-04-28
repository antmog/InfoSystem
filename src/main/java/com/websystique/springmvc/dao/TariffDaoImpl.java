package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Tariff;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
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

    @SuppressWarnings("unchecked")
    public List<Tariff> findAllTariffs() {
        List<Tariff> tariff = getSession()
                .createQuery("SELECT t FROM Tariff t")
                .getResultList();
        return tariff;
    }

    public void save(Tariff tariff) {
        persist(tariff);
    }

    public void deleteById(int id) {
        Tariff tariff = (Tariff) getSession()
                .createQuery("SELECT t FROM Tariff t WHERE t.id LIKE :Id")
                .setParameter("Id", id)
                .getSingleResult();
        delete(tariff);
    }
}
