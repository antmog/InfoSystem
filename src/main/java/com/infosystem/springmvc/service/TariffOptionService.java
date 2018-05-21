package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddTariffOptionDto;
import com.infosystem.springmvc.dto.TariffOptionRulesDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import java.util.List;
import java.util.Set;

public interface TariffOptionService {

    TariffOption findById(int id) throws DatabaseException;

    TariffOption findByName(String name);

    void saveTariffOption(TariffOption tariffOption);

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);

    void deleteTariffOptionById(int id) throws DatabaseException, LogicException;

    List<TariffOption> findFirstTariffOptions();

    void addTariffOption(AddTariffOptionDto addTariffOptionDto);

    boolean isTariffOptionUnique(String tariffOptionName);

    void addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto) throws DatabaseException, LogicException;

    void delRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto) throws LogicException, DatabaseException;

    List<TariffOption> findListOfTariffOptions(int startIndex, int count);

    int getPagesCount(int itemsPerPage);
}
