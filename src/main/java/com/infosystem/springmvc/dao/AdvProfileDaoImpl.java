package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.AdvProfile;
import com.infosystem.springmvc.model.entity.Contract;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("advProfileDao")
public class AdvProfileDaoImpl extends AbstractDao<Integer, AdvProfile> implements AdvProfileDao {
    @SuppressWarnings("unchecked")
    public List<AdvProfile> findAllAdvProfiles() {
        List<AdvProfile> advProfileList = getSession()
                .createQuery("SELECT a FROM AdvProfile a")
                .getResultList();
        return advProfileList;
    }

    @Override
    public AdvProfile findById(Integer id) throws DatabaseException {
        AdvProfile advProfile = getByKey(id);
        if (advProfile == null) {
            throw new DatabaseException("AdvProfile doesn't exist.");
        }
        return advProfile;
    }
}
