package com.infosystem.springmvc.service.dataservice;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;

import java.util.List;

public interface DataService {

    UserDto getUserInfo(String login) throws DatabaseException;

    ContractPageDto getContractPageData(Integer contractId) throws DatabaseException;

    <T> AllEntitiesDto<T> getAllEntityPageData(Class<T> type, Integer pageNumber, int itemsPerPage);

    TariffPageDto getTariffPageData(Integer tariffId) throws DatabaseException;

    TariffOptionPageDto getTariffOptionPageData(Integer optionId) throws DatabaseException;

    List<TariffDto> findAllActiveTariffs();

    List<TariffOptionDtoShort> findAllTariffOptions();

    UserPageDto getUserPageDto(Integer userId) throws DatabaseException;

    UserFundsDto getUserAddFundsData(Integer userId) throws DatabaseException;

    EditUserDto getEditUserData(int userId) throws DatabaseException;

    UserPageDto getCustomerPageData(String login) throws DatabaseException;

    UserFundsDto getUserAddFundsData(String login) throws DatabaseException;

    ChangePasswordDto getChangePasswordData(int userIdInt) throws DatabaseException;

    List<TariffDto> getIndexPageData();
}
