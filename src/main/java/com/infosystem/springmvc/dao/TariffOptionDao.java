package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.entity.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffOptionDao {
    TariffOption findById(int id);

    TariffOption findByName(String name);

    void save(TariffOption tariffOption);

    void deleteById(int id);

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);
}
