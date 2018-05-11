package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.Tariff;

import java.util.List;

public interface TariffDao {

    Tariff findById(int id);

    Tariff findByName(String name);

    void save(Tariff tariff);

    List<Tariff> findAllTariffs();

    List<Tariff> findAllActiveTariffs();

    void deleteById(int id) throws DatabaseException;
}
