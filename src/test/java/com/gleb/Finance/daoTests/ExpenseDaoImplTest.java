package com.gleb.Finance.daoTests;

import com.gleb.Finance.daoImpl.ExpenseDaoImpl;
import com.gleb.Finance.models.Expense;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    private ExpenseDaoImpl expenseDao;

    private User testUser;
    private Expense testExpense1;
    private Expense testExpense2;

    @BeforeEach
    void setUp() {
        expenseDao = new ExpenseDaoImpl(sessionFactory);

        testUser = new User("testUser", "test@mail.com", "password");
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());

        testExpense1 = new Expense();
        testExpense1.setId(1L);
        testExpense1.setCategory("Products");
        testExpense1.setAmount(BigDecimal.valueOf(5000));
        testExpense1.setUser(testUser);

        testExpense2 = new Expense();
        testExpense2.setId(2L);
        testExpense2.setCategory("Transport");
        testExpense2.setAmount(BigDecimal.valueOf(2000));
        testExpense2.setUser(testUser);

        testUser.setExpenses(List.of(testExpense1, testExpense2));
    }

    @Test
    void getAllExpense_ShouldReturnExpenses_WhenUserExists() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        List<Expense> resultList = expenseDao.getAllExpense(1L);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals("Products", resultList.getFirst().getCategory());
        assertEquals(BigDecimal.valueOf(5000), resultList.getFirst().getAmount());

        assertEquals("Transport", resultList.get(1).getCategory());
        assertEquals(BigDecimal.valueOf(2000), resultList.get(1).getAmount());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpenses_ShouldReturnEmptyList_WhenUserNotFound() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 999L)).thenReturn(null);

        List<Expense> resultList = expenseDao.getAllExpense(999L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 999L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpenses_ShouldReturnEmptyList_WhenUserHashNoExpenses() {
        User userWithoutExpenses = new User("noExpenses", "test@mail.com", "password");
        userWithoutExpenses.setId(2L);
        userWithoutExpenses.setExpenses(Collections.emptyList());

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 2L)).thenReturn(userWithoutExpenses);

        List<Expense> resultList = expenseDao.getAllExpense(2L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 2L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpenses_ShouldHandleNullExpensesList() {
        User userWithNullExpenses = new User("nullExpenses", "test@mail.com", "password");
        userWithNullExpenses.setId(3L);
        userWithNullExpenses.setExpenses(null);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 3L)).thenReturn(userWithNullExpenses);

        List<Expense> resultList = expenseDao.getAllExpense(3L);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 3L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpenses_ShouldRollbackTransaction_WhenExceptionOccurs() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           expenseDao.getAllExpense(1L);
        });

        assertEquals("Error getting Expense list", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("Database error", exception.getCause().getMessage());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).rollback();
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldHandleTransactionNull_WhenBeginTransactionFails() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(null);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        List<Expense> resultList = expenseDao.getAllExpense(1L);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(session).close();

        verify(transaction, never()).commit();
        verify(transaction, never()).rollback();
    }
}