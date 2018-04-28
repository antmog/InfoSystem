package com.websystique.springmvc.dao;


import com.websystique.springmvc.model.TariffOption;
import com.websystique.springmvc.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("TariffOptionDao")
public class TariffOptionDaoImpl extends AbstractDao<Integer, TariffOption> implements TariffOptionDao {

    public TariffOption findById(int id) {
        TariffOption tariffOption = getByKey(id);
        if(tariffOption!=null){
            Hibernate.initialize(tariffOption);
        }
        return tariffOption;
    }

    @SuppressWarnings("unchecked")
    public List<TariffOption> findAllTariffOptions() {
        List<TariffOption> tariffOptions = getSession()
                .createQuery("SELECT t FROM TariffOption t")
                .getResultList();
        return tariffOptions;
    }

    public void save(TariffOption tariffOption) {
        persist(tariffOption);
    }

    public void deleteById(int id) {
        TariffOption tariffOption = (TariffOption) getSession()
                .createQuery("SELECT t FROM TariffOption t WHERE t.id LIKE :Id")
                .setParameter("Id", id)
                .getSingleResult();
        delete(tariffOption);
    }



}
