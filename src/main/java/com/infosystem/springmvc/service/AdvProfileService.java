package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AdvProfileDto;
import com.infosystem.springmvc.dto.AdvProfileTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.AdvProfile;

import java.util.List;

public interface AdvProfileService {

    AdvProfile findById(Integer id) throws DatabaseException;

    List<AdvProfile> findAll() throws DatabaseException;

    List<AdvProfile> findActive() throws DatabaseException;

    AdvProfileDto getProfileById(Integer id) throws DatabaseException;

    void addTariffToProfile(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, LogicException, ValidationException;

    void advProfileEditTariff(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, ValidationException;

    void advProfileDeleteTariff(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, ValidationException;

    void activate(int advProfileId) throws DatabaseException, LogicException, ValidationException;
}
