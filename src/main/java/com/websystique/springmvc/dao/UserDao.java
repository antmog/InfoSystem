package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.User;


public interface UserDao {

	User findById(int id);

	void save(User user);
	
	List<User> findAllUsers();

	void deleteById(int id);

}

