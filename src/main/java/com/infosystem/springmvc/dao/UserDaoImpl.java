package com.infosystem.springmvc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infosystem.springmvc.exception.DatabaseException;
import org.springframework.stereotype.Repository;

import com.infosystem.springmvc.model.entity.User;


@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    public void save(User user) {
        persist(user);
    }

    public User findById(int id) throws DatabaseException {
        User user = getByKey(id);
        if (user == null) {
            throw new DatabaseException("User doesn't exist.");
        }
        return user;
    }

    public <T> User findByParameter(String parameter, T parameterValue) throws DatabaseException {
        List users = getSession()
                .createQuery("SELECT u FROM User u WHERE u." + parameter + " LIKE :parameter")
                .setParameter("parameter", parameterValue)
                .getResultList();
        if (users.isEmpty()) {
            throw new DatabaseException("User doesn't exist.");
        }
        return (User) users.get(0);
    }

    public User findByPassport(Integer passport) throws DatabaseException {
        List users = getSession()
                .createQuery("SELECT u FROM User u WHERE u.passport LIKE :passport")
                .setParameter("passport", passport)
                .getResultList();
        if (users.isEmpty()) {
            throw new DatabaseException("User doesn't exist.");
        }
        return (User) users.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<User> findListOfUsers(int startIndex, int count) {
        if(count==0){
            return new ArrayList<>();
        }
        List<User> users = getSession()
                .createQuery("SELECT u FROM User u ORDER BY u.id")
                .setFirstResult(startIndex-1)
                .setMaxResults(count)
                .getResultList();
        return new ArrayList<>(users) ;
    }

    @Override
    public int userCount() {
        int count = ((Long)getSession()
                .createQuery("select count(*) from User")
                .uniqueResult()).intValue();
        return count;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        List<User> users = getSession()
                .createQuery("SELECT u FROM User u")
                .getResultList();
        return users;
    }

    public void deleteById(int id) {
        List users = getSession()
                .createQuery("SELECT u FROM User u WHERE u.id LIKE :Id")
                .setParameter("Id", id)
                .getResultList();
        delete((User) users.get(0));
    }
}
