package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.WalletDao;
import com.gleb.Finance.models.User;
import com.gleb.Finance.models.Wallet;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


@Repository
public class WalletDaoImpl implements WalletDao {

    private final SessionFactory sessionFactory;
    private final Logger logger = LoggerFactory.getLogger(WalletDaoImpl.class);

    public WalletDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Wallet> getAllWallets(long userId) {
        Transaction transaction;
        logger.info("Getting all wallets for user: {}", userId);
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, userId);

            List<Wallet> list = Collections.emptyList();

            if (user != null && user.getWallets() != null) {
                list = user.getWallets();
                Hibernate.initialize(list);
            }

            if (transaction != null) {
                transaction.commit();
            }

            logger.info("Method finished for user: {}", userId);
            return list;
        } catch (Exception e) {
            logger.info("Failed getting all wallets for user: {}", userId);
            throw new RuntimeException("Error getting wallets", e);
        }
    }

    @Override
    public BigDecimal getTotalAvailableBalance(long userId) {
        logger.info("Getting total available balance for user: {}", userId);
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT SUM(w.balance) FROM Wallet w " +
                    "WHERE w.user.id = :userId AND w.includeInAvailableBalance = true";

            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", userId)
                    .uniqueResult();

            logger.info("Method finished for user: {}", userId);

            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.info("Failed getting total available balance for user: {}", userId);
            throw new RuntimeException("Error getting total available balance", e);
        }
    }

    @Override
    public BigDecimal getTotalNetWorth(long userId) {
        logger.info("Getting total net worth balance for user: {}", userId);
        try(Session session = sessionFactory.openSession()) {
            String hql = "SELECT SUM(w.balance) FROM Wallet w " +
                    "WHERE w.user.id = :userId AND w.includeInNetWorth = true";

            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", userId)
                    .uniqueResult();

            logger.info("Method finished for user: {}", userId);

            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.info("Failed getting total net worth balance for user: {}", userId);
            throw new RuntimeException("Error getting total net worth balance", e);
        }
    }

//    @Override
//    public Map<WalletType, BigDecimal> getBalancesByType(long id) {
//        Session session = sessionFactory.openSession();
//        try {
//            String hql = "SELECT w.type, SUM(w.balance) FROM Wallet w " +
//                    "WHERE w.user.id = :userId " +
//                    "GROUP BY w.type";
//
//            List<Object[]> results = session.createQuery(hql)
//                    .setParameter("userId", id)
//                    .list();
//            return results.stream()
//                    .collect(Collectors.toMap(
//                            result -> (WalletType) result[0],
//                            result -> (BigDecimal) result[1]
//                    ));
//        } catch (Exception e) {
//            throw new RuntimeException("Error getting balances by type", e);
//        } finally {
//            session.close();
//        }
//    }
}
