package com.infosystem.springmvc.service.dataservice;

import com.infosystem.springmvc.dto.AdminPanelDto;
import com.infosystem.springmvc.dto.ContractPageDto;
import com.infosystem.springmvc.dto.TariffOptionPageDto;
import com.infosystem.springmvc.dto.TariffPageDto;
import com.infosystem.springmvc.exception.DatabaseException;

public interface DataService {

    AdminPanelDto getAdminPanelData();

    ContractPageDto getContractPageData(Integer contractId) throws DatabaseException;

    TariffPageDto getTariffPageData(Integer tariffId) throws DatabaseException;

    TariffOptionPageDto getTariffOptionPageData(Integer optionId) throws DatabaseException;
}
