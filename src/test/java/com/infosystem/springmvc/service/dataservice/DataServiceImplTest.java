package com.infosystem.springmvc.service.dataservice;

import com.infosystem.springmvc.converters.JavaUtilDateToStringConverter;
import com.infosystem.springmvc.dao.AdvProfileDao;
import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.*;


import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.*;
import com.infosystem.springmvc.sessioncart.SessionCart;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * DataServiceImpl Tester.
 */
@ExtendWith(MockitoExtension.class)
class DataServiceImplTest {

    @Mock
    private TariffOptionService tariffOptionService;
    @Mock
    private TariffService tariffService;
    @Mock
    private UserService userService;
    @Mock
    private ContractService contractService;
    @Mock
    private CustomModelMapper modelMapper;
    @Mock
    private TariffDao tariffDao;
    @Mock
    private AdvProfileDao advProfileDao;
    @Mock
    private JavaUtilDateToStringConverter javaUtilDateToStringConverter;
    @Mock
    private List<String> imgList;

    @InjectMocks
    private DataServiceImpl dataService;

    @BeforeEach
    void setUp() {
    }

    /**
     * Method: getUserInfo(String login)
     */
    @Test
    void testGetUserInfo() throws Exception {
        String login = "login";
        UserDto userDto = new UserDto();
        when(modelMapper.mapToDto(UserDto.class, userService.findByLogin(login))).thenReturn(userDto);

        assertEquals(userDto, dataService.getUserInfo(login));
        verify(userService, atLeastOnce()).findByLogin(login);
    }

    /**
     * Method: getAllEntityPageData(Class<T> type, Integer pageNumber, int itemsPerPage)
     */
    @Test
    void testGetAllEntityPageData() throws Exception {
        int itemsPerPage = 10;
        int pageNumber = 1;
        when(contractService.getPagesCount(anyInt())).thenReturn(1);

        List<ContractDto> contractDtoList = new ArrayList<>();
        when(modelMapper.mapToList(eq(ContractDto.class), any())).thenReturn(contractDtoList);
        assertEquals(dataService.getAllEntityPageData(ContractDto.class, pageNumber, itemsPerPage).getEntityDtoList(),contractDtoList);

        List<UserDto> userDtoList = new ArrayList<>();
        when(modelMapper.mapToList(eq(UserDto.class), any())).thenReturn(userDtoList);
        assertEquals(dataService.getAllEntityPageData(UserDto.class, pageNumber, itemsPerPage).getEntityDtoList(),userDtoList);

        List<TariffDto> tariffDtoList = new ArrayList<>();
        when(modelMapper.mapToList(eq(TariffDto.class), any())).thenReturn(tariffDtoList);
        assertEquals(dataService.getAllEntityPageData(TariffDto.class, pageNumber, itemsPerPage).getEntityDtoList(),tariffDtoList);

        List<TariffOptionDtoShort> tariffOptionDtoShortList = new ArrayList<>();
        when(modelMapper.mapToList(eq(TariffOptionDtoShort.class), any())).thenReturn(tariffOptionDtoShortList);
        assertEquals(dataService.getAllEntityPageData(TariffOptionDtoShort.class, pageNumber, itemsPerPage).getEntityDtoList(),tariffOptionDtoShortList);

    }

    /**
     * Method: findAllActiveTariffs()
     */
    @Test
    void testFindAllActiveTariffs() throws Exception {
        List<TariffDto> tariffDtoList = new ArrayList<>();
        when(modelMapper.mapToList(TariffDto.class,tariffService.findAllActiveTariffs())).thenReturn(tariffDtoList);

        assertEquals(tariffDtoList,dataService.findAllActiveTariffs());
        verify(tariffService,atLeastOnce()).findAllActiveTariffs();
    }

    /**
     * Method: findAllTariffOptions()
     */
    @Test
    void testFindAllTariffOptions() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getUserPageDto(Integer userId)
     */
    @Test
    void testGetUserPageDto() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getUserAddFundsData(Integer userId)
     */
    @Test
    void testGetUserAddFundsDataUserId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getEditUserData(int userId)
     */
    @Test
    void testGetEditUserData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getCustomerPageData(String login)
     */
    @Test
    void testGetCustomerPageData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getUserAddFundsData(String login)
     */
    @Test
    void testGetUserAddFundsDataLogin() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getChangePasswordData(int userIdInt)
     */
    @Test
    void testGetChangePasswordData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getIndexPageData()
     */
    @Test
    void testGetIndexPageData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAdminPanelData(String login)
     */
    @Test
    void testGetAdminPanelData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAdvProfileTariffData(Integer profileId, Integer tariffId)
     */
    @Test
    void testGetAdvProfileTariffData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAdvProfileAddTariffData()
     */
    @Test
    void testGetAdvProfileAddTariffData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTariffPageData(Integer tariffId)
     */
    @Test
    void testGetTariffPageData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getContractPageData(Integer contractId)
     */
    @Test
    void testGetContractPageData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTariffOptionPageData(Integer optionId)
     */
    @Test
    void testGetTariffOptionPageData() throws Exception {
//TODO: Test goes here... 
    }


} 
