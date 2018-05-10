package com.infosystem.springmvc.dao;

import com.infosystem.springmvc.model.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffOptionDao {
    TariffOption findById(int id);

    void save(TariffOption tariffOption);

    void deleteById(int id);

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);
}
