package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.AdvProfileDao;
import com.infosystem.springmvc.dto.AdvProfileDto;
import com.infosystem.springmvc.dto.AdvProfileTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.AdvProfile;
import com.infosystem.springmvc.model.entity.AdvProfileTariffs;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.List;

@Service("advProfileService")
@Transactional
public class AdvProfileServiceImpl implements AdvProfileService {

    private final AdvProfileDao advProfileDao;
    private final CustomModelMapper customModelMapper;
    private final TariffService tariffService;


    @Autowired
    public AdvProfileServiceImpl(AdvProfileDao advProfileDao, CustomModelMapper customModelMapper, TariffService tariffService) {
        this.advProfileDao = advProfileDao;
        this.customModelMapper = customModelMapper;
        this.tariffService = tariffService;
    }

    @Override
    public AdvProfile findById(Integer id) throws DatabaseException {
        return advProfileDao.findById(id);
    }

    @Override
    public List<AdvProfile> findAll() throws DatabaseException {
        return advProfileDao.findAllAdvProfiles();
    }

    //todo
    @Override
    public List<AdvProfile> findActive() throws DatabaseException {
        return null;
    }

    @Override
    public AdvProfileDto getProfileById(Integer id) throws DatabaseException {
        return customModelMapper.mapToDto(AdvProfileDto.class, findById(id));
    }

    @Override
    public void addTariffToProfile(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, LogicException {
        AdvProfile advProfile = findById(advProfileTariffDto.getAdvProfileId());
        AdvProfileTariffs advProfileTariffs = new AdvProfileTariffs(advProfile, tariffService.findById(advProfileTariffDto.getTariffId()));
        advProfileTariffs.setImg(advProfileTariffDto.getImg());
        advProfileTariffDto.setTariffName(tariffService.findById(advProfileTariffDto.getTariffId()).getName());
        if (advProfile.getAdvProfileTariffsList().contains(advProfileTariffs)) {
            throw new LogicException("Profile alrdy has that tariff.");
        }
        advProfile.getAdvProfileTariffsList().add(advProfileTariffs);
    }

    @Override
    public void advProfileEditTariff(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException {
        AdvProfile advProfile = findById(advProfileTariffDto.getAdvProfileId());
        getAdvProfileTariffs(advProfileTariffDto,advProfile).setImg(advProfileTariffDto.getImg());
    }

    @Override
    public void advProfileDeleteTariff(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException {
        AdvProfile advProfile = findById(advProfileTariffDto.getAdvProfileId());
        advProfile.getAdvProfileTariffsList().remove(getAdvProfileTariffs(advProfileTariffDto,advProfile));
    }

    @Override
    public void activate(int advProfileId) throws DatabaseException {
        List<AdvProfile> advProfileList = advProfileDao.findAllAdvProfiles();
        advProfileList.forEach(advProfile -> advProfile.setStatus(Status.INACTIVE));
        AdvProfile advProfile = findById(advProfileId);
        advProfile.setStatus(Status.ACTIVE);
    }

    private AdvProfileTariffs getAdvProfileTariffs(AdvProfileTariffDto advProfileTariffDto, AdvProfile advProfile) throws DatabaseException {
        advProfileTariffDto.setTariffName(tariffService.findById(advProfileTariffDto.getTariffId()).getName());
        AdvProfileTariffs advProfileTariffs = advProfile.getAdvProfileTariffsList().stream()
                .filter(advProfileTariff -> advProfileTariff.getTariff().getId().equals(advProfileTariffDto.getTariffId()))
                .findFirst().orElse(null);
        if(advProfileTariffs==null){
            throw new DatabaseException("No such tariff for current profile.");
        }
        return advProfileTariffs;
    }
}
