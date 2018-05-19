package com.infosystem.springmvc.service.dataservice;

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
import java.util.Set;

@Service("dataService")
@Transactional
public class DataServiceImpl implements DataService {

    private final TariffOptionService tariffOptionService;
    private final TariffService tariffService;
    private final UserService userService;
    private final ContractService contractService;
    private final CustomModelMapper modelMapper;

    @Autowired
    public DataServiceImpl(TariffOptionService tariffOptionService, TariffService tariffService,
                           UserService userService, ContractService contractService, CustomModelMapper modelMapper) {
        this.tariffOptionService = tariffOptionService;
        this.tariffService = tariffService;
        this.userService = userService;
        this.contractService = contractService;
        this.modelMapper = modelMapper;
    }

    /**
     * @return AdminPanelDto
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
     * @param optionId optionId
     * @return TariffOptionPageDto
     * @throws DatabaseException if no such option
     */
    @Override
    public TariffOptionPageDto getTariffOptionPageData(Integer optionId) throws DatabaseException {
        Set<TariffOption> options = new HashSet<>(tariffOptionService.findAllTariffOptions());
        TariffOption option = tariffOptionService.findById(optionId);
        options.remove(option);
        return new TariffOptionPageDto(modelMapper.mapToTariffOptionDto(option), modelMapper.mapToTariffOptionDtoSet(options));
    }
}
