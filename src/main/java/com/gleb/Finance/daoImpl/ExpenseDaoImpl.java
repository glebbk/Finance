package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.ExpenseDao;
import com.gleb.Finance.models.Expense;
import com.gleb.Finance.models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    private final SessionFactory sessionFactory;

    public ExpenseDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Expense> getAllExpense(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            List<Expense> expenseList = Collections.emptyList();
            if (user != null && user.getExpenses() != null) {
                expenseList = user.getExpenses();
                Hibernate.initialize(expenseList);
            }

            if(transaction != null) {
                transaction.commit();
            }
            return expenseList;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error getting Expense list", e);
        } finally {
            session.close();
        }
    }

    @Override
    public BigDecimal getTotalExpense(long id) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId";

            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", id)
                    .uniqueResult();

            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating total expense", e);
        } finally {
            session.close();
        }
    }
}
