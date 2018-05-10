package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dto.GetOptionsAsJsonDto;
import com.infosystem.springmvc.dto.GetTarifAsJsonDto;
import com.infosystem.springmvc.dto.GetTarifAsJsonDtoById;
import com.infosystem.springmvc.dto.NewStatusDto;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
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

    public Tariff findById(int id) {
        return dao.findById(id);
    }

    public void saveTariff(Tariff tariff) {
        dao.save(tariff);
    }

    public void setStatus(NewStatusDto newStatusDto) {
        dao.findById(newStatusDto.getEntityId()).setStatus(newStatusDto.getEntityStatus());
    }

    public void saveTariff(GetTarifAsJsonDto getTarifAsJsonDto) {
        Tariff newTariff = new Tariff();
        newTariff.setName(getTarifAsJsonDto.getTariffDto().getName());
        newTariff.setPrice(getTarifAsJsonDto.getTariffDto().getPrice());
        List<Integer> optionIdList = new ArrayList<>();
        for (GetOptionsAsJsonDto getOptionsAsJsonDto : getTarifAsJsonDto.getGetOptionsAsJsonDtoList()) {
            optionIdList.add(getOptionsAsJsonDto.getId());
        }
        Set<TariffOption> tariffOptionList = tariffOptionService.selectListByIdList(optionIdList);
        newTariff.setAvailableOptions(tariffOptionList);
        dao.save(newTariff);

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

    public String deleteTariffById(int id) {
        Tariff tariff = dao.findById(id);
        for (Contract contract : contractService.findAllContracts()) {
            if (contract.getTariff().equals(tariff)) {
                return "Tariff is still used by some contracts.";
            }
        }
        dao.deleteById(id);
        return "ok";
    }

    @Override
    public List<Tariff> findFirstTariffs() {
        return dao.findAllTariffs().stream().limit(5).collect(Collectors.toList());
    }

    @Override
    public boolean addOptions(GetTarifAsJsonDtoById getTarifAsJsonDtoById) {
        List<Integer> optionIdList = new ArrayList<>();
        for (GetOptionsAsJsonDto getOptionsAsJsonDto : getTarifAsJsonDtoById.getGetOptionsAsJsonDtoList()) {
            optionIdList.add(getOptionsAsJsonDto.getId());
        }
        Set<TariffOption> tariffOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Tariff tariff = dao.findById(getTarifAsJsonDtoById.getTariffId());
        tariffOptionList.addAll(tariff.getAvailableOptions());
        tariff.setAvailableOptions(tariffOptionList);


        // LOGIC RULES ETC
        return false;
    }

    @Override
    public boolean delOptions(GetTarifAsJsonDtoById getTarifAsJsonDtoById) {
        List<Integer> optionIdList = new ArrayList<>();
        for (GetOptionsAsJsonDto getOptionsAsJsonDto : getTarifAsJsonDtoById.getGetOptionsAsJsonDtoList()) {
            optionIdList.add(getOptionsAsJsonDto.getId());
        }
        Set<TariffOption> tariffOptionList = tariffOptionService.selectListByIdList(optionIdList);
        Tariff tariff = dao.findById(getTarifAsJsonDtoById.getTariffId());

        Set<TariffOption> newTariffOptionList = tariff.getAvailableOptions();
        if (newTariffOptionList.removeAll(tariffOptionList)) {
            System.out.println("YES");
        }
        tariff.setAvailableOptions(newTariffOptionList);

        // LOGIC RULES ETC
        return false;
    }


}
