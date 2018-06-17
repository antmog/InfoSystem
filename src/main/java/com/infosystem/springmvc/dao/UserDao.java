package com.infosystem.springmvc.dao;

import java.util.List;
import java.util.Set;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.User;


public interface UserDao {

    User findById(int id) throws DatabaseException;

    void save(User user);

    List<User> findAllUsers();

    void deleteById(int id);

    <T> User findByParameter(String parameter, T parameterValue) throws DatabaseException;

//    User findByLogin(String login) throws DatabaseException;
//
//    User findByEmail(String mail) throws DatabaseException;
//
//    User findByPassport(Integer passport) throws DatabaseException;

    User findByPassport(Integer passport) throws DatabaseException;

    List<User> findListOfUsers(int startIndex, int count);

    int userCount();

}

