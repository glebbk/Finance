package com.gleb.Finance.daoTests;

import com.gleb.Finance.daoImpl.IncomeDaoImpl;
import com.gleb.Finance.models.Income;
import com.gleb.Finance.models.User;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncomeDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private IncomeDaoImpl incomeDao;

    @Mock
    private Transaction transaction;

    private User testUser;
    private Income testIncome1;
    private Income testIncome2;

    @BeforeEach
    void setUp() {
        incomeDao = new IncomeDaoImpl(sessionFactory);
        testUser = new User("testUser", "test@mail.com", "password");
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());

        testIncome1 = new Income();
        testIncome1.setId(1L);
        testIncome1.setCategory("testIncome");
        testIncome1.setAmount(BigDecimal.valueOf(1000));

        testIncome2 = new Income();
        testIncome2.setId(2L);
        testIncome2.setCategory("testIncome2");
        testIncome2.setAmount(BigDecimal.valueOf(5000));

        testUser.setIncomes(List.of(testIncome1, testIncome2));
    }

    @Test
    void getAllIncomes_ShouldReturnIncomes_WhenUserExists() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        List<Income> resultList = incomeDao.getAllIncomes(1L);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());

        assertEquals("testIncome", resultList.getFirst().getCategory());
        assertEquals(BigDecimal.valueOf(1000), resultList.getFirst().getAmount());
        assertEquals("testIncome2", resultList.get(1).getCategory());
        assertEquals(BigDecimal.valueOf(5000), resultList.get(1).getAmount());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllIncomes_ShouldReturnEmptyList_WhenUserNotFound() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 2L)).thenReturn(null);

        List<Income> resultList = incomeDao.getAllIncomes(2L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 2L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllIncomes_ShouldReturnEmptyList_WhenUserHasNoIncomes() {
        User testUser = new User("test", "test@mail.com", "password");
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(User.class, 3L)).thenReturn(testUser);
        when(session.beginTransaction()).thenReturn(transaction);

        List<Income> resultList = incomeDao.getAllIncomes(3L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 3L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllIncomes_ShouldHandleNullExpensesList() {
        User user = new User("testUser", "test@mail.com", "password");
        user.setId(1L);
        user.setIncomes(null);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenReturn(user);

        List<Income> resultList = incomeDao.getAllIncomes(1L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllIncomes_ShouldRollbackTransaction_WhenExceptionOccurs() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            incomeDao.getAllIncomes(1L);
        });

        assertEquals("Error get Income list", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("Database error", exception.getCause().getMessage());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction, never()).commit();
        verify(transaction).rollback();
        verify(session).close();
    }

    @Test
    void getAllIncomes_ShouldHandleTransactionNull_WhenBeginTransactionFails() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(null);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        List<Income> resultList = incomeDao.getAllIncomes(1L);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction, never()).commit();
        verify(transaction, never()).rollback();
        verify(session).close();
    }
}
