package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dto.AddTariffDto;
import com.infosystem.springmvc.dto.EditTariffDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService {

    @Autowired
    private TariffDao dao;

    @Autowired
    private TariffOptionService tariffOptionService;

    @Autowired
    private ContractService contractService;

    @Autowired
    CustomModelMapper modelMapperWrapper;

    public Tariff findById(int id) throws DatabaseException {
        Tariff tariff = dao.findById(id);
        if (tariff == null) {
            throw new DatabaseException("Tariff doesn't exist.");
        }
        return tariff;
    }

    public Tariff findByName(String name) {
        return dao.findByName(name);
    }

    public void addTariff(Tariff tariff) {
        dao.save(tariff);
    }

    public void updateTariff(Tariff tariff) throws DatabaseException {
        Tariff entity = findById(tariff.getId());
        if (entity != null) {

        }
    }


    public List<Tariff> findAllTariffs() {
        return dao.findAllTariffs();
    }

    public List<Tariff> findAllActiveTariffs() {
        return dao.findAllActiveTariffs();
    }

    public List<Tariff> findFirstTariffs() {
        return dao.findAllTariffs().stream().limit(5).collect(Collectors.toList());
    }

    /**
     * Creates new tariff with data from DTO
     *
     * @param addTariffDto
     */
    public void addTariff(AddTariffDto addTariffDto) throws LogicException {
        if (!isNameUnique(addTariffDto.getTariffDto().getName())) {
            throw new LogicException("Chose another name for tariff.");
        }
        Tariff tariff = modelMapperWrapper.mapToTariff(addTariffDto);

        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionList(addTariffDto.getTariffOptionDtoList());

        //todo
        Set<TariffOption> optionExcludingOptions;
        for (TariffOption toBeAddedTariffOption : toBeAddedOptionsList) {
            optionExcludingOptions = new HashSet<>(toBeAddedTariffOption.getExcludingTariffOptions());
            optionExcludingOptions.retainAll(toBeAddedOptionsList);
            if (!optionExcludingOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : optionExcludingOptions) {
                    sb.append(toBeAddedTariffOption.getName()).append(" excludes ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }

        //todo
        Set<TariffOption> optionRelatedOptions;
        for (TariffOption toBeAddedTariffOption : toBeAddedOptionsList) {
            optionRelatedOptions = toBeAddedTariffOption.getRelatedTariffOptions();
            if (!toBeAddedOptionsList.containsAll(optionRelatedOptions)) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : optionRelatedOptions) {
                    sb.append(toBeAddedTariffOption.getName()).append(" related with ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }

        tariff.setAvailableOptions(toBeAddedOptionsList);

        dao.save(tariff);
    }

    /**
     * Deletes tariff if its not used.
     *
     * @param id
     * @throws LogicException    if tariff is still used in any contracts
     * @throws DatabaseException if tariff doesn't exist
     */
    public void deleteTariffById(int id) throws LogicException, DatabaseException {
        Tariff tariff = findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            if (contract.getTariff().equals(tariff)) {
                throw new LogicException("Tariff is still used.");
            }
        }
        dao.deleteById(id);
    }

    /**
     * Add selected options to selected tariff.
     *
     * @param editTariffDto
     * @throws DatabaseException if tariff doesn't exist
     */
    @Override
    public void addOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException {
        Tariff tariff = findById(editTariffDto.getTariffId());
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionList(editTariffDto.getTariffOptionDtoList());
        Set<TariffOption> expectedOptionsList = Stream.of(tariff.getAvailableOptions(), toBeAddedOptionsList).flatMap(Collection::stream).collect(Collectors.toSet());

        //todo
        Set<TariffOption> optionExcludingOptions;
        for (TariffOption expectedActiveTariffOption : expectedOptionsList) {
            optionExcludingOptions = new HashSet<>(expectedActiveTariffOption.getExcludingTariffOptions());
            optionExcludingOptions.retainAll(toBeAddedOptionsList);
            if (!optionExcludingOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : optionExcludingOptions) {
                    sb.append(expectedActiveTariffOption.getName()).append(" excludes ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }
        //todo
        Set<TariffOption> optionRelatedOptions;
        for (TariffOption toBeAddedOption : toBeAddedOptionsList) {
            optionRelatedOptions = toBeAddedOption.getRelatedTariffOptions();
            if (!expectedOptionsList.containsAll(optionRelatedOptions)) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : optionRelatedOptions) {
                    sb.append(toBeAddedOption.getName()).append(" related with ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }


        tariff.getAvailableOptions().addAll(toBeAddedOptionsList);
    }

    /**
     * Delete selected options from selected tariff.
     *
     * @param editTariffDto
     * @throws DatabaseException if tariff doesn't exist
     */
    @Override
    public void delOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException {
        Tariff tariff = findById(editTariffDto.getTariffId());
        Set<TariffOption> toBeDeletedOptionsList = modelMapperWrapper.mapToTariffOptionList(editTariffDto.getTariffOptionDtoList());

        //todo
        Set<TariffOption> expectedOptionsList = new HashSet<>(tariff.getAvailableOptions());
        expectedOptionsList.removeAll(toBeDeletedOptionsList);
        Set<TariffOption> optionRelatedOptions;
        for (TariffOption expectedActiveTariffOption : expectedOptionsList) {
            optionRelatedOptions = new HashSet<>(expectedActiveTariffOption.getRelatedTariffOptions());
            optionRelatedOptions.retainAll(toBeDeletedOptionsList);
            if (!optionRelatedOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (TariffOption tariffOption : expectedActiveTariffOption.getRelatedTariffOptions()) {
                    sb.append(expectedActiveTariffOption.getName()).append(" related with ").append(tariffOption.getName()).append(".\n");
                }
                throw new LogicException(sb.toString());
            }
        }

        tariff.getAvailableOptions().removeAll(toBeDeletedOptionsList);
    }

    @Override
    public Set<TariffOption> getAvailableOptionsForTariff(int tariffId) throws DatabaseException {
        return findById(tariffId).getAvailableOptions();
    }

    /**
     * Set status of selected tariff to selected status.
     *
     * @param setNewStatusDto
     * @throws DatabaseException if tariff doesn't exist
     */
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException {
        findById(setNewStatusDto.getEntityId()).setStatus(setNewStatusDto.getEntityStatus());
    }

    private boolean isNameUnique(String tariffName) {
        Tariff tariff = findByName(tariffName);
        return (tariff == null);
    }

}
