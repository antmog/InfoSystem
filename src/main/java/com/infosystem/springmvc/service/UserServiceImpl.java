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
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infosystem.springmvc.dao.UserDao;
import com.infosystem.springmvc.model.entity.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    ContractService contractService;

    @Autowired
    CustomModelMapper modelMapperWrapper;

    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(int id) throws DatabaseException {
        return dao.findById(id);
    }

    public User findByLogin(String login) throws DatabaseException {
        return dao.findByParameter("login", login);
    }

    public User findByEmail(String mail) throws DatabaseException {
        return dao.findByParameter("mail", mail);
    }

    public User findByPassport(Integer passport) throws DatabaseException {
        return dao.findByParameter("passport", String.valueOf(passport));
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    @Override
    public List<User> findListOfUsers(int startIndex, int count) {
        return dao.findListOfUsers(startIndex, count);
    }

    /**
     * Deletes user if he has no contracts.
     *
     * @param id id
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
     * @param setNewStatusDto setNewStatusDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException {
        findById(setNewStatusDto.getEntityId()).setStatus(modelMapperWrapper.mapToStatus(setNewStatusDto.getEntityStatus()));
    }

    /**
     * Updates selected user fields.
     *
     * @param editAddressDto editAddressDto
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
     * @param editMailDto editMailDto
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
     * @param editPassportDto editPassportDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void updateUserPassport(EditPassportDto editPassportDto) throws DatabaseException, ValidationException {
        User user = findById(editPassportDto.getUserId());
        user.setPassport(Integer.valueOf(editPassportDto.getValue()));
    }

    /**
     * @param searchByNumberDto searchByNumberDto
     * @return user (related with contract that has phoneNumber)
     * @throws LogicException if there is no contract (user) with such phone number
     */
    @Override
    public User findByPhoneNumber(SearchByNumberDto searchByNumberDto) throws LogicException {
        Contract contract = contractService.findByPhoneNumber(searchByNumberDto.getPhoneNumber());
        if (contract == null) {
            throw new LogicException("No such number.");
        }
        return contractService.findByPhoneNumber(searchByNumberDto.getPhoneNumber()).getUser();
    }

    public void addUser(AddUserDto addUserDto) {
        User user = modelMapperWrapper.mapToEntity(User.class, addUserDto);
        user.setBalance(0.0);
        user.setStatus(Status.ACTIVE);
        saveUser(user);
    }

    @Override
    public boolean checkParameterNotUnique(String parameter, String parameterValue) {
        try {
            User user = dao.findByParameter(parameter, parameterValue);
        } catch (DatabaseException dbe) {
            return false;
        }
        return true;
    }

//    public boolean doesEmailExist(String mail) throws DatabaseException {
//        User user = findByEmail(mail);
//        return (user != null);
//    }
//
//    public boolean doesPassportExist(String passport) throws DatabaseException {
//        User user = findByPassport(Integer.valueOf(passport));
//        return (user != null);
//    }

    public void addFunds(FundsDto FundsDto, String login) throws DatabaseException {
        addFunds(findByLogin(login), FundsDto.getAmount());
    }

    public void addFunds(UserFundsDto userFundsDto) throws DatabaseException {
        addFunds(findById(userFundsDto.getUserId()), userFundsDto.getAmount());
    }

    @Override
    public void editUser(EditUserDto editUserDto) throws DatabaseException {
        User user = findById(editUserDto.getId());
        user.setFirstName(editUserDto.getFirstName());
        user.setLastName(editUserDto.getLastName());
        user.setAddress(editUserDto.getAddress());
        user.setMail(editUserDto.getMail());
        user.setPassword(passwordEncoder.encode(editUserDto.getPassword()));
}

    private void addFunds(User user, double amount) {
        user.addFunds(amount);
    }

    public void spendFunds(User user, double amount) {
        user.spendFunds(amount);
    }

    public String getBalance(GetBalanceDto getBalanceDto) throws DatabaseException {
        return String.valueOf(findById(getBalanceDto.getUserId()).getBalance());
    }

    @Override
    public int getPagesCount(int itemsPerPage) {
        return (dao.userCount() - 1) / itemsPerPage + 1;
    }
}
