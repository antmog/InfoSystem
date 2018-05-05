package com.websystique.springmvc.service;

import java.util.List;
import java.util.stream.Collectors;

import com.websystique.springmvc.dto.EditUserDto;
import com.websystique.springmvc.dto.NewStatusDto;
import com.websystique.springmvc.dto.SearchUserByNumber;
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
    @Autowired
	private ContractService contractService;

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

	@Override
	public void updateUser(EditUserDto editUserDto) {
		User user = dao.findById(editUserDto.getUserId());
		switch (editUserDto.getDataInstance()) {
			case "address":
				user.setAddress(editUserDto.getValue());
				break;
			case "passport":
				user.setPassport(Integer.valueOf(editUserDto.getValue()));
				break;
			case "mail":
				user.setMail(editUserDto.getValue());
				break;
			default:
				break;
		}
	}

	@Override
	public User findByPhoneNumber(SearchUserByNumber searchUserByNumber) {
		return contractService.findByPhoneNumber(searchUserByNumber.getPhoneNumber()).getUser();
	}

}
