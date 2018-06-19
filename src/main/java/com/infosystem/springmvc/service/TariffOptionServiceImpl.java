package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffOptionDao;
import com.infosystem.springmvc.dto.AddTariffOptionDto;
import com.infosystem.springmvc.dto.TariffOptionRulesDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.*;
import com.infosystem.springmvc.model.enums.TariffOptionRule;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("tariffOptionService")
@Transactional
public class TariffOptionServiceImpl implements TariffOptionService {

    private ContractService contractService;
    private TariffService tariffService;
    private final TariffOptionDao dao;
    private CustomModelMapper modelMapperWrapper;

    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @Autowired
    public void setModelMapperWrapper(CustomModelMapper modelMapperWrapper) {
        this.modelMapperWrapper = modelMapperWrapper;
    }

    @Autowired
    public TariffOptionServiceImpl(TariffOptionDao dao) {
        this.dao = dao;
    }

    public TariffOption findById(int id) throws DatabaseException {
        TariffOption tariffOption = dao.findById(id);
        return tariffOption;
    }

    public TariffOption findByName(String name) {
        return dao.findByName(name);
    }

    public void saveTariffOption(TariffOption tariffOption) {
        dao.save(tariffOption);
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
     * @param id id
     * @throws DatabaseException if tariffOption doesn't exist
     * @throws LogicException    if tariffOption is still used
     */
    public void deleteTariffOptionById(int id) throws DatabaseException, LogicException {
        String exceptionMessage = "This option is still used.";
        TariffOption tariffOption = findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            for (TariffOption option : contract.getActiveOptions()) {
                if (tariffOption.equals(option)) {
                    throw new LogicException(exceptionMessage);
                }
            }
        }
        for (Tariff tariff : tariffService.findAllTariffs()) {
            for (TariffOption option : tariff.getAvailableOptions()) {
                if (tariffOption.equals(option)) {
                    throw new LogicException(exceptionMessage);
                }
            }
        }
        dao.deleteById(id);
    }

    /**
     * Adding rule to option depending on input.
     *
     * @throws DatabaseException if option doesn't exist
     * @throws LogicException    if trying to make rule for itself
     */
    @Override
    public void addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto) throws DatabaseException, LogicException {
        TariffOptionRule rule = TariffOptionRule.valueOf(tariffOptionRulesDto.getRule());
        HashSet<TariffOption> realRelatedOptions;
        Set<TariffOption> optionList = modelMapperWrapper.mapToTariffOptionSet(tariffOptionRulesDto.getTariffOptionDtoList());
        TariffOption tariffOption = findById(tariffOptionRulesDto.getTariffOptionId());

        isWrongRule(tariffOption, optionList);
        if (rule.equals(TariffOptionRule.RELATED)) {
            HashSet<TariffOption> expectedSet = new HashSet<>();
            HashSet<TariffOption> topSet = findTop(tariffOption);
            for (TariffOption tariffOptionTop : topSet) {
                realRelatedOptions = generateRealRelatedOptionList(tariffOptionTop.getRelatedTariffOptions());
                realRelatedOptions.add(tariffOptionTop);
                expectedSet.addAll(realRelatedOptions);
            }
            for (TariffOption toBeAddedOption : optionList) {
                topSet = findTop(toBeAddedOption);
                for (TariffOption tariffOptionTop : topSet) {
                    realRelatedOptions = generateRealRelatedOptionList(tariffOptionTop.getRelatedTariffOptions());
                    realRelatedOptions.add(tariffOptionTop);
                    expectedSet.addAll(realRelatedOptions);
                }
            }
            for (TariffOption option : expectedSet) {
                option.getExcludingTariffOptions().retainAll(expectedSet);
                if (!option.getExcludingTariffOptions().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    option.getExcludingTariffOptions().forEach(anotherOption -> sb.append(anotherOption.getName()).append(";"));
                    String exceptionMessage = "Options " + sb.toString() + " exclude " + option.getName() + ".";
                    throw new LogicException(exceptionMessage);
                }
            }
            optionList.forEach(relatedTariffOption -> relatedTariffOption.getIsRelatedFor().add(tariffOption));
            tariffOption.getRelatedTariffOptions().addAll(optionList);
        }
        if (rule.equals(TariffOptionRule.EXCLUDING)) {
            HashSet<TariffOption> topSet = findTop(tariffOption);
            for (TariffOption tariffOptionTop : topSet) {
                realRelatedOptions = generateRealRelatedOptionList(tariffOptionTop.getRelatedTariffOptions());
                realRelatedOptions.add(tariffOptionTop);
                realRelatedOptions.retainAll(optionList);
                if (!realRelatedOptions.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    realRelatedOptions.forEach(option -> sb.append(option.getName()).append(";"));
                    String exceptionMessage = "Cant exclude options " + sb.toString() + ".";
                    throw new LogicException(exceptionMessage);
                }
            }
            tariffOption.getExcludingTariffOptions().addAll(optionList);
            optionList.forEach(excludingTariffOption -> excludingTariffOption.getExcludingTariffOptions().add(tariffOption));
        }
    }

    private HashSet<TariffOption> findTop(TariffOption tariffOption) {
        HashSet<TariffOption> topSet = new HashSet<>();
        anotherIsRelatedFor(tariffOption, topSet);
        return topSet;
    }

    private void anotherIsRelatedFor(TariffOption anotherIsRelatedFor, HashSet<TariffOption> topSet) {
        if (anotherIsRelatedFor.getIsRelatedFor().isEmpty()) {
            topSet.add(anotherIsRelatedFor);
        }
        for (TariffOption isRelatedFor : anotherIsRelatedFor.getIsRelatedFor()) {
            anotherIsRelatedFor(isRelatedFor, topSet);
        }
    }

    private HashSet<TariffOption> generateRealRelatedOptionList
            (Set<TariffOption> anotherRelatedOptionList) {
        HashSet<TariffOption> realRelatedOptions = new HashSet<>();
        anotherRelatedFor(anotherRelatedOptionList, realRelatedOptions);
        return realRelatedOptions;
    }

    private void anotherRelatedFor(Set<TariffOption> anotherRelatedOptionList, HashSet<TariffOption> realRelatedOptions) {
        realRelatedOptions.addAll(anotherRelatedOptionList);
        for (TariffOption tariffOption : anotherRelatedOptionList) {
            anotherRelatedFor(tariffOption.getRelatedTariffOptions(), realRelatedOptions);
        }
    }

    /**
     * Deleting rule to option depending on input.
     *
     * @throws DatabaseException if option doesn't exist
     * @throws LogicException    if trying to make rule for itself
     */
    @Override
    public void delRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto) throws LogicException, DatabaseException {
        TariffOptionRule rule = TariffOptionRule.valueOf(tariffOptionRulesDto.getRule());

        Set<TariffOption> optionList = modelMapperWrapper.mapToTariffOptionSet(tariffOptionRulesDto.getTariffOptionDtoList());
        TariffOption tariffOption = findById(tariffOptionRulesDto.getTariffOptionId());

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

    @Override
    public List<TariffOption> findListOfTariffOptions(int startIndex, int count) {
        return dao.findListOfTariffOptions(startIndex, count);
    }

    @Override
    public int getPagesCount(int itemsPerPage) {
        return (dao.tariffOptionCount() - 1) / itemsPerPage + 1;
    }

    /**
     * Check if trying to make rule for itself (tariffOption).
     *
     * @param tariffOption  tariffOption
     * @param tariffOptions tariffOptions
     */
    private void isWrongRule(TariffOption tariffOption, Set<TariffOption> tariffOptions) throws LogicException {
        if (tariffOptions.remove(tariffOption)) {
            String exceptionMessage = "Oh, cmon, hacker)00)0";
            throw new LogicException(exceptionMessage);
        }
    }

    public boolean isTariffOptionUnique(String tariffOptionName) {
        TariffOption tariffOption = findByName(tariffOptionName);
        return (tariffOption == null);
    }
}
