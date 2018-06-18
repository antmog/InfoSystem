package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffDao;
import com.infosystem.springmvc.dao.TariffDaoImpl;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.jms.JmsDataMapper;
import com.infosystem.springmvc.model.entity.*;


import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TariffServiceImpl Tester.
 */
@ExtendWith(MockitoExtension.class)
class TariffServiceImplTest {

    private List<Tariff> tariffs = new ArrayList<>();

    @Mock
    private TariffDao tariffDao;

    @Mock
    private CustomModelMapper modelMapperWrapper;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Mock
    private ContractService contractService;

    @Mock
    private JmsDataMapper jmsDataMapper;

    @Mock
    private AdvProfileService advProfileService;

    @Mock
    private OptionsRulesChecker optionsRulesChecker;


    @BeforeEach
    void setUp() {
        getTariffList();
    }

    private void getTariffList() {
        Tariff t1 = new Tariff();
        t1.setId(1);
        Tariff t2 = new Tariff();
        t2.setId(2);
        tariffs.addAll(Arrays.asList(t1, t2));
    }

    /**
     * Method: findById(int id)
     */
    @Test
    void testFindById() throws DatabaseException {
        Tariff tariff = tariffs.get(0);
        when(tariffDao.findById(1)).thenReturn(tariff);
        assertEquals(tariffService.findById(tariff.getId()), tariff);
        verify(tariffDao).findById(tariff.getId());
    }

    /**
     * Method: findByName(String name)
     */
    @Test
    void testFindByName(){
        Tariff tariff = tariffs.get(0);
        String name = "tariffName";
        tariff.setName(name);
        when(tariffDao.findByName(name)).thenReturn(tariff);
        tariffService.findByName(tariff.getName());
        verify(tariffDao).findByName(tariff.getName());
    }

    /**
     * Method: findAllTariffs()
     */
    @Test
    void testFindAllTariffs()  {
        when(tariffDao.findAllTariffs()).thenReturn(tariffs);
        assertEquals(tariffService.findAllTariffs(), tariffs);
        verify(tariffDao).findAllTariffs();
    }

    /**
     * Method: findAllActiveTariffs()
     */
    @Test
    void testFindAllActiveTariffs()  {
        when(tariffDao.findAllActiveTariffs()).thenReturn(tariffs);
        assertEquals(tariffService.findAllActiveTariffs(), tariffs);
        verify(tariffDao).findAllActiveTariffs();
    }

    /**
     * Method: findFirstTariffs()
     */
    @Test
    void testFindFirstTariffs()  {
        when(tariffDao.findAllTariffs()).thenReturn(tariffs);
        assertEquals(tariffService.findAllTariffs(), tariffs);
        verify(tariffDao, atLeastOnce()).findAllTariffs();
    }

    /**
     * Method: addTariff(AddTariffDto addTariffDto)
     */
    @Test
    void testAddTariff() throws LogicException {
        AddTariffDto addTariffDto = new AddTariffDto();
        TariffDto tariffDto = new TariffDto();
        tariffDto.setName("tariffName");
        addTariffDto.setTariffDto(tariffDto);
        Tariff tariff = tariffs.get(0);
        tariffService.setModelMapperWrapper(modelMapperWrapper);
        tariffService.setOptionsRulesChecker(optionsRulesChecker);
        when(modelMapperWrapper.mapToTariff(addTariffDto)).thenReturn(tariff);
        when(tariffService.findByName(addTariffDto.getTariffDto().getName())).thenReturn(null);

        tariffService.addTariff(addTariffDto);

        verify(tariffDao, atLeastOnce()).save(tariff);
    }

    /**
     * Method: deleteTariffById(int id)
     */
    @Test
    void testDeleteTariffById() throws DatabaseException, LogicException {
        Tariff tariff = tariffs.get(0);
        tariffService.setContractService(contractService);
        tariffService.setAdvProfileService(advProfileService);
        when(contractService.findAllContracts()).thenReturn(new ArrayList<>());
        when(advProfileService.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(tariffDao).deleteById(tariff.getId());
        when(tariffDao.findById(tariff.getId())).thenReturn(tariff);
        tariffService.deleteTariffById(tariff.getId());
        verify(tariffDao).deleteById(tariff.getId());
        verify(tariffDao, atLeastOnce()).findById(tariff.getId());
    }

    /**
     * Method: deleteTariffById(int id)
     */
    @Test
    void testAlrdyUsedDeleteTariffById() throws DatabaseException {
        String exceptionMessage = "Tariff is still used.";
        Tariff tariff = tariffs.get(0);
        Contract contract = new Contract();
        contract.setTariff(tariff);
        tariffService.setContractService(contractService);
        when(tariffDao.findById(tariff.getId())).thenReturn(tariff);
        when(contractService.findAllContracts()).thenReturn(new ArrayList<>(Collections.singletonList(contract)));
        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffService.deleteTariffById(tariff.getId());
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: deleteTariffById(int id)
     */
    @Test
    void testAlrdyUsedAdvDeleteTariffById() throws DatabaseException {
        String exceptionMessage = "Tariff is still used in advertisment.";
        Tariff tariff = tariffs.get(0);
        AdvProfile advProfile = new AdvProfile();
        AdvProfileTariffs advProfileTariffs = new AdvProfileTariffs(advProfile, tariff);
        advProfile.setAdvProfileTariffsList(new ArrayList<>(Collections.singletonList(advProfileTariffs)));

        tariffService.setContractService(contractService);
        tariffService.setAdvProfileService(advProfileService);
        when(tariffDao.findById(tariff.getId())).thenReturn(tariff);
        when(advProfileService.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(advProfile)));

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffService.deleteTariffById(tariff.getId());
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: addOptions(EditTariffDto editTariffDto)
     */
    @Test
    void testAddOptions() throws DatabaseException, LogicException {
        Tariff tariff = tariffs.get(0);
        TariffOption tariffOption1 = new TariffOption();
        tariffOption1.setId(1);
        TariffOption tariffOption2 = new TariffOption();
        tariffOption2.setId(2);
        tariff.setAvailableOptions(new HashSet<>(Collections.singletonList(tariffOption1)));
        EditTariffDto editTariffDto = new EditTariffDto();
        editTariffDto.setTariffId(tariff.getId());
        HashSet<TariffOption> tariffOptions = new HashSet<>(Collections.singletonList(tariffOption2));
        tariffService.setModelMapperWrapper(modelMapperWrapper);
        tariffService.setOptionsRulesChecker(optionsRulesChecker);
        tariffService.setJmsDataMapper(jmsDataMapper);
        when(tariffDao.findById(editTariffDto.getTariffId())).thenReturn(tariff);
        when(modelMapperWrapper.mapToTariffOptionSet(editTariffDto.getTariffOptionDtoList())).thenReturn(tariffOptions);

        tariffService.addOptions(editTariffDto);

        verify(tariffDao,atLeastOnce()).findById(tariff.getId());
        assertEquals(tariff.getAvailableOptions(),new HashSet<>(Arrays.asList(tariffOption1,tariffOption2)));
    }

    /**
     * Method: delOptions(EditTariffDto editTariffDto)
     */
    @Test
    void testDelOptions() throws DatabaseException, LogicException {
        Tariff tariff = tariffs.get(0);
        TariffOption tariffOption1 = new TariffOption();
        tariffOption1.setId(1);
        tariff.setAvailableOptions(new HashSet<>(Collections.singletonList(tariffOption1)));
        EditTariffDto editTariffDto = new EditTariffDto();
        editTariffDto.setTariffId(tariff.getId());
        HashSet<TariffOption> tariffOptions = new HashSet<>(Collections.singletonList(tariffOption1));
        tariffService.setModelMapperWrapper(modelMapperWrapper);
        tariffService.setOptionsRulesChecker(optionsRulesChecker);
        tariffService.setJmsDataMapper(jmsDataMapper);
        when(tariffDao.findById(editTariffDto.getTariffId())).thenReturn(tariff);
        when(modelMapperWrapper.mapToTariffOptionSet(editTariffDto.getTariffOptionDtoList())).thenReturn(tariffOptions);

        tariffService.delOptions(editTariffDto);

        verify(tariffDao,atLeastOnce()).findById(tariff.getId());
        assertTrue(tariff.getAvailableOptions().isEmpty());
    }

    /**
     * Method: getAvailableOptionsForTariff(int tariffId)
     */
    @Test
    void testGetAvailableOptionsForTariff() throws DatabaseException {
        Tariff tariff = tariffs.get(0);
        TariffOptionDtoShort tariffOptionDtoShort = new TariffOptionDtoShort();
        tariffOptionDtoShort.setId(tariff.getId());
        TariffOption tariffOption1 = new TariffOption();
        tariffOption1.setId(1);
        tariff.setAvailableOptions(new HashSet<>(Collections.singletonList(tariffOption1)));
        tariffService.setModelMapperWrapper(modelMapperWrapper);
        when(tariffDao.findById(tariff.getId())).thenReturn(tariff);
        when(modelMapperWrapper.mapToTariffOptionDtoShortSet(tariff.getAvailableOptions()))
                .thenReturn(new HashSet<>(Collections.singletonList(tariffOptionDtoShort)));

        assertEquals(tariffService.getAvailableOptionsForTariff(tariff.getId()).size(),tariff.getAvailableOptions().size());
        verify(tariffDao,atLeastOnce()).findById(tariff.getId());
    }

    /**
     * Method: findListOfTariffs(int startIndex, int count)
     */
    @Test
    void testFindListOfTariffs() {
        when(tariffDao.findListOfTariffs(anyInt(),anyInt())).thenReturn(tariffs);
        assertEquals(tariffs,tariffService.findListOfTariffs(anyInt(),anyInt()));
    }

    /**
     * Method: getPagesCount(int itemsPerPage)
     */
    @ParameterizedTest(name = "{index} => itemsCount={0}, pagesCount={1}")
    @MethodSource("dataProvider")
    void testGetPagesCount(int itemsCount, int pagesCount) throws Exception {
        when(tariffDao.tariffCount()).thenReturn(itemsCount);
        assertEquals(tariffService.getPagesCount(10), pagesCount);
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(1, 1),
                Arguments.of(9, 1),
                Arguments.of(10, 1),
                Arguments.of(11, 2),
                Arguments.of(19, 2),
                Arguments.of(20, 2),
                Arguments.of(21, 3),
                Arguments.of(0, 1)
        );
    }

    /**
     * Method: setStatus(SetNewStatusDto setNewStatusDto)
     */
    @Test
    void testSetStatus() throws Exception {
        Tariff tariff = tariffs.get(0);
        SetNewStatusDto setNewStatusDto = new SetNewStatusDto();
        setNewStatusDto.setEntityId(tariff.getId());
        tariffService.setAdvProfileService(advProfileService);
        tariffService.setModelMapperWrapper(modelMapperWrapper);
        when(modelMapperWrapper.mapToStatus(any())).thenReturn(Status.INACTIVE);
        when(tariffDao.findById(setNewStatusDto.getEntityId())).thenReturn(tariff);

        tariffService.setStatus(setNewStatusDto);
        assertEquals(tariff.getStatus(),Status.INACTIVE);
    }

    /**
     * Method: setStatus(SetNewStatusDto setNewStatusDto)
     */
    @Test
    void testAlrdyUsedSetStatus() throws Exception {
        String exceptionMessage = "Tariff is still used in advertisment.";
        Tariff tariff = tariffs.get(0);
        SetNewStatusDto setNewStatusDto = new SetNewStatusDto();
        setNewStatusDto.setEntityId(tariff.getId());
        AdvProfile advProfile = new AdvProfile();
        AdvProfileTariffs advProfileTariffs = new AdvProfileTariffs(advProfile,tariff);
        advProfile.setAdvProfileTariffsList(new ArrayList<>(Collections.singletonList(advProfileTariffs)));
        when(advProfileService.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(advProfile)));
        tariffService.setAdvProfileService(advProfileService);
        tariffService.setModelMapperWrapper(modelMapperWrapper);
        when(tariffDao.findById(setNewStatusDto.getEntityId())).thenReturn(tariff);

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffService.setStatus(setNewStatusDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }
} 
