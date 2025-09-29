package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.IncomeDao;
import com.gleb.Finance.models.Income;
import com.gleb.Finance.models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

            if(user != null && user.getIncomes() != null) {
                incomeList = user.getIncomes();
                Hibernate.initialize(incomeList);
            }

            if(transaction != null) {
                transaction.commit();
            }
            return incomeList;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error get Income list", e);
        } finally {
            session.close();
        }
    }

    @Override
    public BigDecimal getTotalIncome(long id) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT SUM(i.amount) FROM Income i WHERE i.user.id = :userId";

            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", id)
                    .uniqueResult();

            return result != null ? result :BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating total income", e);
        } finally {
            session.close();
        }
    }
}
