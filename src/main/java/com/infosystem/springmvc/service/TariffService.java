package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddTariffDto;
import com.infosystem.springmvc.dto.EditTariffDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffService {

    Tariff findById(int id) throws DatabaseException;

    Tariff findByName(String name);

    void addTariff(Tariff tariff);

    void addTariff(AddTariffDto tariff) throws LogicException;

    void updateTariff(Tariff tariff) throws DatabaseException;

    List<Tariff> findAllTariffs();

    List<Tariff> findAllActiveTariffs();

    void deleteTariffById(int id) throws LogicException, DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException;

    List<Tariff> findFirstTariffs();

    void addOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException;

    void delOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException;

    Set<TariffOption> getAvailableOptionsForTariff(int tariffId) throws DatabaseException;
}
