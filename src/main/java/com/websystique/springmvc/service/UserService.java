package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.dto.EditUserDto;
import com.websystique.springmvc.dto.NewStatusDto;
import com.websystique.springmvc.model.User;


public interface UserService {
	
	User findById(int id);

	User findByLogin(String login);

	void saveUser(User user);
	
	void updateUser(User user);

	List<User> findAllUsers();

	List<User> findFirstUsers();

	void deleteUserById(int id);

    void setStatus(NewStatusDto newStatusDto);

    void updateUser(EditUserDto editUserDto);
}