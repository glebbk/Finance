package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.SavingGoalDao;
import com.gleb.Finance.models.SavingGoal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class SavingGoalDaoImpl implements SavingGoalDao {

    private final SessionFactory sessionFactory;
    private final Logger logger = LoggerFactory.getLogger(SavingGoalDaoImpl.class);

    public SavingGoalDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<SavingGoal> findById(long id) {
        logger.info("Getting saving goal by id: {}", id);
        try(Session session = sessionFactory.openSession()) {
            SavingGoal goal = session.get(SavingGoal.class, id);

            logger.info("Method finished for id: {}", id);
            return Optional.ofNullable(goal);
        } catch (Exception e) {
            logger.info("Failed getting saving goal with id: {}", id);
            throw new RuntimeException("Error getting saving goal by id", e);
        }
    }

    @Override
    public Optional<BigDecimal> findTargetAmountById(long id) {
        logger.info("Getting target amount with id: {}", id);
        try(Session session = sessionFactory.openSession()) {
            String hql = "SELECT sg.targetAmount FROM SavingGoal sg " +
                    "WHERE sg.id = :id";

            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("id", id)
                    .uniqueResult();

            logger.info("Method finished with id: {}", id);

            return Optional.ofNullable(result);
        } catch(Exception e) {
            logger.info("Failed getting target amount for id: {}", id);
            throw new RuntimeException("Error getting target amount", e);
        }
    }

    @Override
    public Optional<BigDecimal> getMainSavingGoalTarget(long userId) {
        logger.info("Getting main saving goal for user: {}", userId);
        try(Session session = sessionFactory.openSession()) {
            String hql = "SELECT sg.targetAmount FROM SavingGoal sg " +
                    "WHERE sg.user.id = :userId AND sg.goalType = 'SAVINGS' " +
                    "AND sg.completed = false ORDER BY sg.deadline ASC";
            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", userId)
                    .setMaxResults(1)
                    .uniqueResult();

            logger.info("Method finished for user: {}", userId);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            logger.info("Failed getting main goal target for user: {}", userId);
            throw new RuntimeException("Error getting main goal target", e);
        }
    }
}
