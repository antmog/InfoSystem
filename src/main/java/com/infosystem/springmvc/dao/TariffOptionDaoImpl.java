package com.infosystem.springmvc.dao;


import com.infosystem.springmvc.model.entity.TariffOption;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("TariffOptionDao")
public class TariffOptionDaoImpl extends AbstractDao<Integer, TariffOption> implements TariffOptionDao {

    public TariffOption findById(int id) {
        TariffOption tariffOption = getByKey(id);
        if(tariffOption!=null){
            Hibernate.initialize(tariffOption);
        }
        return tariffOption;
    }

    @Override
    public TariffOption findByName(String name) {
        List tariffOptionList = getSession()
                .createQuery("SELECT t FROM TariffOption t WHERE t.name LIKE :name")
                .setParameter("name",name)
                .getResultList();
        if(!tariffOptionList.isEmpty()){
            return (TariffOption) tariffOptionList.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<TariffOption> findAllTariffOptions() {
        List<TariffOption> tariffOptions = getSession()
                .createQuery("SELECT t FROM TariffOption t")
                .getResultList();
        return tariffOptions;
    }

    @SuppressWarnings("unchecked")
    public Set<TariffOption> selectListByIdList(List<Integer> optionIdList) {
        if(optionIdList.isEmpty()){
            return new HashSet<>();
        }
        List<TariffOption> tariffOptions = getSession()
                .createQuery("SELECT t FROM TariffOption t WHERE t.id IN (:idList)")
                .setParameter("idList",optionIdList)
                .getResultList();
        return new HashSet<>(tariffOptions) ;
    }

    public void save(TariffOption tariffOption) {
        persist(tariffOption);
    }

    public void deleteById(int id){
        List tariffOptions = getSession()
                .createQuery("SELECT t FROM TariffOption t WHERE t.id LIKE :Id")
                .setParameter("Id", id)
                .getResultList();
        delete((TariffOption)tariffOptions.get(0));
    }

    @SuppressWarnings("unchecked")
    public List<TariffOption> findListOfTariffOptions(int startIndex, int count) {
        if (count == 0) {
            return new ArrayList<>();
        }
        List<TariffOption> tariffOptions = getSession()
                .createQuery("SELECT u FROM TariffOption u ORDER BY u.id")
                .setFirstResult(startIndex - 1)
                .setMaxResults(count)
                .getResultList();
        return new ArrayList<>(tariffOptions);
    }

    @Override
    public int tariffOptionCount() {
        int count = ((Long) getSession()
                .createQuery("select count(*) from TariffOption")
                .uniqueResult()).intValue();
        return count;
    }


}
