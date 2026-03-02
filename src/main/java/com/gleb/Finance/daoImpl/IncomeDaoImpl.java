package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.IncomeDao;
import com.gleb.Finance.models.Income;
import com.gleb.Finance.models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class IncomeDaoImpl implements IncomeDao {

    private final SessionFactory sessionFactory;
    private final Logger logger = LoggerFactory.getLogger(IncomeDaoImpl.class);

    public IncomeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Income> getAllIncomes(long userId) {
        logger.info("Getting all incomes for user: {}", userId);
        Transaction transaction;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);

            List<Income> list = Collections.emptyList();

            if (user != null && user.getIncomes() != null) {
                list = user.getIncomes();
                Hibernate.initialize(list);
            }

            if (transaction != null) {
                transaction.commit();
            }

            logger.info("Method finished for user: {}", userId);
            return list;
        } catch (Exception e) {
            logger.info("Failed getting all incomes for user: {}", userId);
            throw new RuntimeException("Error getting all incomes", e);
        }
    }

    @Override
    public Optional<Income> getIncome(long incomeId) {
        logger.info("Getting income with id: {}", incomeId);
        try(Session session = sessionFactory.openSession()) {
            Income income = session.get(Income.class, incomeId);

            logger.info("Method finished for income: {}", income);
            return Optional.ofNullable(income);
        } catch(Exception e) {
            logger.info("Failed getting income with id: {}", incomeId);
            throw new RuntimeException("Error getting income", e);
        }
    }

    @Override
    public BigDecimal getTotalIncomeWithDate(long userId, LocalDate from, LocalDate endDate) {
        logger.info("Getting total income with date for user: {}", userId);
        try(Session session = sessionFactory.openSession()) {
            String hql = "SELECT SUM(i.amount) FROM Income i " +
                    "WHERE i.user.id = :userId " +
                    "AND i.incomeDate >= :from " +
                    "AND i.incomeDate <= :endDate";
            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", userId)
                    .setParameter("from", from)
                    .setParameter("endDate", endDate)
                    .uniqueResult();

            logger.info("Method finished for user: {}", userId);

            return result != null ? result : BigDecimal.ZERO;
        } catch(Exception e) {
            logger.info("Failed getting total income with date for user: {}", userId);
            throw new RuntimeException("Error getting total income with date", e);
        }
    }

    @Override
    public List<Income> getIncomesByDateRange(long userId, LocalDate startDate, LocalDate endDate) {
        logger.info("Getting incomes by date range for user: {}", userId);
        try(Session session = sessionFactory.openSession()) {
            String hql = "FROM Income i " +
                    "WHERE i.user.id = :userId " +
                    "AND i.incomeDate >= :startDate " +
                    "AND i.incomeDate <= :endDate";

            List<Income> result = session.createQuery(hql, Income.class)
                    .setParameter("userId", userId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();

            logger.info("Method finished for user with id: {}", userId);

            return result;
        } catch (Exception e) {
            logger.info("Failed getting incomes by date range for user: {}", userId);
            throw new RuntimeException("Error getting incomes by date range", e);
        }
    }
}
