package com.gleb.Finance.daoTests;

import com.gleb.Finance.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    private User testUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("securePassword123");

        anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUsername("anotherUser");
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword("anotherPassword");
    }
}