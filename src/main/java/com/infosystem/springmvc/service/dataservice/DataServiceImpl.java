package com.infosystem.springmvc.service.dataservice;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public UserDto getUserInfo(String login) throws DatabaseException {
        return modelMapper.mapToDto(UserDto.class, userService.findByLogin(login));
    }

    /**
     * DTO containing list of entities of type <T> for page with all entities of specified type.
     *
     * @return allEntitiesDto
     */
    @Override
    public <T> AllEntitiesDto<T> getAllEntityPageData(Class<T> type, Integer pageNumber, int itemsPerPage) {
        AllEntitiesDto<T> allEntitiesDto = new AllEntitiesDto<>();
        int startIndex = (pageNumber - 1) * itemsPerPage + 1;
        List<T> entityDtoList = new ArrayList<>();
        if (type.equals(ContractDto.class)) {
            entityDtoList = modelMapper.mapToList(type, contractService.findListOfContracts(startIndex, itemsPerPage));
            allEntitiesDto.setPageCount(contractService.getPagesCount(itemsPerPage));
        } else if (type.equals(UserDto.class)) {
            entityDtoList = modelMapper.mapToList(type, userService.findListOfUsers(startIndex, itemsPerPage));
            allEntitiesDto.setPageCount(userService.getPagesCount(itemsPerPage));
        } else if (type.equals(TariffDto.class)) {
            entityDtoList = modelMapper.mapToList(type, tariffService.findListOfTariffs(startIndex, itemsPerPage));
            allEntitiesDto.setPageCount(tariffService.getPagesCount(itemsPerPage));
        } else if (type.equals(TariffOptionDtoShort.class)) {
            entityDtoList = modelMapper.mapToList(type, tariffOptionService.findListOfTariffOptions(startIndex, itemsPerPage));
            allEntitiesDto.setPageCount(tariffOptionService.getPagesCount(itemsPerPage));
        }
        allEntitiesDto.setEntityDtoList(entityDtoList);
        return allEntitiesDto;
    }

    /**
     * @return List<TariffDto> of all active tariffs
     */
    @Override
    public List<TariffDto> findAllActiveTariffs() {
        return modelMapper.mapToList(TariffDto.class, tariffService.findAllActiveTariffs());
    }

    /**
     * @return List<TariffOptionDtoShort> of all tariff options
     */
    @Override
    public List<TariffOptionDtoShort> findAllTariffOptions() {
        return modelMapper.mapToList(TariffOptionDtoShort.class, tariffOptionService.findAllTariffOptions());
    }

    /**
     * @param userId userId
     * @return UserPageDto for user with userId
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public UserPageDto getUserPageDto(Integer userId) throws DatabaseException {
        return modelMapper.mapToDto(UserPageDto.class, userService.findById(userId));
    }

    @Override
    public UserFundsDto getUserAddFundsData(Integer userId) throws DatabaseException {
        User user = userService.findById(userId);
        return new UserFundsDto(user.getBalance(),user.getId());
    }

    @Override
    public EditUserDto getEditUserData(int userId) throws DatabaseException {
        return modelMapper.mapToDto(EditUserDto.class, userService.findById(userId));
    }

    @Override
    public UserPageDto getCustomerPageData(String login) throws DatabaseException {
        return modelMapper.mapToDto(UserPageDto.class, userService.findByLogin(login));
    }

    @Override
    public UserFundsDto getUserAddFundsData(String login) throws DatabaseException {
        User user = userService.findByLogin(login);
        return new UserFundsDto(user.getBalance(),user.getId());
    }

    @Override
    public ChangePasswordDto getChangePasswordData(int userIdInt){
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setUserId(userIdInt);
        return changePasswordDto;
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
