package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.TariffDao;
import com.websystique.springmvc.dto.GetOptionsAsJsonDto;
import com.websystique.springmvc.dto.GetTarifAsJsonDto;
import com.websystique.springmvc.model.Tariff;
import com.websystique.springmvc.model.TariffOption;
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

    public Tariff findById(int id) {
        return dao.findById(id);
    }

    public void saveTariff(Tariff tariff) {
        dao.save(tariff);
    }


    public void saveTariff(GetTarifAsJsonDto getTarifAsJsonDto) {
        Tariff newTariff = new Tariff();
        newTariff.setName(getTarifAsJsonDto.getTariffDto().getName());
        newTariff.setPrice(getTarifAsJsonDto.getTariffDto().getPrice());
        List<Integer> optionIdList = new ArrayList<>();
        for( GetOptionsAsJsonDto getOptionsAsJsonDto : getTarifAsJsonDto.getGetOptionsAsJsonDtoList()){
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

    public void deleteTariffById(int id) {
        dao.deleteById(id);
    }

    @Override
    public List<Tariff> findFirstTariffs() {
        return dao.findAllTariffs().stream().limit(5).collect(Collectors.toList());
    }

}
