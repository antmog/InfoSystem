package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.User;


public interface UserService {
	
	User findById(int id);
	
	void saveUser(User user);
	
	void updateUser(User user);

	List<User> findAllUsers();

	void deleteUserById(int id);

}