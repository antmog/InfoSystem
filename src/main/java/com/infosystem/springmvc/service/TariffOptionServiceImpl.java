package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffOptionDao;
import com.infosystem.springmvc.dto.AddTariffOptionDto;
import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.enums.TariffOptionRule;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("tariffOptionService")
@Transactional
public class TariffOptionServiceImpl implements TariffOptionService {

    @Autowired
    private ContractService contractService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private TariffOptionDao dao;

    @Autowired
    CustomModelMapper modelMapperWrapper;

    public TariffOption findById(int id) throws DatabaseException {
        TariffOption tariffOption = dao.findById(id);
        if (tariffOption == null) {
            throw new DatabaseException("TariffOption doesn't exist.");
        }
        return tariffOption;
    }

    public TariffOption findByName(String name) {
        return dao.findByName(name);
    }

    public void saveTariffOption(TariffOption tariffOption) {
        dao.save(tariffOption);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTariffOption(TariffOption tariffOption) throws DatabaseException {
        TariffOption entity = findById(tariffOption.getId());
        if (entity != null) {
            // logic
        }
    }


    public List<TariffOption> findAllTariffOptions() {
        return dao.findAllTariffOptions();
    }

    public List<TariffOption> findFirstTariffOptions() {
        return dao.findAllTariffOptions().stream().limit(5).collect(Collectors.toList());
    }

    @Override
    public void addTariffOption(AddTariffOptionDto addTariffOptionDto) {
        TariffOption tariffOption = modelMapperWrapper.mapToTariffOption(addTariffOptionDto);
        saveTariffOption(tariffOption);
    }

    @Override
    public Set<TariffOption> selectListByIdList(List<Integer> optionIdList) {
        return dao.selectListByIdList(optionIdList);
    }

    /**
     * Deletes tariffOption if its not used.
     *
     * @param id
     * @throws DatabaseException if tariffOption doesn't exist
     * @throws LogicException    if tariffOption is still used
     */
    public void deleteTariffOptionById(int id) throws DatabaseException, LogicException {
        TariffOption tariffOption = findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            for (TariffOption option : contract.getActiveOptions()) {
                if (tariffOption.equals(option)) {
                    //todo log (warn)
                    throw new LogicException("This option is still used.");
                }
            }
        }
        for (Tariff tariff : tariffService.findAllTariffs()) {
            for (TariffOption option : tariff.getAvailableOptions()) {
                if (tariffOption.equals(option)) {
                    throw new LogicException("This option is still used.");
                }
            }
        }
        dao.deleteById(id);
    }

    /**
     * Adding rule to option depending on input.
     *
     * @param tariffOptionId
     * @param tariffOptionDtoList
     * @throws DatabaseException if option doesn't exist
     * @throws LogicException    if trying to make rule for itself
     */
    @Override
    public void addRuleTariffOptions(Integer tariffOptionId, List<TariffOptionDto> tariffOptionDtoList, TariffOptionRule rule) throws DatabaseException, LogicException {
        Set<TariffOption> optionList = modelMapperWrapper.mapToTariffOptionSet(tariffOptionDtoList);
        TariffOption tariffOption = findById(tariffOptionId);
        isWrongRule(tariffOption, optionList);
        if (rule.equals(TariffOptionRule.RELATED)) {
            Set<TariffOption> expectedTariffOptions = new HashSet<>(tariffOption.getRelatedTariffOptions());
            expectedTariffOptions.addAll(optionList);
            if (!Collections.disjoint(tariffOption.getExcludingTariffOptions(), optionList)) {
                throw new LogicException("One or more of chosen options are in exclude list.");
            }
            for (TariffOption option : optionList) {
                if (!Collections.disjoint(option.getExcludingTariffOptions(), expectedTariffOptions)) {
                    throw new LogicException("Some of option you want to add are excluding each other, or current related.");
                }
            }
            optionList.forEach(relatedTariffOption -> relatedTariffOption.getIsRelatedFor().add(tariffOption));
            tariffOption.getRelatedTariffOptions().addAll(optionList);
        }
        if (rule.equals(TariffOptionRule.EXCLUDING)) {
            for(TariffOption toBeExcluded : optionList){
                if(!Collections.disjoint(tariffOption.getRelatedTariffOptions(),toBeExcluded.getIsRelatedFor())){
                    throw new LogicException("One or more of chosen options are related for one of related options.");
                }
            }
            if (!Collections.disjoint(tariffOption.getRelatedTariffOptions(), optionList)) {
                throw new LogicException("One or more of chosen options are in related list.");
            }
            if (!tariffOption.getIsRelatedFor().isEmpty()) {
                for (TariffOption isRelatedForThis : tariffOption.getIsRelatedFor()) {
                    if(optionList.contains(isRelatedForThis)){
                        throw new LogicException("This option is related for one of chosen.");
                    }
                    if (!Collections.disjoint(isRelatedForThis.getRelatedTariffOptions(), optionList)) {
                        throw new LogicException("Cant exclude one or more options. " + isRelatedForThis.getName() +
                                " option has current option, with one or more options you want to exclude, in related list.");
                    }
                }
            }
            tariffOption.getExcludingTariffOptions().addAll(optionList);
            optionList.forEach(excludingTariffOption -> excludingTariffOption.getExcludingTariffOptions().add(tariffOption));
        }
    }

    /**
     * Deleting rule to option depending on input.
     *
     * @param tariffOptionId
     * @param tariffOptionDtoList
     * @throws DatabaseException if option doesn't exist
     * @throws LogicException    if trying to make rule for itself
     */
    @Override
    public void delRuleTariffOptions(Integer tariffOptionId, List<TariffOptionDto> tariffOptionDtoList, TariffOptionRule rule) throws LogicException, DatabaseException {
        Set<TariffOption> optionList = modelMapperWrapper.mapToTariffOptionSet(tariffOptionDtoList);
        TariffOption tariffOption = findById(tariffOptionId);
        isWrongRule(tariffOption, optionList);
        if (rule.equals(TariffOptionRule.RELATED)) {
            optionList.forEach(relatedTariffOption -> relatedTariffOption.getIsRelatedFor().remove(tariffOption));
            tariffOption.getRelatedTariffOptions().removeAll(optionList);
        }
        if (rule.equals(TariffOptionRule.EXCLUDING)) {
            tariffOption.getExcludingTariffOptions().removeAll(optionList);
            optionList.forEach(excludingTariffOption -> excludingTariffOption.getExcludingTariffOptions().remove(tariffOption));
        }
    }

    /**
     * Check if trying to make rule for itself (tariffOption).
     *
     * @param tariffOption
     * @param tariffOptions
     * @return
     */
    private void isWrongRule(TariffOption tariffOption, Set<TariffOption> tariffOptions) throws LogicException {
        if (tariffOptions.remove(tariffOption)) {
            throw new LogicException("Oh, cmon, hacker)00)0");
        }
    }

    public boolean isTariffOptionUnique(String tariffOptionName) {
        TariffOption tariffOption = findByName(tariffOptionName);
        return (tariffOption == null);
    }
}
