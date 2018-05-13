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


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Tariff findByName(String name) {return dao.findByName(name);}

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
     * @param addTariffDto
     */
    public void addTariff(AddTariffDto addTariffDto) throws LogicException {
        if (!isNameUnique(addTariffDto.getTariffDto().getName())){
            throw new LogicException("Chose another name for tariff.");
        }
        Tariff tariff = modelMapperWrapper.mapToTariff(addTariffDto);
        if (!addTariffDto.getTariffOptionDtoList().isEmpty()) {
            Set<TariffOption> tariffOptionList = modelMapperWrapper.mapToTariffOptionList(addTariffDto.getTariffOptionDtoList());
            tariff.setAvailableOptions(tariffOptionList);
        }
        dao.save(tariff);
    }

    /**
     * Deletes tariff if its not used.
     * @param id
     * @throws LogicException if tariff is still used in any contracts
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
     * @param editTariffDto
     * @throws DatabaseException if tariff doesn't exist
     */
    @Override
    public void addOptions(EditTariffDto editTariffDto) throws DatabaseException {
        Tariff tariff = findById(editTariffDto.getTariffId());
        Set<TariffOption> tariffOptionList = modelMapperWrapper.mapToTariffOptionList(editTariffDto.getTariffOptionDtoList());
        tariff.getAvailableOptions().addAll(tariffOptionList);
    }

    /**
     * Delete selected options from selected tariff.
     * @param editTariffDto
     * @throws DatabaseException if tariff doesn't exist
     */
    @Override
    public void delOptions(EditTariffDto editTariffDto) throws DatabaseException {
        Tariff tariff = findById(editTariffDto.getTariffId());
        Set<TariffOption> tariffOptionList = modelMapperWrapper.mapToTariffOptionList(editTariffDto.getTariffOptionDtoList());
        tariff.getAvailableOptions().removeAll(tariffOptionList);
    }

    @Override
    public Set<TariffOption> getAvailableOptionsForTariff(int tariffId) throws DatabaseException {
        return findById(tariffId).getAvailableOptions();
    }

    /**
     * Set status of selected tariff to selected status.
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
