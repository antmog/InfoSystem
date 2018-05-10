package com.infosystem.springmvc.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.infosystem.springmvc.model.User;


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
        User user = (User) getSession()
                .createQuery("SELECT u FROM User u WHERE u.login LIKE :Login")
                .setParameter("Login", login)
                .getSingleResult();
        if (user != null) {
            Hibernate.initialize(user);
        }
        return user;
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

    public void deleteById(int id) {
        User user = (User) getSession()
                .createQuery("SELECT u FROM User u WHERE u.id LIKE :Id")
                .setParameter("Id", id)
                .getSingleResult();
        delete(user);
    }


}
