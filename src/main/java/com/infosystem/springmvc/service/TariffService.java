package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.GetTarifAsJsonDto;
import com.infosystem.springmvc.dto.GetTarifAsJsonDtoById;
import com.infosystem.springmvc.dto.NewStatusDto;
import com.infosystem.springmvc.model.Tariff;

import java.util.List;

public interface TariffService {

    Tariff findById(int id);

    void saveTariff(Tariff tariff);

    void saveTariff(GetTarifAsJsonDto tariff);

    void updateTariff(Tariff tariff);

    List<Tariff> findAllTariffs();

    List<Tariff> findAllActiveTariffs();

    String deleteTariffById(int id);

    void setStatus(NewStatusDto newStatusDto);

    List<Tariff> findFirstTariffs();

    boolean addOptions(GetTarifAsJsonDtoById getTarifAsJsonDtoById);

    boolean delOptions(GetTarifAsJsonDtoById getTarifAsJsonDtoById);
}
