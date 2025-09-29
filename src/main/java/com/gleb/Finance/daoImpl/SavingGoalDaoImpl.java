package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.SavingGoalDao;
import com.gleb.Finance.models.SavingGoal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class SavingGoalDaoImpl implements SavingGoalDao {
    private final SessionFactory sessionFactory;

    public SavingGoalDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<SavingGoal> findById(long id) {
        Session session = sessionFactory.openSession();
        SavingGoal savingGoal = session.get(SavingGoal.class, id);
        return Optional.ofNullable(savingGoal);
    }

    @Override
    public Optional<BigDecimal> findTargetAmountById(long id) {
        Session session = sessionFactory.openSession();
        Query<BigDecimal> query = session.createQuery(
                "SELECT sg.targetAmount FROM SavingGoal sg WHERE sg.id = :id", BigDecimal.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.uniqueResult());
    }

    @Override
    public void save(SavingGoal savingGoal) {
        Session session = sessionFactory.openSession();
        session.persist(savingGoal);
    }

    @Override
    public void update(SavingGoal savingGoal) {
        Session session = sessionFactory.openSession();
        session.merge(savingGoal);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.openSession();
        SavingGoal savingGoal = session.get(SavingGoal.class, id);
        if (savingGoal != null) {
            session.remove(savingGoal);
        }
    }
}
