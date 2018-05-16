package com.infosystem.springmvc.service.DataService;

import com.infosystem.springmvc.dto.AdminPanelDto;
import com.infosystem.springmvc.dto.ContractPageDto;
import com.infosystem.springmvc.dto.TariffOptionPageDto;
import com.infosystem.springmvc.dto.TariffPageDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("dataService")
@Transactional
public class DataServiceImpl implements DataService {

    @Autowired
    TariffOptionService tariffOptionService;
    @Autowired
    TariffService tariffService;
    @Autowired
    UserService userService;
    @Autowired
    ContractService contractService;
    @Autowired
    CustomModelMapper modelMapper;

    /**
     *
     * @return
     */
    @Override
    public AdminPanelDto getAdminPanelData() {
        return new AdminPanelDto(modelMapper.mapToUserDtoList(userService.findFirstUsers()),
                modelMapper.mapToTariffDtoList(tariffService.findFirstTariffs()),
                modelMapper.mapToTariffOptionDtoSet(new HashSet<>(tariffOptionService.findFirstTariffOptions())));
    }

    @Override
    public TariffPageDto getTariffPageData(Integer tariffId) throws DatabaseException {
        return new TariffPageDto(modelMapper.mapToTariffDto(tariffService.findById(tariffId)),
                modelMapper.mapToTariffOptionDtoSet(new HashSet<>(tariffOptionService.findAllTariffOptions())));
    }

    @Override
    public ContractPageDto getContractPageData(Integer contractId) throws DatabaseException {
        return new ContractPageDto(modelMapper.mapToContractDto(contractService.findById(contractId)),
                modelMapper.mapToTariffDtoList(tariffService.findAllActiveTariffs()));
    }

    /**
     * @param optionId
     * @return
     * @throws DatabaseException
     */
    @Override
    public TariffOptionPageDto getTariffOptionPageData(Integer optionId) throws DatabaseException {
        Set<TariffOption> options = new HashSet<>(tariffOptionService.findAllTariffOptions());
        TariffOption option = tariffOptionService.findById(optionId);
        options.remove(option);
        return new TariffOptionPageDto(modelMapper.mapToTariffOptionDto(option),modelMapper.mapToTariffOptionDtoSet(options));
    }
}
