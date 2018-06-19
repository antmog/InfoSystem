package com.infosystem.springmvc.jms;

import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.dto.adv.AdvTariffOptionDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.AdvProfile;
import com.infosystem.springmvc.model.entity.AdvProfileTariffs;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.AdvProfileService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class JmsDataMapper {

    @Autowired
    private AdvProfileService advProfileService;

    private final MessageSender messageSender;
    private final TariffService tariffService;
    private final CustomModelMapper customModelMapper;

    @Autowired
    public JmsDataMapper(MessageSender messageSender,
                         TariffService tariffService, CustomModelMapper customModelMapper) {
        this.messageSender = messageSender;
        this.tariffService = tariffService;
        this.customModelMapper = customModelMapper;
    }

    @PostConstruct
    public void init() {
        tariffService.setJmsDataMapper(this);
    }

    public void tariffAddOptions(int tariffId, List<TariffOptionDto> tariffOptionDtoList) throws DatabaseException, ValidationException {
        messageSender.initiateSendEditTariff("addOptions", tariffService.findById(tariffId).getName(),
                customModelMapper.mapToList(AdvTariffOptionDto.class, tariffOptionDtoList));
    }

    public void tariffDelOptions(int tariffId, List<TariffOptionDto> tariffOptionDtoList) throws DatabaseException, ValidationException {
        messageSender.initiateSendEditTariff("delOptions", tariffService.findById(tariffId).getName(),
                customModelMapper.mapToList(AdvTariffOptionDto.class, tariffOptionDtoList));
    }


    public void advProfileAddTariff(int tariffId, int advProfileId) throws DatabaseException, ValidationException {
        AdvProfile advProfile = advProfileService.findById(advProfileId);
        if (advProfile.getStatus().equals(Status.ACTIVE)) {
            AdvProfileTariffs advProfileTariffs = advProfile.getAdvProfileTariffsList().stream()
                    .filter(advProfileTariff -> advProfileTariff.getTariff().getId().equals(tariffId)).findFirst().orElse(null);
            if (advProfileTariffs == null) {
                String exceptionMessage = "No img for tariff id " + tariffId + " for profile " + advProfileId + ".";
                throw new DatabaseException(exceptionMessage);
            }
            AdvTariffDto advTariffDto = customModelMapper.mapToDto(AdvTariffDto.class, tariffService.findById(tariffId));
            advTariffDto.setImage(advProfileTariffs.getImg());
            messageSender.initiateSendNewTariff(advTariffDto);
        }
    }

    public void advProfileTariffImgChanged(int tariffId, int advProfileId, String img) throws DatabaseException, ValidationException {
        AdvProfile advProfile = advProfileService.findById(advProfileId);
        if (advProfile.getStatus().equals(Status.ACTIVE)) {
            messageSender.initiateSendChangeImage(tariffService.findById(tariffId).getName(), img);
        }
    }

    public void advProfileDeleteTariff(int tariffId, int advProfileId) throws DatabaseException, ValidationException {
        AdvProfile advProfile = advProfileService.findById(advProfileId);
        if (advProfile.getStatus().equals(Status.ACTIVE)) {
            messageSender.initiateSendDelTariff(tariffService.findById(tariffId).getName());
        }
    }

    public void advProfileActivate() throws ValidationException {
        messageSender.initiateSend();
    }
}


