package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dao.UserDaoImpl;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditAddressDto;
import com.infosystem.springmvc.dto.editUserDto.EditMailDto;
import com.infosystem.springmvc.dto.editUserDto.EditPassportDto;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.util.CustomModelMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private List<User> users = new ArrayList<>();

    @Mock
    private UserDaoImpl userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomModelMapper customModelMapper;

    @Mock
    private ContractService contractService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        getUsersList();
    }

    private void getUsersList() {
        User u1 = new User();
        u1.setId(1);
        User u2 = new User();
        u2.setId(2);
        users.addAll(Arrays.asList(u1, u2));
    }

    @Test
    void testFindById() throws DatabaseException {
        User user = users.get(0);
        when(userDao.findById(1)).thenReturn(user);
        assertEquals(userService.findById(user.getId()), user);
        verify(userDao).findById(user.getId());
    }

    @Test
    void testFindByLogin() throws DatabaseException {
        User user = users.get(0);
        when(userDao.findByParameter("login", user.getLogin())).thenReturn(user);
        userService.findByLogin(user.getLogin());
        verify(userDao).findByParameter("login", user.getLogin());
    }

    @Test
    void testSaveUser() {
        User user = users.get(0);
        doNothing().when(userDao).save(user);
        when(passwordEncoder.encode(any())).thenReturn(user.getPassword());
        userService.saveUser(user);
        verify(userDao, atLeastOnce()).save(user);
    }

    @Test
    void testFindaAllUsers() {
        when(userDao.findAllUsers()).thenReturn(users);
        assertEquals(userService.findAllUsers(), users);
        verify(userDao).findAllUsers();
    }

    @Test
    void testFindListOfUsers() {
        when(userDao.findListOfUsers(0, users.size())).thenReturn(users);
        assertEquals(userService.findListOfUsers(0, users.size()), users);
        verify(userDao).findListOfUsers(0, users.size());
    }

    @Test
    void testDeleteUserById() throws DatabaseException, LogicException {
        User user = users.get(0);
        doNothing().when(userDao).deleteById(user.getId());
        when(userDao.findById(user.getId())).thenReturn(user);
        userService.deleteUserById(user.getId());
        verify(userDao).deleteById(user.getId());
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    @Test
    void testSetStatus() throws DatabaseException {
        User user = users.get(0);
        Status status = Status.ACTIVE;
        SetNewStatusDto setNewStatusDto = new SetNewStatusDto();
        setNewStatusDto.setEntityId(user.getId());
        setNewStatusDto.setEntityStatus(status.getStatus());

        when(userDao.findById(user.getId())).thenReturn(user);
        when(customModelMapper.mapToStatus(any())).thenReturn(status);
        userService.setCustomModelMapper(customModelMapper);
        userService.setStatus(setNewStatusDto);
        assertEquals(userService.findById(user.getId()).getStatus(), status);
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    @Test
    void testUpdateUserMail() throws DatabaseException {
        User user = users.get(0);
        String mail = "mail";
        EditMailDto editMailDto = new EditMailDto();
        editMailDto.setUserId(user.getId());
        editMailDto.setValue(mail);

        when(userDao.findById(user.getId())).thenReturn(user);
        userService.updateUserMail(editMailDto);
        assertEquals(userService.findById(user.getId()).getMail(), mail);
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    @Test
    void testUpdateUserPassport() throws DatabaseException {
        User user = users.get(0);
        String pw = "12";
        EditPassportDto editPassportDto = new EditPassportDto();
        editPassportDto.setUserId(user.getId());
        editPassportDto.setValue(pw);

        when(userDao.findById(user.getId())).thenReturn(user);
        userService.updateUserPassport(editPassportDto);
        assertEquals(userService.findById(user.getId()).getPassport(), Integer.valueOf(pw));
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    @Test
    void testUpdateUserAddress() throws DatabaseException {
        User user = users.get(0);
        String address = "address";
        EditAddressDto editAddressDto = new EditAddressDto();
        editAddressDto.setUserId(user.getId());
        editAddressDto.setValue(address);

        when(userDao.findById(user.getId())).thenReturn(user);
        userService.updateUserAddress(editAddressDto);
        assertEquals(userService.findById(user.getId()).getAddress(), address);
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    @Test
    void testFindByPhoneNumberRight() throws LogicException {
        Contract contract = new Contract();
        contract.setUser(users.get(0));
        SearchByNumberDto searchByNumberDto = new SearchByNumberDto();

        when(contractService.findByPhoneNumber(any())).thenReturn(contract);

        userService.setContractService(contractService);

        assertEquals(userService.findByPhoneNumber(searchByNumberDto), users.get(0).getId());
    }

    @Test
    void testFindByPhoneNumberWrong() {
        String exceptionMessage = "No such number.";
        userService.setContractService(contractService);
        when(contractService.findByPhoneNumber(any())).thenReturn(null);

        LogicException exception = assertThrows(LogicException.class, () -> {
            userService.findByPhoneNumber(new SearchByNumberDto());
        });
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testAddUser() {
        AddUserDto addUserDto = new AddUserDto();
        User user = users.get(0);

        when(customModelMapper.mapToEntity(User.class, addUserDto)).thenReturn(user);

        userService.setCustomModelMapper(customModelMapper);
        userService.addUser(addUserDto);

        verify(userDao, atLeastOnce()).save(user);
    }


    @Test
    void testCheckParameterNotUniqueTrue() throws DatabaseException {
        when(userDao.findByParameter(anyString(), any())).thenReturn(new User());
        assertTrue(userService.checkParameterNotUnique(anyString(), any()));
    }

    @Test
    void testCheckParameterNotUniqueFalse() throws DatabaseException {
        when(userDao.findByParameter(anyString(), any())).thenThrow(new DatabaseException("Exception message."));
        assertFalse(userService.checkParameterNotUnique(anyString(), any()));
    }

    @Test
    void testAddFunds() throws DatabaseException {
        User user = users.get(0);
        Double initBalance = 0.0;
        user.setBalance(initBalance);
        UserFundsDto userFundsDto = new UserFundsDto();
        userFundsDto.setAmount(1);
        userFundsDto.setUserId(user.getId());
        when(userDao.findById(user.getId())).thenReturn(user);
        userService.addFunds(userFundsDto);
        verify(userDao, atLeastOnce()).findById(user.getId());
        assertEquals(Double.parseDouble(userService.getBalance(user.getId())), initBalance + userFundsDto.getAmount());
    }

    @Test
    void testGetBalance() throws DatabaseException {
        User user = users.get(0);
        user.setBalance(0.0);
        when(userDao.findById(user.getId())).thenReturn(user);
        assertEquals((Double) Double.parseDouble(userService.getBalance(user.getId())), user.getBalance());
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    /**
     * Method: getPagesCount(int itemsPerPage)
     */
    @ParameterizedTest(name = "{index} => itemsCount={0}, pagesCount={1}")
    @MethodSource("dataProvider")
    void testGetPagesCount(int itemsCount, int pagesCount) throws Exception {
        when(userDao.userCount()).thenReturn(itemsCount);
        assertEquals(userService.getPagesCount(10), pagesCount);
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

    @Test
    void testEditUser() throws DatabaseException {
        User user = users.get(0);
        EditUserDto editUserDto = new EditUserDto();
        editUserDto.setId(user.getId());
        editUserDto.setAddress("address");
        when(userDao.findById(user.getId())).thenReturn(user);
        userService.editUser(editUserDto);
        verify(userDao, atLeastOnce()).findById(user.getId());
        assertEquals(userService.findById(editUserDto.getId()).getAddress(), "address");
    }

    @Test
    void testTrueCheckIfUserPasswordMatches() throws DatabaseException {
        User user = users.get(0);
        String password = "pw";
        user.setPassword(password);
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setUserId(user.getId());
        changePasswordDto.setPassword(password);
        when(userDao.findById(user.getId())).thenReturn(user);
        when(passwordEncoder.matches(any(), any())).thenReturn(user.getPassword().equals(changePasswordDto.getPassword()));
        assertTrue(userService.checkIfUserPasswordMatches(changePasswordDto));
        verify(userDao, atLeastOnce()).findById(user.getId());
    }

    @Test
    void testFalseCheckIfUserPasswordMatches() throws DatabaseException {
        User user = users.get(0);
        String password = "pw";
        user.setPassword(password);
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setUserId(user.getId());
        changePasswordDto.setPassword("anotherPassword");
        when(userDao.findById(user.getId())).thenReturn(user);
        when(passwordEncoder.matches(any(), any())).thenReturn(user.getPassword().equals(changePasswordDto.getPassword()));
        assertFalse(userService.checkIfUserPasswordMatches(changePasswordDto));
        verify(userDao, atLeastOnce()).findById(user.getId());
    }
}
