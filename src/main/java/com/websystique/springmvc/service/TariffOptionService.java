package com.websystique.springmvc.service;

import com.websystique.springmvc.model.TariffOption;

import java.util.List;

public interface TariffOptionService {

    TariffOption findById(int id);

    void saveTariffOption(TariffOption tariffOption);

    void updateTariffOption(TariffOption tariffOption);

    List<TariffOption> findAllTariffOptions();

    void deleteTariffOptionById(int id);
}
