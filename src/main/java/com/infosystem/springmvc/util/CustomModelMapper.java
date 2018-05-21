package com.infosystem.springmvc.util;

import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dao.UserDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomModelMapper {

    @Autowired
    TariffOptionService tariffOptionService;
    @Autowired
    UserService userService;

    private TariffDao tariffDao;
    private UserDao userDao;
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Autowired
    public CustomModelMapper(TariffDao tariffDao, UserDao userDao) {
        this.tariffDao = tariffDao;
        this.userDao = userDao;
    }

    /**
     * @param type Dto type class
     * @param entity entity
     * @param <T> Dto type
     * @return Dto
     */
    public <T> T mapToDto(Class<T> type, Object entity) {
        return modelMapper.map(entity, type);
    }

    /**
     * @param type Entity type class
     * @param dto dto
     * @param <T> entity type
     * @return entity
     */
    public <T> T mapToEntity(Class<T> type, Object dto) {
        return modelMapper.map(dto, type);
    }

    /**
     * @param type Dto type class
     * @param entityList list of entities
     * @param <T> Dto type
     * @param <D> entity type
     * @return List of Dto
     */
    public <T,D> List<T> mapToDtoList(Class<T> type, List<D> entityList) {
        List<T> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(mapToDto(type,entity)));
        return dtoList;
    }

    /**
     * @param addContractDto addContractDto
     * @return contract with phone number, tariff and user according to DTO data.
     */
    public Contract mapToContract(AddContractDto addContractDto) throws DatabaseException {
        Contract contract = new Contract();

        Tariff tariff = tariffDao.findById(addContractDto.getContractDto().getTariffId());
        User user = userDao.findById(addContractDto.getContractDto().getUser().getId());

        contract.setPhoneNumber(addContractDto.getContractDto().getPhoneNumber());
        contract.setTariff(tariff);
        contract.setUser(user);
        return contract;
    }

    /**
     * @param tariffOptionDtoList tariffOptionDtoList
     * @return tariffOption list(set) with ID's from tariffOptionDtoList
     */
    public Set<TariffOption> mapToTariffOptionSet(Collection<TariffOptionDto> tariffOptionDtoList) {
        List<Integer> optionIdList = new ArrayList<>();
        for (TariffOptionDto tariffOptionDto : tariffOptionDtoList) {
            optionIdList.add(tariffOptionDto.getId());
        }
        return tariffOptionService.selectListByIdList(optionIdList);
    }

    /**
     * @param addTariffDto addTariffDto
     * @return tariff with name and price from addTariffDto
     */
    public Tariff mapToTariff(AddTariffDto addTariffDto) {
        Tariff tariff = new Tariff();
        tariff.setName(addTariffDto.getTariffDto().getName());
        tariff.setPrice(addTariffDto.getTariffDto().getPrice());
        return tariff;
    }



    /**
     * @param addTariffOptionDto addTariffOptionDto
     * @return TariffOption
     */
    public TariffOption mapToTariffOption(AddTariffOptionDto addTariffOptionDto) {
        return modelMapper.map(addTariffOptionDto, TariffOption.class);
    }

    /**
     * @param tariffOptionList tariffOptionList
     * @return tariffOption list(set) with ID's from tariffOptionDtoList
     */
    public Set<TariffOptionDto> mapToTariffOptionDtoSet(Set<TariffOption> tariffOptionList) {
        Set<TariffOptionDto> tariffOptionDtoList = new HashSet<TariffOptionDto>();
        tariffOptionList.forEach(tariffOption -> tariffOptionDtoList.add(modelMapper.map(tariffOption, TariffOptionDto.class)));
        return tariffOptionDtoList;
    }

    public Set<TariffOptionDtoShort> mapToTariffOptionDtoShortSet(Set<TariffOption> tariffOptionList) {
        Set<TariffOptionDtoShort> tariffOptionDtoList = new HashSet<>();
        tariffOptionList.forEach(tariffOption -> tariffOptionDtoList.add(modelMapper.map(tariffOption, TariffOptionDtoShort.class)));
        return tariffOptionDtoList;
    }

    public TariffOptionDto mapToTariffOptionDto(TariffOption tariffOption) {
        return modelMapper.map(tariffOption, TariffOptionDto.class);
    }



    public List<TariffDto> mapToTariffDtoList(List<Tariff> tariffs) {
        List<TariffDto> tariffDtoList = new ArrayList<>();
        tariffs.forEach(tariff -> tariffDtoList.add(modelMapper.map(tariff, TariffDto.class)));
        return tariffDtoList;
    }


    public ContractDto mapToContractDto(Contract contract) {
        return modelMapper.map(contract, ContractDto.class);
    }

    public TariffDto mapToTariffDto(Tariff tariff) {
        return modelMapper.map(tariff, TariffDto.class);
    }

    public Status mapToStatus(String status) {
        return modelMapper.map(status, Status.class);
    }
}
