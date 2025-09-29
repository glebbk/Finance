package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.WalletDao;
import com.gleb.Finance.models.User;
import com.gleb.Finance.models.Wallet;
import com.gleb.Finance.models.WalletType;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class WalletDaoImpl implements WalletDao {

    private final SessionFactory sessionFactory;

    public WalletDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Wallet> getAllWallets(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            List<Wallet> walletList = Collections.emptyList();

            if (user != null && user.getWallets() != null) {
                walletList = user.getWallets();
                Hibernate.initialize(walletList);
            }

            if (transaction != null) {
                transaction.commit();
            }

            return walletList;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error get Wallet list", e);
        } finally {
            session.close();
        }
    }

    @Override
    public BigDecimal getTotalAvailableBalance(long id) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT SUM(w.balance) FROM Wallet w " +
                    "WHERE w.user.id = :userId AND w.includeInAvailableBalance = true";
            return (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", id)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating available balance", e);
        } finally {
            session.close();
        }
    }

    @Override
    public BigDecimal getTotalNetWorth(long id) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT SUM(w.balance) FROM Wallet w " +
                    "WHERE w.user.id = :userId AND w.includeInNetWorth = true";
            return (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", id)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating net worth", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Map<WalletType, BigDecimal> getBalancesByType(long id) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT w.type, SUM(w.balance) FROM Wallet w " +
                    "WHERE w.user.id = :userId " +
                    "GROUP BY w.type";

            List<Object[]> results = session.createQuery(hql)
                    .setParameter("userId", id)
                    .list();
            return results.stream()
                    .collect(Collectors.toMap(
                            result -> (WalletType) result[0],
                            result -> (BigDecimal) result[1]
                    ));
        } catch (Exception e) {
            throw new RuntimeException("Error getting balances by type", e);
        } finally {
            session.close();
        }
    }
}
