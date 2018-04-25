package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.TariffOption;

import java.util.List;

public interface TariffOptionDao {
    TariffOption findById(int id);

    void save(TariffOption tariffOption);

    void deleteById(int id);

    List<TariffOption> findAllTariffOptions();

}
