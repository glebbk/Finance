package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.WalletBalanceHistoryDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class WalletBalanceHistoryDaoImpl implements WalletBalanceHistoryDao {
    private final SessionFactory sessionFactory;

    public WalletBalanceHistoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BigDecimal getTotalAvailableBalanceByIdAndDate(long userId, LocalDate date) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT COALESCE(SUM(h.balance), 0) " +
                    "FROM WalletBalanceHistory h " +
                    "WHERE h.wallet.user.id = :userId " +
                    "AND h.balanceDate = :date " +
                    "AND h.wallet.includeInAvailableBalance = true";

            BigDecimal result = (BigDecimal) session.createQuery(hql)
                    .setParameter("userId", userId)
                    .setParameter("date", date)
                    .uniqueResult();

            return result != null ? result : BigDecimal.ZERO;
        } finally {
            session.close();
        }
    }
}
