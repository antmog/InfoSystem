package com.infosystem.springmvc.service;

import java.util.List;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditAddressDto;
import com.infosystem.springmvc.dto.editUserDto.EditMailDto;
import com.infosystem.springmvc.dto.editUserDto.EditPassportDto;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.User;


public interface UserService {

    User findById(int id) throws DatabaseException;

    User findByLogin(String login) throws DatabaseException;

    void saveUser(User user);

    List<User> findAllUsers();

    List<User> findListOfUsers(int startIndex, int count);

    void deleteUserById(int id) throws LogicException, DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException;

    void updateUserMail(EditMailDto editMailDto) throws DatabaseException, ValidationException;

    void updateUserPassport(EditPassportDto editPassportDto) throws DatabaseException, ValidationException;

    void updateUserAddress(EditAddressDto editAddressDto) throws DatabaseException, ValidationException;

    Integer findByPhoneNumber(SearchByNumberDto searchByNumberDto) throws LogicException;

    void addUser(AddUserDto addUserDto);

    boolean checkParameterNotUnique(String parameter, String parameterValue);

    void addFunds(FundsDto fundsDto, String login) throws DatabaseException;

    void spendFunds(User user, double amount);

    String getBalance(Integer userId) throws DatabaseException;

    int getPagesCount(int itemsPerPAge);

    void addFunds(UserFundsDto userFundsDto) throws DatabaseException;

    void editUser(EditUserDto editUserDto) throws DatabaseException;

    void editUser(ChangePasswordDto changePasswordDto) throws DatabaseException;

    boolean checkIfUserPasswordMatches(ChangePasswordDto changePasswordDto) throws DatabaseException;
}
