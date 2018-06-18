package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.jms.JmsDataMapper;
import com.infosystem.springmvc.model.entity.*;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService {


    private ContractService contractService;
    private final TariffDao dao;
    private CustomModelMapper modelMapperWrapper;
    private OptionsRulesChecker optionsRulesChecker;
    private AdvProfileService advProfileService;
    private JmsDataMapper jmsDataMapper;

    public void setAdvProfileService(AdvProfileService advProfileService) {
        this.advProfileService = advProfileService;
    }

    @Autowired
    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @Autowired
    public void setModelMapperWrapper(CustomModelMapper modelMapperWrapper) {
        this.modelMapperWrapper = modelMapperWrapper;
    }

    @Autowired
    public void setOptionsRulesChecker(OptionsRulesChecker optionsRulesChecker) {
        this.optionsRulesChecker = optionsRulesChecker;
    }

    public void setJmsDataMapper(JmsDataMapper jmsDataMapper) {
        this.jmsDataMapper = jmsDataMapper;
    }

    @Autowired
    public TariffServiceImpl(TariffDao dao) {
        this.dao = dao;
    }

    public Tariff findById(int id) throws DatabaseException {
        Tariff tariff = dao.findById(id);
        return tariff;
    }

    public Tariff findByName(String name) {
        return dao.findByName(name);
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
     * @param addTariffDto addTariffDto
     */
    public void addTariff(AddTariffDto addTariffDto) throws LogicException {
        Tariff tariff = findByName(addTariffDto.getTariffDto().getName());
        if (tariff != null) {
            throw new LogicException("Chose another name for tariff.");
        }
        tariff = modelMapperWrapper.mapToTariff(addTariffDto);
        Set<TariffOption> toBeAddedOptionsList = modelMapperWrapper.mapToTariffOptionSet(addTariffDto.getTariffOptionDtoList());
        optionsRulesChecker.checkAddRelatedAdmin(toBeAddedOptionsList, tariff.getAvailableOptions());
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
        for (AdvProfile advProfile : advProfileService.findAll()) {
            AdvProfileTariffs advProfileTariffs = advProfile.getAdvProfileTariffsList().stream()
                    .filter(advProfileTariff -> advProfileTariff.getTariff().equals(tariff)).findFirst().orElse(null);
            if (advProfileTariffs != null) {
                throw new LogicException("Tariff is still used in advertisment.");
            }
        }
        dao.deleteById(id);
    }

    /**
     * Add selected options to selected tariff.
     *
     * @param editTariffDto editTariffDto
     * @throws DatabaseException if tariff doesn't exist
     */
    @Override
    public void addOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException {
        Tariff tariff = findById(editTariffDto.getTariffId());
        Set<TariffOption> toBeAddedOptions = modelMapperWrapper.mapToTariffOptionSet(editTariffDto.getTariffOptionDtoList());
        optionsRulesChecker.checkIfTariffAlreadyHave(tariff, toBeAddedOptions);
        optionsRulesChecker.checkAddRelatedAdmin(toBeAddedOptions, tariff.getAvailableOptions());
        tariff.getAvailableOptions().addAll(toBeAddedOptions);
        jmsDataMapper.tariffAddOptions(editTariffDto.getTariffId(), editTariffDto.getTariffOptionDtoList());
    }

    /**
     * Delete selected options from selected tariff.
     *
     * @param editTariffDto editTariffDto
     * @throws DatabaseException if tariff doesn't exist
     */
    @Override
    public void delOptions(EditTariffDto editTariffDto) throws DatabaseException, LogicException {
        Tariff tariff = findById(editTariffDto.getTariffId());
        Set<TariffOption> toBeDeletedOptions = modelMapperWrapper.mapToTariffOptionSet(editTariffDto.getTariffOptionDtoList());

        optionsRulesChecker.checkDelRalated(toBeDeletedOptions, tariff.getAvailableOptions());

        tariff.getAvailableOptions().removeAll(modelMapperWrapper.mapToTariffOptionSet(editTariffDto.getTariffOptionDtoList()));
        jmsDataMapper.tariffDelOptions(editTariffDto.getTariffId(), editTariffDto.getTariffOptionDtoList());
    }

    @Override
    public Set<TariffOptionDtoShort> getAvailableOptionsForTariff(int tariffId) throws DatabaseException {
        return modelMapperWrapper.mapToTariffOptionDtoShortSet(findById(tariffId).getAvailableOptions());
    }

    @Override
    public List<Tariff> findListOfTariffs(int startIndex, int count) {
        return dao.findListOfTariffs(startIndex, count);
    }

    @Override
    public int getPagesCount(int itemsPerPage) {
        return (dao.tariffCount() - 1) / itemsPerPage + 1;
    }


    /**
     * Set status of selected tariff to selected status.
     *
     * @param setNewStatusDto setNewStatusDto
     * @throws DatabaseException if tariff doesn't exist
     */
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException, LogicException {
        Tariff tariff = findById(setNewStatusDto.getEntityId());
        for (AdvProfile advProfile : advProfileService.findAll()) {
            AdvProfileTariffs advProfileTariffs = advProfile.getAdvProfileTariffsList().stream()
                    .filter(advProfileTariff -> advProfileTariff.getTariff().equals(tariff)).findFirst().orElse(null);
            if (advProfileTariffs != null) {
                throw new LogicException("Tariff is still used in advertisment.");
            }
        }
        tariff.setStatus(modelMapperWrapper.mapToStatus(setNewStatusDto.getEntityStatus()));
    }
}
