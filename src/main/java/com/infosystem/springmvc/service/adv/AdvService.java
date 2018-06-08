package com.infosystem.springmvc.service.adv;

import com.infosystem.springmvc.dto.adv.AdvInitialDataDto;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("advService")
@Transactional
public class AdvService {

    private DataService dataService;
    private CustomModelMapper customModelMapper;

    @Autowired
    public AdvService(DataService dataService, CustomModelMapper customModelMapper){
        this.dataService = dataService;
        this.customModelMapper = customModelMapper;
    }

    public AdvInitialDataDto getInitialAdvData() {
        AdvInitialDataDto advInitialDataDto = new AdvInitialDataDto();
        advInitialDataDto.setAdvTariffDtoList(customModelMapper.mapToList(AdvTariffDto.class,dataService.getIndexPageData()));
        return advInitialDataDto;
    }
}
