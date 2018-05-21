package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.entity.Tariff;

import java.util.List;

public interface TariffDao {

    Tariff findById(int id);

    void save(Tariff tariff);

    List<Tariff> findAllTariffs();

    void deleteById(int id);

    Tariff findByName(String name);

    List<Tariff> findAllActiveTariffs();

    int tariffCount();

    List<Tariff> findListOfTariffs(int startIndex, int count);
}
