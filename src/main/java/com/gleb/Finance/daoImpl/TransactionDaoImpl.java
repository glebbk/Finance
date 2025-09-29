package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.TransactionDao;
import com.gleb.Finance.dto.RecentTransactionDto;
import com.gleb.Finance.models.TransactionType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionDaoImpl implements TransactionDao {
    private final SessionFactory sessionFactory;

    public TransactionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<RecentTransactionDto> getRecentTransactions(long userId, int limit) {
        Session session = sessionFactory.openSession();
        try {
            String hql = """
            SELECT 
                i.description as name,
                i.amount as amount,
                'INCOME' as type,
                i.incomeDate as date
            FROM Income i 
            WHERE i.user.id = :userId
            
            UNION ALL
            
            SELECT 
                e.description as name,
                e.amount as amount,
                'EXPENSE' as type, 
                e.expenseDate as date
            FROM Expense e 
            WHERE e.user.id = :userId
            
            ORDER BY date DESC
            """;

            List<Object[]> results = session.createQuery(hql, Object[].class)
                    .setParameter("userId", userId)
                    .setMaxResults(limit)
                    .list();

            return results.stream()
                    .map(this::mapToRecentTransactionDto)
                    .collect(Collectors.toList());

        } finally {
            session.close();
        }
    }

    private RecentTransactionDto mapToRecentTransactionDto(Object[] result) {
        TransactionType type = "INCOME".equals(result[2]) ? TransactionType.INCOME : TransactionType.EXPENSE;
        return new RecentTransactionDto(
                (String) result[0], // name
                (BigDecimal) result[1], // amount
                type, // type
                (LocalDate) result[3] // date
        );
    }
}