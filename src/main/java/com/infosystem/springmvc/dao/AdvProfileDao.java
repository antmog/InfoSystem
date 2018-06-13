package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.AdvProfile;
import java.util.List;


public interface AdvProfileDao {
    public List<AdvProfile> findAllAdvProfiles();

    AdvProfile findById(Integer id) throws DatabaseException;
}
