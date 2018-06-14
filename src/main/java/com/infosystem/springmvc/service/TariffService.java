package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.User;

import java.util.List;
import java.util.Set;

public interface TariffService {

    Tariff findById(int id) throws DatabaseException;

    Tariff findByName(String name);

    void addTariff(AddTariffDto tariff) throws LogicException;

    List<Tariff> findAllTariffs();

    List<Tariff> findAllActiveTariffs();

    void deleteTariffById(int id) throws LogicException, DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException, LogicException;

    List<Tariff> findFirstTariffs();

    void addOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException;

    void delOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException;

    Set<TariffOptionDtoShort> getAvailableOptionsForTariff(int tariffId) throws DatabaseException;

    List<Tariff> findListOfTariffs(int startIndex, int count);

    int getPagesCount(int itemsPerPage);
}
