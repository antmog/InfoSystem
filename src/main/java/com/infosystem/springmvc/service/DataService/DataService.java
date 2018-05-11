package com.infosystem.springmvc.service.DataService;

import com.infosystem.springmvc.dto.AdminPanelDto;
import com.infosystem.springmvc.dto.ContractPageDto;
import com.infosystem.springmvc.dto.TariffPageDto;
import com.infosystem.springmvc.exception.DatabaseException;

public interface DataService {
    AdminPanelDto getAdminPanelData();
    ContractPageDto getContractPageData(Integer contractId) throws DatabaseException;
    TariffPageDto getTariffPageData(Integer tariff_id) throws DatabaseException;
}
