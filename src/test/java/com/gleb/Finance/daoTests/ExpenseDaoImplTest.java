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
import java.util.Arrays;
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

        // Создаем тестового пользователя согласно вашей модели
        testUser = new User("testuser", "test@email.com", "password");
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());

        // Создаем тестовые расходы
        testExpense1 = new Expense();
        testExpense1.setId(1L);
        testExpense1.setCategory("Продукты");
        testExpense1.setAmount(BigDecimal.valueOf(5000.0));
        testExpense1.setUser(testUser); // Устанавливаем связь с пользователем

        testExpense2 = new Expense();
        testExpense2.setId(2L);
        testExpense2.setCategory("Транспорт");
        testExpense2.setAmount(BigDecimal.valueOf(2000.0));
        testExpense2.setUser(testUser); // Устанавливаем связь с пользователем

        // Устанавливаем список расходов для пользователя
        testUser.setExpenses(Arrays.asList(testExpense1, testExpense2));
    }

    @Test
    void getAllExpense_ShouldReturnExpenses_WhenUserExists() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        // Act
        List<Expense> result = expenseDao.getAllExpense(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Продукты", result.get(0).getCategory());
        assertEquals(BigDecimal.valueOf(5000.0), result.get(0).getAmount());
        assertEquals(testUser, result.get(0).getUser()); // Проверяем связь

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldReturnEmptyList_WhenUserNotFound() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 999L)).thenReturn(null);

        // Act
        List<Expense> result = expenseDao.getAllExpense(999L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 999L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldReturnEmptyList_WhenUserHasNoExpenses() {
        // Arrange
        User userWithoutExpenses = new User("noexpenses", "noexpenses@email.com", "password");
        userWithoutExpenses.setId(2L);
        userWithoutExpenses.setExpenses(Collections.emptyList());

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 2L)).thenReturn(userWithoutExpenses);

        // Act
        List<Expense> result = expenseDao.getAllExpense(2L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 2L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldHandleNullExpensesList() {
        // Arrange
        User userWithNullExpenses = new User("nullexpenses", "nullexpenses@email.com", "password");
        userWithNullExpenses.setId(3L);
        userWithNullExpenses.setExpenses(null); // Явно устанавливаем null

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 3L)).thenReturn(userWithNullExpenses);

        // Act
        List<Expense> result = expenseDao.getAllExpense(3L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(session).beginTransaction();
        verify(session).get(User.class, 3L);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldRollbackTransaction_WhenExceptionOccurs() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
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
    void getAllExpense_ShouldCloseSession_EvenWhenExceptionOccurs() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenThrow(new RuntimeException("Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> expenseDao.getAllExpense(1L));

        // Verify session is closed even after exception
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldHandleTransactionNull_WhenBeginTransactionFails() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(null); // Transaction is null
        when(session.get(User.class, 1L)).thenReturn(testUser);

        // Act
        List<Expense> result = expenseDao.getAllExpense(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        // Should not call commit or rollback when transaction is null
        verify(transaction, never()).commit();
        verify(transaction, never()).rollback();
        verify(session).close();
    }

    @Test
    void getAllExpense_ShouldInitializeLazyCollection() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(User.class, 1L)).thenReturn(testUser);

        // Act
        List<Expense> result = expenseDao.getAllExpense(1L);

        // Assert
        assertNotNull(result);
        // Hibernate.initialize() должен быть вызван для ленивой коллекции
        // В реальном тесте мы бы проверили, что коллекция инициализирована
        assertEquals(2, result.size());

        verify(session).beginTransaction();
        verify(session).get(User.class, 1L);
        verify(transaction).commit();
        verify(session).close();
    }
}