package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.AdvProfileDao;
import com.infosystem.springmvc.dto.AdvProfileDto;
import com.infosystem.springmvc.dto.AdvProfileTariffDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.jms.AdvJmsDataMapper;
import com.infosystem.springmvc.model.entity.AdvProfile;
import com.infosystem.springmvc.model.entity.AdvProfileTariffs;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("advProfileService")
@Transactional
public class AdvProfileServiceImpl implements AdvProfileService {

    private final AdvProfileDao advProfileDao;
    private final CustomModelMapper customModelMapper;
    private final TariffService tariffService;

    @Autowired
    private AdvJmsDataMapper advJmsDataMapper;

    @Autowired
    public AdvProfileServiceImpl(AdvProfileDao advProfileDao, CustomModelMapper customModelMapper, TariffService tariffService) {
        this.advProfileDao = advProfileDao;
        this.customModelMapper = customModelMapper;
        this.tariffService = tariffService;
    }

    @PostConstruct
    public void init() {
        tariffService.setAdvProfileService(this);
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
    public void addTariffToProfile(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, LogicException, ValidationException {
        AdvProfile advProfile = findById(advProfileTariffDto.getAdvProfileId());
        AdvProfileTariffs advProfileTariffs = new AdvProfileTariffs(advProfile, tariffService.findById(advProfileTariffDto.getTariffId()));
        advProfileTariffs.setImg(advProfileTariffDto.getImg());
        advProfileTariffDto.setTariffName(tariffService.findById(advProfileTariffDto.getTariffId()).getName());
        if (advProfile.getAdvProfileTariffsList().contains(advProfileTariffs)) {
            String exceptionMessage = "Profile alrdy has that tariff.";
            throw new LogicException(exceptionMessage);
        }
        advProfile.getAdvProfileTariffsList().add(advProfileTariffs);
        advJmsDataMapper.advProfileAddTariff(advProfileTariffDto.getTariffId(),advProfileTariffDto.getAdvProfileId());
    }

    @Override
    public void advProfileEditTariff(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, ValidationException {
        AdvProfile advProfile = findById(advProfileTariffDto.getAdvProfileId());
        getAdvProfileTariffs(advProfileTariffDto,advProfile).setImg(advProfileTariffDto.getImg());
        advJmsDataMapper.advProfileTariffImgChanged(advProfileTariffDto.getTariffId(),
                advProfileTariffDto.getAdvProfileId(),advProfileTariffDto.getImg());
    }

    @Override
    public void advProfileDeleteTariff(AdvProfileTariffDto advProfileTariffDto) throws DatabaseException, ValidationException {
        AdvProfile advProfile = findById(advProfileTariffDto.getAdvProfileId());
        advProfile.getAdvProfileTariffsList().remove(getAdvProfileTariffs(advProfileTariffDto,advProfile));
        advJmsDataMapper.advProfileDeleteTariff(advProfileTariffDto.getTariffId(),advProfileTariffDto.getAdvProfileId());
    }

    @Override
    public void activate(int advProfileId) throws DatabaseException, LogicException, ValidationException {
        AdvProfile advProfile = findById(advProfileId);
        if(advProfile.getStatus().equals(Status.ACTIVE)){
            String exceptionMessage = "Profile is alrdy active.";
            throw new LogicException(exceptionMessage);
        }
        List<AdvProfile> advProfileList = advProfileDao.findAllAdvProfiles();
        advProfileList.forEach(anotherAdvProfile -> anotherAdvProfile.setStatus(Status.INACTIVE));
        advProfile.setStatus(Status.ACTIVE);
        advJmsDataMapper.advProfileActivate();
    }

    private AdvProfileTariffs getAdvProfileTariffs(AdvProfileTariffDto advProfileTariffDto, AdvProfile advProfile) throws DatabaseException {
        advProfileTariffDto.setTariffName(tariffService.findById(advProfileTariffDto.getTariffId()).getName());
        AdvProfileTariffs advProfileTariffs = advProfile.getAdvProfileTariffsList().stream()
                .filter(advProfileTariff -> advProfileTariff.getTariff().getId().equals(advProfileTariffDto.getTariffId()))
                .findFirst().orElse(null);
        if(advProfileTariffs==null){
            String exceptionMessage = "No such tariff for current profile.";
            throw new DatabaseException(exceptionMessage);
        }
        return advProfileTariffs;
    }
}
