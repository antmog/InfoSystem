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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public AdminPanelDto getAdminPanelData() {
        return new AdminPanelDto(userService.findFirstUsers(),
                tariffService.findFirstTariffs(),tariffOptionService.findFirstTariffOptions());
    }

    @Override
    public TariffPageDto getTariffPageData(Integer tariff_id) throws DatabaseException {
        return new TariffPageDto(tariffService.findById(tariff_id),tariffOptionService.findAllTariffOptions());
    }

    @Override
    public ContractPageDto getContractPageData(Integer contractId) throws DatabaseException {
        return new ContractPageDto(contractService.findById(contractId),tariffService.findAllActiveTariffs());
    }

    @Override
    public TariffOptionPageDto getTariffOptionPageData(Integer optionId) throws DatabaseException {
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();
        TariffOption option = tariffOptionService.findById(optionId);
        options.remove(option);
        return new TariffOptionPageDto(option,options);
    }
}
