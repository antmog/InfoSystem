package com.infosystem.springmvc.service;

import java.util.List;
import java.util.stream.Collectors;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditAddressDto;
import com.infosystem.springmvc.dto.editUserDto.EditMailDto;
import com.infosystem.springmvc.dto.editUserDto.EditPassportDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosystem.springmvc.dao.UserDao;
import com.infosystem.springmvc.model.entity.User;

import static java.util.Arrays.stream;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContractService contractService;

    @Autowired
    CustomModelMapper modelMapperWrapper;

    public User findById(int id) throws DatabaseException {
        User user = dao.findById(id);
        if (user == null) {
            throw new DatabaseException("User doesn't exist.");
        }
        return user;
    }

    public User findByLogin(String login) {
        return dao.findByLogin(login);
    }

    public User findByEmail(String mail) {
        return dao.findByEmail(mail);
    }

    public User findByPassport(Integer passport) {
        return dao.findByPassport(passport);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    @Override
    public List<User> findFirstUsers() {
        return dao.findAllUsers().stream().limit(5).collect(Collectors.toList());
    }

    /**
     * Deletes user if he has no contracts.
     *
     * @param id
     * @throws LogicException    if user still has contracts
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void deleteUserById(int id) throws LogicException, DatabaseException {
        findById(id);
        if (!dao.findById(id).getUserContracts().isEmpty()) {
            throw new LogicException("User still have contracts!");
        }
        dao.deleteById(id);
    }

    /**
     * Set user status to selected status.
     *
     * @param setNewStatusDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException {
        findById(setNewStatusDto.getEntityId()).setStatus(modelMapperWrapper.mapToStatus(setNewStatusDto.getEntityStatus()));
    }

    /**
     * Updates selected user fields.
     *
     * @param editAddressDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void updateUserAddress(EditAddressDto editAddressDto) throws DatabaseException, ValidationException {
        User user = findById(editAddressDto.getUserId());
        user.setAddress(editAddressDto.getValue());
    }

    /**
     * Updates selected user fields.
     *
     * @param editMailDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void updateUserMail(EditMailDto editMailDto) throws DatabaseException, ValidationException {
        User user = findById(editMailDto.getUserId());
        user.setMail(editMailDto.getValue());
    }

    /**
     * Updates selected user fields.
     *
     * @param editPassportDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void updateUserPassport(EditPassportDto editPassportDto) throws DatabaseException, ValidationException {
        User user = findById(editPassportDto.getUserId());
        user.setPassport(Integer.valueOf(editPassportDto.getValue()));
    }

    /**
     * @param searchByNumber
     * @return user (related with contract that has phoneNumber)
     * @throws LogicException if there is no contract (user) with such phone number
     */
    @Override
    public User findByPhoneNumber(SearchByNumber searchByNumber) throws LogicException {
        Contract contract = contractService.findByPhoneNumber(searchByNumber.getPhoneNumber());
        if (contract == null) {
            throw new LogicException("No such number.");
        }
        return contractService.findByPhoneNumber(searchByNumber.getPhoneNumber()).getUser();
    }

    public void addUser(AddUserDto addUserDto) {
        User user = modelMapperWrapper.mapToUser(addUserDto);
        user.setBalance(0.0);
        user.setStatus(Status.ACTIVE);
        saveUser(user);
    }

    public boolean doesLoginExist(String login) {
        User user = findByLogin(login);
        return (user != null);
    }

    public boolean doesEmailExist(String mail) {
        User user = findByEmail(mail);
        return (user != null);
    }

    public boolean doesPassportExist(String passport) {
        User user = findByPassport(Integer.valueOf(passport));
        return (user != null);
    }

    public void addFunds(FundsDto FundsDto, String login) throws DatabaseException {
        User user = findByLogin(login);
        checkIfUserExist(user);
        user.addFunds(FundsDto.getAmount());
    }

    public void spendFunds(FundsDto FundsDto, String login) throws DatabaseException {
        User user = findByLogin(login);
        checkIfUserExist(user);
        user.spendFunds(FundsDto.getAmount());
    }

    public String getBalance(GetBalanceDto getBalanceDto) throws DatabaseException {
        return String.valueOf(findById(getBalanceDto.getUserId()).getBalance());
    }

    private void checkIfUserExist(User user) throws DatabaseException {
        if (user == null) {
            throw new DatabaseException("Whoops, user doesn't exist.");
        }
    }
}
