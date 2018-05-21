package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffOptionDao {

    TariffOption findById(int id);

    void save(TariffOption tariffOption);

    List<TariffOption> findAllTariffOptions();

    void deleteById(int id);

    TariffOption findByName(String name);

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);

    int tariffOptionCount();

    List<TariffOption> findListOfTariffOptions(int startIndex, int count);
}
