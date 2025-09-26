package com.gleb.Finance.daoTests;

import com.gleb.Finance.daoImpl.WalletDaoImpl;
import com.gleb.Finance.models.User;
import com.gleb.Finance.models.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class WalletDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    private WalletDaoImpl walletDao;

    private User testUser;
    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    void setUp() {
        walletDao = new WalletDaoImpl(sessionFactory);
        testUser = new User("testUser", "test@mail.com", "password");
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());

        wallet1 = new Wallet();
        wallet1.setId(1L);
        wallet1.setName("CreditCard");
        wallet1.setBalance(BigDecimal.valueOf(3000));
        wallet1.setUser(testUser);

        wallet2 = new Wallet();
        wallet2.setId(2L);
        wallet2.setName("Cash");
        wallet2.setBalance(BigDecimal.valueOf(5000));
        wallet2.setUser(testUser);

        testUser.setWallets(List.of(wallet1, wallet2));
    }

    @Test
    void getAllWallets_ShouldReturnWallets_WhenUserExists() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        List<Wallet> resultList = walletDao.getAllWallets(1L);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());

        assertEquals("CreditCard", resultList.getFirst().getName());
        assertEquals(BigDecimal.valueOf(3000), resultList.getFirst().getBalance());
        assertEquals("Cash", resultList.get(1).getName());
        assertEquals(BigDecimal.valueOf(5000), resultList.get(1).getBalance());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllWallets_ShouldReturnEmptyList_WhenUserNotFound() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 999L)).thenReturn(null);

        List<Wallet> resultList = walletDao.getAllWallets(999L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 999L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllWallets_ShouldReturnEmptyList_WhenUserHasNoWallets() {
        User user = new User("emptyWalletsUser", "empty@mail.com", "password");
        user.setId(3L);
        user.setWallets(Collections.emptyList());

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 3L)).thenReturn(user);

        List<Wallet> resultList = walletDao.getAllWallets(3L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 3L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllWallets_ShouldHandleNullExpensesList() {
        User user = new User("testUser", "test@mail.com", "password");
        user.setId(4L);
        user.setWallets(null);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 4L)).thenReturn(user);

        List<Wallet> resultList = walletDao.getAllWallets(4L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 4L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllWallets_ShouldRollbackTransaction_WhenExceptionOccurs() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenThrow(new RuntimeException("DataBase error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           walletDao.getAllWallets(1L);
        });

        assertEquals("Error get Wallet list", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("DataBase error", exception.getCause().getMessage());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).rollback();
        verify(session).close();
    }

    @Test
    void getAllWallets_ShouldHandleTransactionNull_WhenBeginTransactionFails() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(null);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        List<Wallet> resultList = walletDao.getAllWallets(1L);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(session).close();

        verify(transaction, never()).commit();
        verify(transaction, never()).rollback();
    }
}
