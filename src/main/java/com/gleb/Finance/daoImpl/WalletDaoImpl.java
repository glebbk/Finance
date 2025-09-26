package com.gleb.Finance.daoImpl;

import com.gleb.Finance.dao.WalletDao;
import com.gleb.Finance.models.User;
import com.gleb.Finance.models.Wallet;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

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

            if(user != null && user.getWallets() != null) {
                walletList = user.getWallets();
                Hibernate.initialize(walletList);
            }

            if(transaction != null) {
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
}
