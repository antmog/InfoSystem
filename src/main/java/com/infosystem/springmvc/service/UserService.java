package com.infosystem.springmvc.service;

import java.util.List;

import com.infosystem.springmvc.dto.EditUserDto;
import com.infosystem.springmvc.dto.SearchByNumber;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.model.User;


public interface UserService {
	
	User findById(int id);

	User findByLogin(String login);

	void saveUser(User user);
	
	void updateUser(User user);

	List<User> findAllUsers();

	List<User> findFirstUsers();

	String deleteUserById(int id);

    void setStatus(SetNewStatusDto setNewStatusDto);

    void updateUser(EditUserDto editUserDto);

    User findByPhoneNumber(SearchByNumber searchByNumber);
}