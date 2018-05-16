package com.infosystem.springmvc.service;

import java.util.List;

import com.infosystem.springmvc.dto.FundsDto;
import com.infosystem.springmvc.dto.AddUserDto;
import com.infosystem.springmvc.dto.SearchByNumber;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.dto.editUserDto.EditAddressDto;
import com.infosystem.springmvc.dto.editUserDto.EditMailDto;
import com.infosystem.springmvc.dto.editUserDto.EditPassportDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.User;


public interface UserService {

    User findById(int id) throws DatabaseException;

    User findByLogin(String login);

    User findByEmail(String mail);

    User findByPassport(Integer passport);

    void saveUser(User user);

    List<User> findAllUsers();

    List<User> findFirstUsers();

    void deleteUserById(int id) throws LogicException, DatabaseException;

    void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException;

    void updateUserMail(EditMailDto editMailDto) throws DatabaseException, ValidationException;

    void updateUserPassport(EditPassportDto editPassportDto) throws DatabaseException, ValidationException;

    void updateUserAddress(EditAddressDto editAddressDto) throws DatabaseException, ValidationException;

    User findByPhoneNumber(SearchByNumber searchByNumber) throws LogicException;

    void addUser(AddUserDto addUserDto);

    boolean doesLoginExist(String login) ;

    boolean doesEmailExist(String mail);

    boolean doesPassportExist(String passport);

    void addFunds(FundsDto fundsDto, String login) throws DatabaseException;

    void spendFunds(FundsDto fundsDto, String login) throws DatabaseException;
}
