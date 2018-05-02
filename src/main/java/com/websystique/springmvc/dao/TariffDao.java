package com.websystique.springmvc.dao;

import com.websystique.springmvc.dto.GetOptionsAsJsonDto;
import com.websystique.springmvc.model.Tariff;

import java.util.List;

public interface TariffDao {

    Tariff findById(int id);

    void save(Tariff tariff);

    List<Tariff> findAllTariffs();

    void deleteById(int id);
}
