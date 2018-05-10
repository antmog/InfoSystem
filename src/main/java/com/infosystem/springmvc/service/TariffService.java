package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddTariffDto;
import com.infosystem.springmvc.dto.EditTariffDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.model.Tariff;

import java.util.List;

public interface TariffService {

    Tariff findById(int id);

    void saveTariff(Tariff tariff);

    void saveTariff(AddTariffDto tariff);

    void updateTariff(Tariff tariff);

    List<Tariff> findAllTariffs();

    List<Tariff> findAllActiveTariffs();

    String deleteTariffById(int id);

    void setStatus(SetNewStatusDto setNewStatusDto);

    List<Tariff> findFirstTariffs();

    boolean addOptions(EditTariffDto editTariffDto);

    boolean delOptions(EditTariffDto editTariffDto);
}
