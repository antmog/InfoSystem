package com.infosystem.springmvc.service.adv;

import com.infosystem.springmvc.dto.adv.AdvInitialDataDto;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.AdvProfile;
import com.infosystem.springmvc.model.entity.AdvProfileTariffs;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.AdvProfileService;
import com.infosystem.springmvc.service.TariffOptionServiceImpl;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("advService")
@Transactional
public class AdvService {

    private final CustomModelMapper customModelMapper;
    private final AdvProfileService advProfileService;

    @Autowired
    public AdvService(CustomModelMapper customModelMapper, AdvProfileService advProfileService){
        this.customModelMapper = customModelMapper;
        this.advProfileService = advProfileService;
    }

    public AdvInitialDataDto getInitialAdvData() throws DatabaseException {
        AdvInitialDataDto advInitialDataDto = new AdvInitialDataDto();
        List<AdvTariffDto> advTariffDtoList = new ArrayList<>();

        AdvProfile advProfile = advProfileService.findAll().stream()
                .filter(anotherAdvProfile -> anotherAdvProfile.getStatus().equals(Status.ACTIVE)).findFirst().orElse(null);
        if(advProfile==null){
            String exceptionMessage = "No active advertisment profiles.";
            throw new DatabaseException(exceptionMessage);
        }
        for(AdvProfileTariffs advProfileTariffs:advProfile.getAdvProfileTariffsList()){
            AdvTariffDto advTariffDto = customModelMapper.mapToDto(AdvTariffDto.class,advProfileTariffs.getTariff());
            advTariffDto.setImage(advProfileTariffs.getImg());
            advTariffDtoList.add(advTariffDto);
        }

        advInitialDataDto.setAdvTariffDtoList(advTariffDtoList);
        return advInitialDataDto;
    }
}
