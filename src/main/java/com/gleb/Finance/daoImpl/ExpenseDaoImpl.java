package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.ExpenseDao;
import com.gleb.Finance.models.Expense;
import com.gleb.Finance.models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    private final SessionFactory sessionFactory;

    private final Logger logger = LoggerFactory.getLogger(ExpenseDaoImpl.class);

    public ExpenseDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Expense> getAllExpense(long userId) {
        logger.info("Getting all expenses for user: {}", userId);
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);

            List<Expense> list = Collections.emptyList();

            if (user != null && user.getExpenses() != null) {
                list = user.getExpenses();
                Hibernate.initialize(list);
            }

            if (transaction != null) {
                transaction.commit();
            }
            logger.debug("Method finished for user: {}", userId);
            return list;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failed to get expenses for user: {}", userId, e);
            throw new RuntimeException("Error getting Expense list", e);
        }
    }

    @Override
    public Optional<Expense> getExpense(long expenseId) {
        logger.info("Getting expense with id: {}", expenseId);
        try (Session session = sessionFactory.openSession()) {
            Expense expense = session.get(Expense.class, expenseId);

            logger.debug("Method finished for expenseId: {}", expenseId);
            return Optional.ofNullable(expense);
        } catch (Exception e) {
            logger.error("Failed to get expense with id: {}", expenseId, e);
            throw new RuntimeException("Error getting Expense", e);
        }
    }

//    @Override
//    public BigDecimal getTotalExpenseWithDate(long userId, LocalDate from, LocalDate to) {
//        Session session = sessionFactory.openSession();
//        try {
//            String hql = "SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
//                    "WHERE e.user.id = :userId " +
//                    "AND e.expenseDate >= :from " +
//                    "AND e.expenseDate <= :to";
//
//            return (BigDecimal) session.createQuery(hql)
//                    .setParameter("userId", userId)
//                    .setParameter("from", from)
//                    .setParameter("to", to)
//                    .uniqueResult();
//        }  catch (Exception e) {
//            throw new RuntimeException("Error calculating total Expense With Date", e);
//        } finally {
//            session.close();
//        }
//    }
}
