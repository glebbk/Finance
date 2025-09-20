package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.IncomeDao;
import com.gleb.Finance.models.Income;
import com.gleb.Finance.models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class IncomeDaoImpl implements IncomeDao {

    private final SessionFactory sessionFactory;

    public IncomeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Income> getAllIncomes(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            List<Income> incomeList = List.of();

            if(user != null) {
                incomeList = user.getIncomes();
                Hibernate.initialize(incomeList);
            }

            transaction.commit();
            return incomeList;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error get Income list", e);
        } finally {
            session.close();
        }
    }
}
