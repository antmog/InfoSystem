package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.dto.AddTariffDto;
import com.infosystem.springmvc.dto.EditTariffDto;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
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

    public Tariff findById(int id) {
        return dao.findById(id);
    }

    public Tariff findByName(String name) {return dao.findByName(name);}

    public void addTariff(Tariff tariff) {
        dao.save(tariff);
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

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTariff(Tariff tariff) {
        Tariff entity = dao.findById(tariff.getId());
        if (entity != null) {

        }
    }


    public List<Tariff> findAllTariffs() {
        return dao.findAllTariffs();
    }

    public List<Tariff> findAllActiveTariffs() {
        return dao.findAllActiveTariffs();
    }

    /**
     * @param id
     * @throws LogicException if tariff is still used in any contracts
     */
    public void deleteTariffById(int id) throws LogicException, DatabaseException {
        Tariff tariff = dao.findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            if (contract.getTariff().equals(tariff)) {
                throw new LogicException("Tariff is still used.");
            }
        }
        dao.deleteById(id);
    }

    @Override
    public List<Tariff> findFirstTariffs() {
        return dao.findAllTariffs().stream().limit(5).collect(Collectors.toList());
    }

    @Override
    public void addOptions(EditTariffDto editTariffDto) {
        Tariff tariff = dao.findById(editTariffDto.getTariffId());
        Set<TariffOption> tariffOptionList = modelMapperWrapper.mapToTariffOptionList(editTariffDto.getTariffOptionDtoList());
        tariffOptionList.addAll(tariff.getAvailableOptions());
        tariff.setAvailableOptions(tariffOptionList);
    }

    @Override
    public void delOptions(EditTariffDto editTariffDto) {
        Tariff tariff = dao.findById(editTariffDto.getTariffId());
        Set<TariffOption> tariffOptionList = modelMapperWrapper.mapToTariffOptionList(editTariffDto.getTariffOptionDtoList());
        Set<TariffOption> newTariffOptionList = tariff.getAvailableOptions();
        newTariffOptionList.removeAll(tariffOptionList);
        tariff.setAvailableOptions(newTariffOptionList);
    }

    public void setStatus(SetNewStatusDto setNewStatusDto) {
        dao.findById(setNewStatusDto.getEntityId()).setStatus(setNewStatusDto.getEntityStatus());
    }

    private boolean isNameUnique(String tariffName) {
        Tariff tariff = findByName(tariffName);
        return (tariff == null);
    }

}
