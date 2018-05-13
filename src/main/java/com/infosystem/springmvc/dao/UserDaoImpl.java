package com.infosystem.springmvc.dao;

import java.util.List;

import com.infosystem.springmvc.exception.DatabaseException;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.infosystem.springmvc.model.entity.User;


@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    public User findById(int id) {
        User user = getByKey(id);
        if (user != null) {
            Hibernate.initialize(user);
        }
        return user;
    }

    @Override
    public User findByLogin(String login) {
        List users = getSession()
                .createQuery("SELECT u FROM User u WHERE u.login LIKE :Login")
                .setParameter("Login", login)
                .getResultList();
        if(users.isEmpty()){
            return null;
        }
        return (User) users.get(0);
    }

    @Override
    public User findByEmail(String mail) {
        List users = getSession()
                .createQuery("SELECT u FROM User u WHERE u.mail LIKE :mail")
                .setParameter("mail", mail)
                .getResultList();
        if(users.isEmpty()){
            return null;
        }
        return (User) users.get(0);
    }

    @Override
    public User findByPassport(Integer passport) {
        List users = getSession()
                .createQuery("SELECT u FROM User u WHERE u.passport LIKE :passport")
                .setParameter("passport", passport)
                .getResultList();
        if(users.isEmpty()){
            return null;
        }
        return (User) users.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        List<User> users = getSession()
                .createQuery("SELECT u FROM User u")
                .getResultList();
        return users;
    }

    public void save(User user) {
        persist(user);
    }

    /**
     * @param id
     * @throws DatabaseException if entity doesnt exist
     */
    public void deleteById(int id){
        List users =  getSession()
                .createQuery("SELECT u FROM User u WHERE u.id LIKE :Id")
                .setParameter("Id", id)
                .getResultList();
        delete((User) users.get(0));
    }


}
