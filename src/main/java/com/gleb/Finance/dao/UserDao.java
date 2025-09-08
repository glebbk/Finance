package com.gleb.Finance.dao;

import com.gleb.Finance.models.Income;
import com.gleb.Finance.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User create (User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error creating user", e);
        } finally {
            session.close();
        }
    }

    public Optional<User> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }catch (Exception e) {
            throw new RuntimeException("Error finding user by id", e);
        } finally {
            session.close();
        }
    }
}
