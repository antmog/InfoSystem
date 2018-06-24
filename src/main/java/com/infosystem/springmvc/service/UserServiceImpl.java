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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infosystem.springmvc.dao.UserDao;
import com.infosystem.springmvc.model.entity.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private ContractService contractService;

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    private CustomModelMapper customModelMapper;

    public void setCustomModelMapper(CustomModelMapper customModelMapper) {
        this.customModelMapper = customModelMapper;
    }

    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;

    }

    public User findById(int id) throws DatabaseException {
        User user = dao.findById(id);
        return user;
    }

    public User findByLogin(String login) throws DatabaseException {
        return dao.findByParameter("login", login);
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
        if (!dao.findById(id).getUserContracts().isEmpty()) {
            String exceptionMessage = "User still have contracts!";
            throw new LogicException(exceptionMessage);
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
    public void setStatus(SetNewStatusDto setNewStatusDto) throws DatabaseException, ValidationException {
        User user = findById(setNewStatusDto.getEntityId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (user.getStatus().equals(Status.BLOCKED) && authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            String exceptionMessage = "You are not admin to do this.";
            throw new ValidationException(exceptionMessage);
        }
        Status status = customModelMapper.mapToStatus(setNewStatusDto.getEntityStatus());
        user.setStatus(status);
    }

    /**
     * Updates selected user fields.
     *
     * @param editAddressDto editAddressDto
     * @throws DatabaseException if user doesn't exist
     */
    @Override
    public void updateUserAddress(EditAddressDto editAddressDto) throws DatabaseException {
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
    public void updateUserMail(EditMailDto editMailDto) throws DatabaseException {
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
    public void updateUserPassport(EditPassportDto editPassportDto) throws DatabaseException {
        User user = findById(editPassportDto.getUserId());
        user.setPassport(Integer.valueOf(editPassportDto.getValue()));
    }

    /**
     * @param searchByNumberDto searchByNumberDto
     * @return user (related with contract that has phoneNumber)
     * @throws LogicException if there is no contract (user) with such phone number
     */
    @Override
    public Integer findByPhoneNumber(SearchByNumberDto searchByNumberDto) throws LogicException {
        Contract contract = contractService.findByPhoneNumber(searchByNumberDto.getPhoneNumber());
        if (contract == null) {
            String exceptionMessage = "No such number.";
            throw new LogicException(exceptionMessage);
        }
        return contract.getUser().getId();
    }

    public void addUser(AddUserDto addUserDto) {
        User user = customModelMapper.mapToEntity(User.class, addUserDto);
        user.setBalance(0.0);
        user.setStatus(Status.ACTIVE);
        saveUser(user);
    }

    @Override
    public <T> boolean checkParameterNotUnique(String parameter, T parameterValue) {
        try {
            dao.findByParameter(parameter, parameterValue);
        } catch (DatabaseException dbe) {
            String exceptionMessage = dbe.getMessage();
            logger.warn(exceptionMessage);
            return false;
        }
        return true;
    }

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
        if (editUserDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(editUserDto.getPassword()));
        }
    }

    @Override
    public void editUser(ChangePasswordDto changePasswordDto) throws DatabaseException {
        User user = findById(changePasswordDto.getUserId());
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
    }

    @Override
    public boolean checkIfUserPasswordMatches(ChangePasswordDto changePasswordDto) throws DatabaseException {
        User user = findById(changePasswordDto.getUserId());
        return passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword());
    }

    private void addFunds(User user, double amount) {
        user.addFunds(amount);
    }

    public String getBalance(Integer userId) throws DatabaseException {
        return String.valueOf(findById(userId).getBalance());
    }

    @Override
    public int getPagesCount(int itemsPerPage) {
        return (dao.userCount() - 1) / itemsPerPage + 1;
    }
}
