package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Tariff;

import java.util.List;

public interface TariffService {

    Tariff findById(int id);

    void saveTariff(Tariff tariff);

    void updateTariff(Tariff tariff);

    List<Tariff> findAllTariffs();

    void deleteTariffById(int id);
}
