package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.ContractDao;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.*;


import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.security.CustomUserDetailsService;
import com.infosystem.springmvc.service.security.SecurityService;
import com.infosystem.springmvc.sessioncart.SessionCart;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
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
 * ContractServiceImpl Tester.
 */
@ExtendWith(MockitoExtension.class)
class ContractServiceImplTest {

    private List<Contract> contracts = new ArrayList<>();

    @Mock
    private CustomModelMapper modelMapperWrapper;

    @Mock
    private OptionsRulesChecker optionsRulesChecker;

    @Mock
    private UserService userService;

    @Mock
    private SecurityService securityService;

    @Mock
    private SessionCart sessionCart;

    @Mock
    private TariffService tariffService;

    @Mock
    private ContractDao contractDao;

    @InjectMocks
    private ContractServiceImpl contractService;

    @BeforeEach
    void setUp() {
        getTariffOptionsList();
        contractService.setUserService(userService);
        contractService.setSecurityService(securityService);
        contractService.setTariffService(tariffService);
        contractService.setOptionsRulesChecker(optionsRulesChecker);
        contractService.setModelMapperWrapper(modelMapperWrapper);
        contractService.setSessionCart(sessionCart);
    }

    private void getTariffOptionsList() {
        Contract c1 = new Contract();
        c1.setId(1);
        Contract c2 = new Contract();
        c2.setId(2);
        Contract c3 = new Contract();
        c3.setId(3);
        contracts.addAll(Arrays.asList(c1, c2, c3));
    }

    /**
     * Method: findById(int id)
     */
    @Test
    void testFindById() throws Exception {
        Contract contract = contracts.get(0);
        when(contractDao.findById(1)).thenReturn(contract);
        assertEquals(contractService.findById(contract.getId()), contract);
        verify(contractDao).findById(contract.getId());
    }

    /**
     * Method: saveContract(Contract contract)
     */
    @Test
    void testSaveContract() throws Exception {
        Contract contract = contracts.get(0);
        doNothing().when(contractDao).save(contract);
        contractService.saveContract(contract);
        verify(contractDao, atLeastOnce()).save(contract);
    }

    /**
     * Method: findAllContracts()
     */
    @Test
    void testFindAllContracts() throws Exception {
        when(contractDao.findAllContracts()).thenReturn(contracts);
        assertEquals(contractService.findAllContracts(), contracts);
        verify(contractDao).findAllContracts();
    }

    /**
     * Method: deleteContractById(int id)
     */
    @Test
    void testDeleteContractById() throws Exception {
        Contract contract = contracts.get(0);
        doNothing().when(contractDao).deleteById(contract.getId());
        when(contractDao.findById(contract.getId())).thenReturn(contract);
        contractService.deleteContractById(contract.getId());
        verify(contractDao).deleteById(contract.getId());
        verify(contractDao, atLeastOnce()).findById(contract.getId());
    }

    /**
     * Method: setStatus(SetNewStatusDto setNewStatusDto)
     */
    @Test
    void testSetStatus() throws Exception {
        Contract contract = contracts.get(0);
        SetNewStatusDto setNewStatusDto = new SetNewStatusDto();
        setNewStatusDto.setEntityId(contract.getId());
        when(modelMapperWrapper.mapToStatus(any())).thenReturn(Status.INACTIVE);
        when(contractDao.findById(setNewStatusDto.getEntityId())).thenReturn(contract);
        when(securityService.isPrincipalAdmin()).thenReturn(true);

        contractService.setStatus(setNewStatusDto);
        assertEquals(contract.getStatus(), Status.INACTIVE);
    }

    /**
     * Method: setStatus(SetNewStatusDto setNewStatusDto)
     */
    @Test
    void test_actionPermitted_SetStatus() throws Exception {
        String exceptionMessage = "You are not admin to do this.";
        Contract contract = contracts.get(0);
        User user = new User();
        user.setId(1);
        contract.setUser(user);
        SetNewStatusDto setNewStatusDto = new SetNewStatusDto();
        setNewStatusDto.setEntityId(contract.getId());
        when(contractDao.findById(setNewStatusDto.getEntityId())).thenReturn(contract);
        when(securityService.isPrincipalAdmin()).thenReturn(false);
        when(securityService.getPrincipal()).thenReturn("login");
        when(userService.findByLogin(anyString())).thenReturn(new User());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            contractService.setStatus(setNewStatusDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: findByPhoneNumber(String phoneNumber)
     */
    @Test
    void testFindByPhoneNumber() throws Exception {
        String phoneNumber = "225";
        Contract contract = contracts.get(0);
        contract.setPhoneNumber(phoneNumber);
        when(contractDao.findByPhoneNumber(phoneNumber)).thenReturn(contract);
        assertEquals(contract, contractService.findByPhoneNumber(phoneNumber));
        verify(contractDao, atLeastOnce()).findByPhoneNumber(phoneNumber);
    }

    /**
     * Method: addOptions(EditContractDto editContractDto)
     */
    @Test
    void testAddOptions() throws Exception {
        Double tariffPrice = 1.0;
        Double initialUserBalance = 2.0;
        Double optionPrice = 1.0;
        Double optionCostOfAdd = 1.0;
        Contract contract = contracts.get(0);
        User user = new User();
        user.setBalance(initialUserBalance);
        Tariff tariff = new Tariff();
        tariff.setPrice(tariffPrice);
        contract.setUser(user);
        contract.setTariff(tariff);
        EditContractDto editContractDto = new EditContractDto();
        TariffOption tariffOption1 = new TariffOption();
        tariffOption1.setId(1);
        tariffOption1.setCostOfAdd(optionCostOfAdd);
        tariffOption1.setPrice(optionPrice);
        TariffOption tariffOption2 = new TariffOption();
        tariffOption2.setId(2);
        tariffOption2.setCostOfAdd(optionCostOfAdd);
        tariffOption2.setPrice(optionPrice);
        Set<TariffOption> tariffOptions = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2));

        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(tariffOptions);

        contractService.addOptions(editContractDto);

        assertEquals(contract.getActiveOptions(), tariffOptions);
        assertTrue(user.getBalance() - (Double) (initialUserBalance - 2 * optionCostOfAdd) <= 0.0000001);
        assertTrue(contract.getPrice() - (Double) (tariffPrice + 2 * optionPrice) <= 0.0000001);
    }

    /**
     * Method: addOptions(EditContractDto editContractDto)
     */
    @Test
    void test_NotEnoughFunds_AddOptions() throws Exception {
        String exceptionMessage = "Not enough funds.";
        Double initialUserBalance = 1.0;
        Double optionCostOfAdd = 1.0;
        Contract contract = contracts.get(0);
        User user = new User();
        user.setBalance(initialUserBalance);
        Tariff tariff = new Tariff();
        contract.setUser(user);
        contract.setTariff(tariff);
        EditContractDto editContractDto = new EditContractDto();
        TariffOption tariffOption1 = new TariffOption();
        tariffOption1.setId(1);
        tariffOption1.setCostOfAdd(optionCostOfAdd);
        TariffOption tariffOption2 = new TariffOption();
        tariffOption2.setId(2);
        tariffOption2.setCostOfAdd(optionCostOfAdd);
        Set<TariffOption> tariffOptions = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2));

        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(tariffOptions);
        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.addOptions(editContractDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: customerAddOptions(AddOptionsDto addOptionsDto)
     */
    @Test
    void testCustomerAddOptions() throws Exception {
        Double initialUserBalance = 2.0;
        Double optionCostOfAdd = 1.0;
        Map<Integer, Set<TariffOptionDto>> options = new HashMap<>();
        TariffOptionDto tariffOptionDto1 = new TariffOptionDto();
        TariffOptionDto tariffOptionDto2 = new TariffOptionDto();
        tariffOptionDto1.setId(1);
        tariffOptionDto2.setId(2);
        Contract contract1 = contracts.get(0);
        Contract contract2 = contracts.get(1);
        contract1.setStatus(Status.ACTIVE);
        contract2.setStatus(Status.ACTIVE);
        Set<TariffOptionDto> options1 = new HashSet<>(Collections.singletonList(tariffOptionDto1));
        Set<TariffOptionDto> options2 = new HashSet<>(Collections.singletonList(tariffOptionDto2));
        TariffOption tariffOption1 = new TariffOption();
        TariffOption tariffOption2 = new TariffOption();
        tariffOption1.setId(tariffOptionDto1.getId());
        tariffOption2.setId(tariffOptionDto2.getId());
        tariffOption1.setCostOfAdd(optionCostOfAdd);
        tariffOption2.setCostOfAdd(optionCostOfAdd);
        Set<TariffOption> options1mapped = new HashSet<>(Collections.singletonList(tariffOption1));
        Set<TariffOption> options2mapped = new HashSet<>(Collections.singletonList(tariffOption2));
        options.put(contract1.getId(), options1);
        options.put(contract2.getId(), options2);
        AddOptionsDto addOptionsDto = new AddOptionsDto();
        addOptionsDto.setUserId(1);
        User user = new User();
        user.setBalance(initialUserBalance);
        user.setId(addOptionsDto.getUserId());
        when(modelMapperWrapper.mapToSet(eq(TariffOption.class), any())).thenReturn(options1mapped).thenReturn(options2mapped);
        when(userService.findById(addOptionsDto.getUserId())).thenReturn(user);
        when(contractDao.findById(anyInt())).thenReturn(contracts.get(0), contracts.get(1));
        when(sessionCart.getOptions()).thenReturn(options);
        when(securityService.getPrincipal()).thenReturn("login");
        when(userService.findByLogin(anyString())).thenReturn(user);

        contractService.customerAddOptions(addOptionsDto);

        verify(contractDao, atLeastOnce()).findById(anyInt());
        assertTrue(user.getBalance() - (Double) (initialUserBalance - 2 * optionCostOfAdd) <= 0.0000001);
    }

    /**
     * Method: customerAddOptions(AddOptionsDto addOptionsDto)
     */
    @Test
    void test_CartIsEmpty_CustomerAddOptions() throws Exception {
        AddOptionsDto addOptionsDto = new AddOptionsDto();
        addOptionsDto.setUserId(1);
        User user = new User();
        user.setId(addOptionsDto.getUserId());
        when(userService.findById(addOptionsDto.getUserId())).thenReturn(user);
        when(sessionCart.getOptions()).thenReturn(new HashMap<>());
        when(securityService.getPrincipal()).thenReturn("login");
        when(userService.findByLogin(anyString())).thenReturn(user);

        String exceptionMessage = "Cart is empty.";
        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.customerAddOptions(addOptionsDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: customerAddOptions(AddOptionsDto addOptionsDto)
     */
    @Test
    void test_ContractNotActive_CustomerAddOptions() throws Exception {
        Map<Integer, Set<TariffOptionDto>> options = new HashMap<>();
        Contract contract1 = contracts.get(0);
        contract1.setStatus(Status.INACTIVE);
        options.put(contract1.getId(), new HashSet<>());
        AddOptionsDto addOptionsDto = new AddOptionsDto();
        addOptionsDto.setUserId(1);
        User user = new User();
        user.setId(addOptionsDto.getUserId());
        when(userService.findById(addOptionsDto.getUserId())).thenReturn(user);
        when(userService.findByLogin(anyString())).thenReturn(user);
        when(contractDao.findById(anyInt())).thenReturn(contracts.get(0));
        when(sessionCart.getOptions()).thenReturn(options);
        when(securityService.getPrincipal()).thenReturn("login");

        String exceptionMessage = "Contract " + contract1.getId() + " is NOT active, sorry.";
        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.customerAddOptions(addOptionsDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: customerAddOptions(AddOptionsDto addOptionsDto)
     */
    @Test
    void test_NotEnoughFunds_CustomerAddOptions() throws Exception {
        Double initialUserBalance = 1.0;
        Double optionCostOfAdd = 1.0;
        Map<Integer, Set<TariffOptionDto>> options = new HashMap<>();
        TariffOptionDto tariffOptionDto1 = new TariffOptionDto();
        TariffOptionDto tariffOptionDto2 = new TariffOptionDto();
        tariffOptionDto1.setId(1);
        tariffOptionDto2.setId(2);
        Contract contract1 = contracts.get(0);
        Contract contract2 = contracts.get(1);
        contract1.setStatus(Status.ACTIVE);
        contract2.setStatus(Status.ACTIVE);
        Set<TariffOptionDto> options1 = new HashSet<>(Collections.singletonList(tariffOptionDto1));
        Set<TariffOptionDto> options2 = new HashSet<>(Collections.singletonList(tariffOptionDto2));
        TariffOption tariffOption1 = new TariffOption();
        TariffOption tariffOption2 = new TariffOption();
        tariffOption1.setId(tariffOptionDto1.getId());
        tariffOption2.setId(tariffOptionDto2.getId());
        tariffOption1.setCostOfAdd(optionCostOfAdd);
        tariffOption2.setCostOfAdd(optionCostOfAdd);
        Set<TariffOption> options1mapped = new HashSet<>(Collections.singletonList(tariffOption1));
        Set<TariffOption> options2mapped = new HashSet<>(Collections.singletonList(tariffOption2));
        options.put(contract1.getId(), options1);
        options.put(contract2.getId(), options2);
        AddOptionsDto addOptionsDto = new AddOptionsDto();
        addOptionsDto.setUserId(1);
        User user = new User();
        user.setBalance(initialUserBalance);
        user.setId(addOptionsDto.getUserId());
        when(modelMapperWrapper.mapToSet(eq(TariffOption.class), any())).thenReturn(options1mapped).thenReturn(options2mapped);
        when(userService.findById(addOptionsDto.getUserId())).thenReturn(user);
        when(contractDao.findById(anyInt())).thenReturn(contracts.get(0), contracts.get(1));
        when(sessionCart.getOptions()).thenReturn(options);
        when(securityService.getPrincipal()).thenReturn("login");
        when(userService.findByLogin(anyString())).thenReturn(user);

        String exceptionMessage = "Not enough funds.";
        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.customerAddOptions(addOptionsDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: delOptions(EditContractDto editContractDto)
     */
    @Test
    void testDelOptions() throws Exception {
        Contract contract = contracts.get(0);
        Tariff tariff = new Tariff();
        tariff.setPrice(1.0);
        contract.setTariff(tariff);
        Set<TariffOption> options = new HashSet<>(Collections.singletonList(new TariffOption()));
        contract.setActiveOptions(options);
        EditContractDto editContractDto = new EditContractDto();
        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(options);

        contractService.delOptions(editContractDto);

        verify(contractDao, atLeastOnce()).findById(anyInt());
        assertTrue(contract.getActiveOptions().isEmpty());
    }


    /**
     * Method: customerDelOptions(EditContractDto editContractDto)
     */
    @Test
    void testCustomerDelOptions() throws Exception {
        String exceptionMessage = "Sorry, contract is not active, refresh page.";
        Contract contract = contracts.get(0);
        User user = new User();
        user.setId(1);
        contract.setUser(user);
        contract.setStatus(Status.INACTIVE);
        EditContractDto editContractDto = new EditContractDto();
        when(securityService.getPrincipal()).thenReturn("login");
        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(userService.findByLogin(anyString())).thenReturn(user);

        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.customerDelOptions(editContractDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: switchTariff(SwitchTariffDto switchTariffDto)
     */
    @Test
    void testSwitchTariff() throws Exception {
        Double price = 1.0;
        Contract contract = contracts.get(0);
        Tariff tariff = new Tariff();
        tariff.setPrice(price);
        SwitchTariffDto switchTariffDto = new SwitchTariffDto();


        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(tariffService.findById(anyInt())).thenReturn(tariff);

        contractService.switchTariff(switchTariffDto);

        verify(contractDao, atLeastOnce()).findById(anyInt());
        verify(tariffService, atLeastOnce()).findById(anyInt());
        assertEquals(contract.getTariff(), tariff);
        assertEquals(contract.getPrice(), price);
    }

    /**
     * Method: switchTariff(SwitchTariffDto switchTariffDto)
     */
    @Test
    void test_CantSwithch_SwitchTariff() throws Exception {
        Contract contract = contracts.get(0);
        TariffOption tariffOption = new TariffOption();
        tariffOption.setName("Option1");
        Set<TariffOption> options = new HashSet<>(Collections.singletonList(tariffOption));
        contract.setActiveOptions(options);
        Tariff tariff = new Tariff();
        SwitchTariffDto switchTariffDto = new SwitchTariffDto();

        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(tariffService.findById(anyInt())).thenReturn(tariff);

        StringBuilder sb = new StringBuilder("New tariff doesn't include all current options. To switch remove options: ");
        for (TariffOption option : options) {
            sb.append(option.getName()).append("\n");
        }
        sb.append(".");
        String exceptionMessage = sb.toString();
        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.switchTariff(switchTariffDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());

    }

    /**
     * Method: customerSwitchTariff(SwitchTariffDto switchTariffDto)
     */
    @Test
    void testCustomerSwitchTariff() throws Exception {
        String exceptionMessage = "Sorry, contract is not active, refresh page.";
        Contract contract = contracts.get(0);
        contract.setStatus(Status.INACTIVE);
        User user = new User();
        user.setId(1);
        contract.setUser(user);
        SwitchTariffDto switchTariffDto = new SwitchTariffDto();
        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(userService.findByLogin(anyString())).thenReturn(user);
        when(securityService.getPrincipal()).thenReturn("login");

        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.customerSwitchTariff(switchTariffDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: customerSwitchTariff(SwitchTariffDto switchTariffDto)
     */
    @Test
    void test_IllegalAction_CustomerSwitchTariff() throws Exception {
        String exceptionMessage = "One more hack and you are going to be arrested.";
        Contract contract = contracts.get(0);
        contract.setStatus(Status.INACTIVE);
        User user = new User();
        user.setId(1);
        contract.setUser(user);
        SwitchTariffDto switchTariffDto = new SwitchTariffDto();
        when(contractDao.findById(anyInt())).thenReturn(contract);
        when(userService.findByLogin(anyString())).thenReturn(new User());
        when(securityService.getPrincipal()).thenReturn("login");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            contractService.customerSwitchTariff(switchTariffDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: newContract(AddContractDto addContractDto)
     */
    @Test
    void testNewContract() throws Exception {
        Double initialBalance = 2.0;
        Double tariffPrice = 1.0;
        Double optionPrice = 1.0;
        Double costOfAdd = 1.0;
        AddContractDto addContractDto = new AddContractDto();
        Tariff tariff = new Tariff();
        tariff.setPrice(tariffPrice);
        User user = new User();
        user.setBalance(initialBalance);
        TariffOption tariffOption1 = new TariffOption();
        TariffOption tariffOption2 = new TariffOption();
        tariffOption1.setId(1);
        tariffOption2.setId(2);
        tariffOption1.setCostOfAdd(costOfAdd);
        tariffOption2.setCostOfAdd(costOfAdd);
        tariffOption1.setPrice(optionPrice);
        tariffOption2.setPrice(optionPrice);
        Set<TariffOption> toBeAddedOptionsList = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2));
        addContractDto.setTariffOptionDtoList(new ArrayList<>(Collections.singletonList(new TariffOptionDto())));
        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(toBeAddedOptionsList);
        when(tariffService.findById(anyInt())).thenReturn(tariff);
        when(userService.findById(anyInt())).thenReturn(user);
        when(contractDao.findByPhoneNumber(any())).thenReturn(null);

        contractService.newContract(addContractDto);

        verify(contractDao, atLeastOnce()).save(any());
        assertTrue(initialBalance - (tariffOption1.getCostOfAdd() + tariffOption2.getCostOfAdd()) <= 0.00001);
    }

    /**
     * Method: newContract(AddContractDto addContractDto)
     */
    @Test
    void test_PhoneNumberAlreadyExists_NewContract() throws Exception {
        String exceptionMessage = "Contract with that phone number already exists.";
        AddContractDto addContractDto = new AddContractDto();
        when(contractDao.findByPhoneNumber(any())).thenReturn(contracts.get(0));

        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.newContract(addContractDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: newContract(AddContractDto addContractDto)
     */
    @Test
    void test_NotEnoughFunds_NewContract() throws Exception {
        String exceptionMessage = "Not enough funds.";
        Double initialBalance = 1.0;
        Double tariffPrice = 1.0;
        Double optionPrice = 1.0;
        Double costOfAdd = 1.0;
        AddContractDto addContractDto = new AddContractDto();
        Tariff tariff = new Tariff();
        tariff.setPrice(tariffPrice);
        User user = new User();
        user.setBalance(initialBalance);
        TariffOption tariffOption1 = new TariffOption();
        TariffOption tariffOption2 = new TariffOption();
        tariffOption1.setId(1);
        tariffOption2.setId(2);
        tariffOption1.setCostOfAdd(costOfAdd);
        tariffOption2.setCostOfAdd(costOfAdd);
        tariffOption1.setPrice(optionPrice);
        tariffOption2.setPrice(optionPrice);
        Set<TariffOption> toBeAddedOptionsList = new HashSet<>(Arrays.asList(tariffOption1, tariffOption2));
        addContractDto.setTariffOptionDtoList(new ArrayList<>(Collections.singletonList(new TariffOptionDto())));
        when(modelMapperWrapper.mapToTariffOptionSet(any())).thenReturn(toBeAddedOptionsList);
        when(tariffService.findById(anyInt())).thenReturn(tariff);
        when(userService.findById(anyInt())).thenReturn(user);
        when(contractDao.findByPhoneNumber(any())).thenReturn(null);

        LogicException exception = assertThrows(LogicException.class, () -> {
            contractService.newContract(addContractDto);
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    /**
     * Method: findListOfContracts(int startIndex, int count)
     */
    @Test
    void testFindListOfContracts() throws Exception {
        when(contractDao.findListOfContracts(anyInt(), anyInt())).thenReturn(contracts);
        assertEquals(contracts, contractService.findListOfContracts(anyInt(), anyInt()));
        verify(contractDao, atLeastOnce()).findListOfContracts(anyInt(), anyInt());
    }

    /**
     * Method: getPagesCount(int itemsPerPage)
     */
    @ParameterizedTest(name = "{index} => itemsCount={0}, pagesCount={1}")
    @MethodSource("dataProvider")
    void testGetPagesCount(int itemsCount, int pagesCount) throws Exception {
        when(contractDao.contractCount()).thenReturn(itemsCount);
        assertEquals(contractService.getPagesCount(10), pagesCount);
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
     * Method: doesPhoneNumberExist(String phoneNumber)
     */
    @Test
    void test_false_DoesPhoneNumberExist() throws Exception {
        when(contractDao.findByPhoneNumber(anyString())).thenReturn(null);

        Method method = ContractServiceImpl.class
                .getDeclaredMethod("doesPhoneNumberExist", String.class);
        method.setAccessible(true);
        assertFalse((boolean)method.invoke(contractService, anyString()));
    }

    /**
     * Method: doesPhoneNumberExist(String phoneNumber)
     */
    @Test
    void test_true_DoesPhoneNumberExist() throws Exception {
        when(contractDao.findByPhoneNumber(anyString())).thenReturn(contracts.get(0));

        Method method = ContractServiceImpl.class
                .getDeclaredMethod("doesPhoneNumberExist", String.class);
        method.setAccessible(true);
        assertTrue((boolean)method.invoke(contractService, anyString()));
    }

}
