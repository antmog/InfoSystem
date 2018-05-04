package com.websystique.springmvc.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.websystique.springmvc.dto.NewStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.User;

import static java.util.Arrays.stream;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
    @Autowired
    private PasswordEncoder passwordEncoder;

	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByLogin(String login) {
		return dao.findByLogin(login);
	}

	public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setAddress(user.getAddress());
			entity.setBirthDate(user.getBirthDate());
			entity.setPassport(user.getPassport());
			entity.setLogin(user.getLogin());
			entity.setMail(user.getMail());
			entity.setPassword(user.getPassword());
			entity.setRole(user.getRole());
		}
	}


	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	@Override
	public List<User> findFirstUsers() {
		return dao.findAllUsers().stream().limit(5).collect(Collectors.toList());
	}

	@Override
	public void deleteUserById(int id) {
		dao.deleteById(id);
	}

	@Override
	public void setStatus(NewStatusDto newStatusDto) {
		dao.findById(newStatusDto.getEntityId()).setStatus(newStatusDto.getEntityStatus());
	}

}
