package com.infosystem.springmvc.jms;

import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.dto.adv.AdvTariffOptionDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.AdvProfile;
import com.infosystem.springmvc.model.entity.AdvProfileTariffs;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.AdvProfileService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.ToAdvertismentJmsJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void tariffAddOptions(int tariffId, List<TariffOptionDto> tariffOptionDtoList) throws DatabaseException {
        messageSender.initiateSend("addOptions", tariffService.findById(tariffId).getName(),
                customModelMapper.mapToList(AdvTariffOptionDto.class, tariffOptionDtoList));
    }

    public void tariffDelOptions(int tariffId, List<TariffOptionDto> tariffOptionDtoList) throws DatabaseException {
        messageSender.initiateSend("delOptions", tariffService.findById(tariffId).getName(),
                customModelMapper.mapToList(AdvTariffOptionDto.class, tariffOptionDtoList));
    }


    public void advProfileAddTariff(int tariffId, int advProfileId) throws DatabaseException {
        AdvProfile advProfile = advProfileService.findById(advProfileId);
        if (advProfile.getStatus().equals(Status.ACTIVE)) {
            AdvProfileTariffs advProfileTariffs = advProfile.getAdvProfileTariffsList().stream()
                    .filter(advProfileTariff -> advProfileTariff.getTariff().getId().equals(tariffId)).findFirst().orElse(null);
            if (advProfileTariffs == null) {
                throw new DatabaseException("No img for tariff id " + tariffId + " for profile " + advProfileId + ".");
            }
            AdvTariffDto advTariffDto = customModelMapper.mapToDto(AdvTariffDto.class, tariffService.findById(tariffId));
            advTariffDto.setImage(advProfileTariffs.getImg());
            messageSender.initiateSend("newTariff", advTariffDto);
        }
    }

    public void advProfileTariffImgChanged(int tariffId, int advProfileId, String img) throws DatabaseException {
        AdvProfile advProfile = advProfileService.findById(advProfileId);
        if (advProfile.getStatus().equals(Status.ACTIVE)) {
            messageSender.initiateSend("changeImage", tariffService.findById(tariffId).getName(), img);
        }
    }

    public void advProfileDeleteTariff(int tariffId, int advProfileId) throws DatabaseException {
        AdvProfile advProfile = advProfileService.findById(advProfileId);
        if (advProfile.getStatus().equals(Status.ACTIVE)) {
            messageSender.initiateSend("delTariff", tariffService.findById(tariffId).getName());
        }
    }

    public void advProfileActivate() throws DatabaseException {
        messageSender.initiateSend("changeProfile");
    }
}


