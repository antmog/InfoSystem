package com.websystique.springmvc.service;

import com.websystique.springmvc.model.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffOptionService {

    TariffOption findById(int id);

    void saveTariffOption(TariffOption tariffOption);

    void updateTariffOption(TariffOption tariffOption);

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);

    void deleteTariffOptionById(int id);
}
