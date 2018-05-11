package com.infosystem.springmvc.util;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dao.TariffOptionDao;
import com.infosystem.springmvc.dao.UserDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.model.Contract;
import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;
import com.infosystem.springmvc.model.User;
import com.infosystem.springmvc.service.TariffOptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CustomModelMapper {

    private ContractDao contractDao;
    private TariffDao tariffDao;
    private TariffOptionDao tariffOptionDao;
    private UserDao userDao;

    private TariffOptionService tariffOptionService;

    private ModelMapper modelMapper = new ModelMapper();
    {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }
    @Autowired
    public CustomModelMapper(ContractDao contractDao, TariffDao tariffDao, TariffOptionDao tariffOptionDao,
                             UserDao userDao, TariffOptionService tariffOptionService) {
        this.contractDao = contractDao;
        this.tariffDao = tariffDao;
        this.tariffOptionDao = tariffOptionDao;
        this.userDao = userDao;
        this.tariffOptionService = tariffOptionService;
    }


    /**
     * @param addContractDto
     * @return contract with phone number, tariff and user according to DTO data.
     */
    public Contract mapToContract(AddContractDto addContractDto) {
        Contract contract = new Contract();

        Tariff tariff = tariffDao.findById(addContractDto.getContractDto().getTariffId());
        User user = userDao.findById(addContractDto.getContractDto().getUserId());

        contract.setPhoneNumber(addContractDto.getContractDto().getPhoneNumber());
        contract.setTariff(tariff);
        contract.setUser(user);
        return contract;
    }

    /**
     * @param tariffOptionDtoList
     * @return tariffOption list(set) with ID's from tariffOptionDtoList
     */
    public Set<TariffOption> mapToTariffOptionList(List<TariffOptionDto> tariffOptionDtoList){
        List<Integer> optionIdList = new ArrayList<>();
        for( TariffOptionDto tariffOptionDto : tariffOptionDtoList){
            optionIdList.add(tariffOptionDto.getId());
        }
        return tariffOptionService.selectListByIdList(optionIdList);
    }

    /**
     * @param addTariffDto
     * @return tariff with name and price from addTariffDto
     */
    public Tariff mapToTariff(AddTariffDto addTariffDto){
        Tariff tariff = new Tariff();
        tariff.setName(addTariffDto.getTariffDto().getName());
        tariff.setPrice(addTariffDto.getTariffDto().getPrice());
        return tariff;
    }

    /**
     * @param addUserDto
     * @return user
     */
    public User mapToUser(AddUserDto addUserDto){
        return modelMapper.map(addUserDto,User.class);
    }

    /**
     * @param addTariffOptionDto
     * @return TariffOption
     */
    public TariffOption mapToTariffOption(AddTariffOptionDto addTariffOptionDto){
        return modelMapper.map(addTariffOptionDto,TariffOption.class);
    }
}
