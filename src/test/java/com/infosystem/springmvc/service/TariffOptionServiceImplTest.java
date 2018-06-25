package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.TariffOptionDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.*;

import com.infosystem.springmvc.model.enums.TariffOptionRule;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
 * TariffOptionServiceImpl Tester.
 */
@ExtendWith(MockitoExtension.class)
class TariffOptionServiceImplTest {

    private List<TariffOption> tariffOptions = new ArrayList<>();

    @InjectMocks
    private TariffOptionServiceImpl tariffOptionService;

    @Mock
    private TariffOptionDao tariffOptionDao;

    @Mock
    private CustomModelMapper modelMapperWrapper;

    @Mock
    private ContractService contractService;

    @Mock
    private TariffService tariffService;

    @BeforeEach
    void setUp() {
        getTariffOptionsList();
    }

    private void getTariffOptionsList() {
        TariffOption t1 = new TariffOption();
        t1.setId(1);
        t1.setName("Name1");
        TariffOption t2 = new TariffOption();
        t2.setId(2);
        t2.setName("Name2");
        TariffOption t3 = new TariffOption();
        t3.setId(3);
        t3.setName("Name3");
        tariffOptions.addAll(Arrays.asList(t1, t2, t3));
    }

    /**
     * Method: findById(int id)
     */
    @Test
    void testFindById() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        when(tariffOptionDao.findById(1)).thenReturn(tariffOption);
        assertEquals(tariffOptionService.findById(tariffOption.getId()), tariffOption);
        verify(tariffOptionDao).findById(tariffOption.getId());
    }

    /**
     * Method: findByName(String name)
     */
    @Test
    void testFindByName() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        String name = "tariffOptionName";
        tariffOption.setName(name);
        when(tariffOptionDao.findByName(name)).thenReturn(tariffOption);
        tariffOptionService.findByName(tariffOption.getName());
        verify(tariffOptionDao).findByName(tariffOption.getName());
    }

    /**
     * Method: saveTariffOption(TariffOption tariffOption)
     */
    @Test
    void testSaveTariffOption() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        doNothing().when(tariffOptionDao).save(tariffOption);
        tariffOptionService.saveTariffOption(tariffOption);
        verify(tariffOptionDao, atLeastOnce()).save(tariffOption);
    }

    /**
     * Method: findAllTariffOptions()
     */
    @Test
    void testFindAllTariffOptions() throws Exception {
        when(tariffOptionDao.findAllTariffOptions()).thenReturn(tariffOptions);
        assertEquals(tariffOptionService.findAllTariffOptions(), tariffOptions);
        verify(tariffOptionDao).findAllTariffOptions();
    }

    /**
     * Method: findFirstTariffOptions()
     */
    @Test
    void testFindFirstTariffOptions() throws Exception {
        when(tariffOptionDao.findAllTariffOptions()).thenReturn(tariffOptions);
        assertEquals(tariffOptionService.findFirstTariffOptions(), tariffOptions);
        verify(tariffOptionDao, atLeastOnce()).findAllTariffOptions();
    }

    /**
     * Method: addTariffOption(AddTariffOptionDto addTariffOptionDto)
     */
    @Test
    void testAddTariffOption() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        AddTariffOptionDto addTariffOptionDto = new AddTariffOptionDto();
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);
        when(modelMapperWrapper.mapToTariffOption(any())).thenReturn(tariffOption);
        tariffOptionService.addTariffOption(addTariffOptionDto);
        verify(tariffOptionDao, atLeastOnce()).save(tariffOption);
    }

    /**
     * Method: selectListByIdList(List<Integer> optionIdList)
     */
    @Test
    void testSelectListByIdList() throws Exception {
        List<Integer> idList = new ArrayList<>();
        when(tariffOptionDao.selectListByIdList(idList)).thenReturn(new HashSet<>(tariffOptions));
        assertEquals(tariffOptionService.selectListByIdList(idList), new HashSet<>(tariffOptions));
    }

    /**
     * Method: deleteTariffOptionById(int id)
     */
    @Test
    void testDeleteTariffOptionById() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        when(tariffOptionDao.findById(1)).thenReturn(tariffOption);
        tariffOptionService.setContractService(contractService);
        tariffOptionService.setTariffService(tariffService);

        tariffOptionService.deleteTariffOptionById(tariffOption.getId());

        verify(tariffOptionDao, atLeastOnce()).findById(tariffOption.getId());
        verify(tariffOptionDao, atLeastOnce()).deleteById(tariffOption.getId());
    }

    /**
     * Method: deleteTariffOptionById(int id)
     */
    @Test
    void testStillUsed_1_DeleteTariffOptionById() throws Exception {
        String exceptionMessage = "This option is still used.";
        TariffOption tariffOption = tariffOptions.get(0);
        Contract contract = new Contract();
        contract.setActiveOptions(new HashSet<>(tariffOptions));
        when(tariffOptionDao.findById(1)).thenReturn(tariffOption);
        tariffOptionService.setContractService(contractService);
        tariffOptionService.setTariffService(tariffService);
        when(contractService.findAllContracts()).thenReturn(new ArrayList<>(Collections.singletonList(contract)));

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffOptionService.deleteTariffOptionById(tariffOption.getId());
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: deleteTariffOptionById(int id)
     */
    @Test
    void testStillUsed_2_DeleteTariffOptionById() throws Exception {
        String exceptionMessage = "This option is still used.";
        TariffOption tariffOption = tariffOptions.get(0);
        Tariff tariff = new Tariff();
        tariff.setAvailableOptions(new HashSet<>(tariffOptions));
        when(tariffOptionDao.findById(1)).thenReturn(tariffOption);
        tariffOptionService.setContractService(contractService);
        tariffOptionService.setTariffService(tariffService);
        when(tariffService.findAllTariffs()).thenReturn(new ArrayList<>(Collections.singletonList(tariff)));

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffOptionService.deleteTariffOptionById(tariffOption.getId());
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_RELATED_ExcludeListed_AddRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        HashSet<TariffOption> excludingOptions = new HashSet<>(Collections.singletonList(tariffOption1));
        tariffOption.setExcludingTariffOptions(excludingOptions);

        StringBuilder sb = new StringBuilder();
        tariffOption.getExcludingTariffOptions().forEach(anotherOption -> sb.append(anotherOption.getName()).append(";"));
        String exceptionMessage = "Options " + sb.toString() + " exclude " + tariffOption.getName() + ".";

        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setRule(TariffOptionRule.RELATED.getRule());

        when(tariffOptionDao.findById(anyInt())).thenReturn(tariffOption);
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);
        when(modelMapperWrapper.mapToTariffOptionSet(any()))
                .thenReturn(new HashSet<>(Collections.singletonList(tariffOption1)));

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_RELATED_ExcludingEachOther_AddRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        TariffOption tariffOption2 = tariffOptions.get(2);
        tariffOption1.setExcludingTariffOptions(new HashSet<>(Collections.singletonList(tariffOption2)));
        tariffOption2.setExcludingTariffOptions(new HashSet<>(Collections.singletonList(tariffOption1)));

        StringBuilder sb = new StringBuilder();
        tariffOption2.getExcludingTariffOptions().forEach(anotherOption -> sb.append(anotherOption.getName()).append(";"));
        String exceptionMessage = "Options " + sb.toString() + " exclude " + tariffOption2.getName() + ".";

        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setRule(TariffOptionRule.RELATED.getRule());

        when(tariffOptionDao.findById(anyInt())).thenReturn(tariffOption);
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);
        when(modelMapperWrapper.mapToTariffOptionSet(any()))
                .thenReturn(new HashSet<>(Arrays.asList(tariffOption1, tariffOption2)));

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_RELATED_ExcludingOneOfRelated_AddRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        TariffOption tariffOption2 = tariffOptions.get(2);
        tariffOption1.setExcludingTariffOptions(new HashSet<>(Collections.singletonList(tariffOption2)));
        tariffOption2.setExcludingTariffOptions(new HashSet<>(Collections.singletonList(tariffOption1)));
        tariffOption.setRelatedTariffOptions(new HashSet<>(Collections.singletonList(tariffOption1)));

        StringBuilder sb = new StringBuilder();
        tariffOption2.getExcludingTariffOptions().forEach(anotherOption -> sb.append(anotherOption.getName()).append(";"));
        String exceptionMessage = "Options " + sb.toString() + " exclude " + tariffOption2.getName() + ".";

        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setRule(TariffOptionRule.RELATED.getRule());

        when(tariffOptionDao.findById(anyInt())).thenReturn(tariffOption);
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);
        when(modelMapperWrapper.mapToTariffOptionSet(any()))
                .thenReturn(new HashSet<>(Collections.singletonList(tariffOption2)));

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_EXCLUDING_ExcludingOneOfRelated_AddRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        TariffOption tariffOption2 = tariffOptions.get(2);
        tariffOption.setRelatedTariffOptions(new HashSet<>(Collections.singletonList(tariffOption1)));
        tariffOption1.setRelatedTariffOptions(new HashSet<>(Collections.singletonList(tariffOption2)));
        HashSet<TariffOption> excludingOptions = new HashSet<>(Collections.singletonList(tariffOption2));

        StringBuilder sb = new StringBuilder();
        excludingOptions.forEach(option -> sb.append(option.getName()).append(";"));
        String exceptionMessage = "Cant exclude options " + sb.toString() + ".";

        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setRule(TariffOptionRule.EXCLUDING.getRule());

        when(tariffOptionDao.findById(anyInt())).thenReturn(tariffOption);
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);
        when(modelMapperWrapper.mapToTariffOptionSet(any()))
                .thenReturn(excludingOptions);

        LogicException exception = assertThrows(LogicException.class, () -> {
            tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: addRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_AddRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setTariffOptionId(tariffOption.getId());
        tariffOptionRulesDto.setRule(TariffOptionRule.RELATED.getRule());
        when(tariffOptionDao.findById(1)).thenReturn(tariffOption);
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);

        tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);

        verify(tariffOptionDao, atLeastOnce()).findById(tariffOption.getId());

        tariffOptionRulesDto.setRule(TariffOptionRule.EXCLUDING.getRule());

        tariffOptionService.addRuleTariffOptions(tariffOptionRulesDto);

        verify(tariffOptionDao, atLeastOnce()).findById(tariffOption.getId());
    }

    /**
     * Method: delRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_RELATED_DelRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        TariffOption tariffOption2 = tariffOptions.get(2);
        HashSet<TariffOption> tariffOptions = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2));
        tariffOption.setRelatedTariffOptions(tariffOptions);

        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setRule(TariffOptionRule.RELATED.getRule());
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);

        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(tariffOptions);
        when(tariffOptionDao.findById(anyInt())).thenReturn(tariffOption);
        tariffOptionService.delRuleTariffOptions(tariffOptionRulesDto);

        verify(tariffOptionDao, atLeastOnce()).findById(anyInt());
        assertTrue(tariffOption.getRelatedTariffOptions().isEmpty());
    }

    /**
     * Method: delRuleTariffOptions(TariffOptionRulesDto tariffOptionRulesDto)
     */
    @Test
    void test_EXCLUDING_DelRuleTariffOptions() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        TariffOption tariffOption2 = tariffOptions.get(2);
        HashSet<TariffOption> tariffOptions = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2));
        tariffOption.setExcludingTariffOptions(tariffOptions);

        TariffOptionRulesDto tariffOptionRulesDto = new TariffOptionRulesDto();
        tariffOptionRulesDto.setRule(TariffOptionRule.EXCLUDING.getRule());
        tariffOptionService.setModelMapperWrapper(modelMapperWrapper);

        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(tariffOptions);
        when(tariffOptionDao.findById(anyInt())).thenReturn(tariffOption);
        tariffOptionService.delRuleTariffOptions(tariffOptionRulesDto);

        verify(tariffOptionDao, atLeastOnce()).findById(anyInt());
        assertTrue(tariffOption.getExcludingTariffOptions().isEmpty());
    }

    /**
     * Method: findListOfTariffOptions(int startIndex, int count)
     */
    @Test
    void testFindListOfTariffOptions() throws Exception {
        when(tariffOptionDao.findListOfTariffOptions(anyInt(), anyInt())).thenReturn(tariffOptions);
        assertEquals(tariffOptions, tariffOptionService.findListOfTariffOptions(anyInt(), anyInt()));
        verify(tariffOptionDao, atLeastOnce()).findListOfTariffOptions(anyInt(), anyInt());
    }

    /**
     * Method: getPagesCount(int itemsPerPage)
     */
    @ParameterizedTest(name = "{index} => itemsCount={0}, pagesCount={1}")
    @MethodSource("dataProvider")
    void testGetPagesCount(int itemsCount, int pagesCount) throws Exception {
        when(tariffOptionDao.tariffOptionCount()).thenReturn(itemsCount);
        assertEquals(tariffOptionService.getPagesCount(10), pagesCount);
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
     * Method: isTariffOptionUnique(String tariffOptionName)
     */
    @Test
    void test_true_IsTariffOptionUnique() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        when(tariffOptionDao.findByName(anyString())).thenReturn(null);
        assertTrue(tariffOptionService.isTariffOptionUnique(tariffOption.getName()));
        verify(tariffOptionDao, atLeastOnce()).findByName(tariffOption.getName());
    }

    /**
     * Method: isTariffOptionUnique(String tariffOptionName)
     */
    @Test
    void test_false_IsTariffOptionUnique() throws Exception {
        TariffOption tariffOption = tariffOptions.get(0);
        when(tariffOptionDao.findByName(anyString())).thenReturn(tariffOption);
        tariffOptionService.isTariffOptionUnique(tariffOption.getName());
        assertFalse(tariffOptionService.isTariffOptionUnique(tariffOption.getName()));
        verify(tariffOptionDao, atLeastOnce()).findByName(tariffOption.getName());
    }


    /**
     * Method: isWrongRule(TariffOption tariffOption, Set<TariffOption> tariffOptions)
     */
    @Test
    void testIsWrongRule() throws Exception {
        String exceptionMessage = "Oh, cmon, hacker)00)0";
        TariffOption tariffOption = tariffOptions.get(0);
        TariffOption tariffOption1 = tariffOptions.get(1);
        TariffOption tariffOption2 = tariffOptions.get(2);
        Set<TariffOption> tariffOptionSet = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2, tariffOption));

        LogicException exception = assertThrows(LogicException.class, () -> {
            try {
                Method method = TariffOptionServiceImpl.class
                        .getDeclaredMethod("isWrongRule", com.infosystem.springmvc.model.entity.TariffOption.class, java.util.Set.class);
                method.setAccessible(true);
                method.invoke(tariffOptionService, tariffOption, tariffOptionSet);
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw new LogicException(e.getCause().getMessage());
            }
        });
        assertEquals(exceptionMessage, exception.getMessage());
//        Method method = targetClass.getDeclaredMethod(methodName, argClasses);
//        method.setAccessible(true);
//        return method.invoke(targetObject, argObjects);
//        Field field = targetClass.getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(object, value);
    }

}
