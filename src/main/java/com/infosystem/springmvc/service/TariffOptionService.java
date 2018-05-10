package com.infosystem.springmvc.service;

import com.infosystem.springmvc.model.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffOptionService {

    TariffOption findById(int id);

    void saveTariffOption(TariffOption tariffOption);

    void updateTariffOption(TariffOption tariffOption);

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);

    String deleteTariffOptionById(int id);

    List<TariffOption> findFirstTariffOptions();

}
