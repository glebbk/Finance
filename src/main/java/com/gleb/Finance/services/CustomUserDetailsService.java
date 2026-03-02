package com.gleb.Finance.services;

import com.gleb.Finance.dao.UserDao;
import com.gleb.Finance.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);

        User user = userDao.findByUsername(username);

        if (user == null) {
            logger.info("User not found by username, trying email: {}", username);
            user = userDao.findByEmail(username);
        }

        if (user == null) {
            logger.error("User not found with username/email: {}", username);
            throw new UsernameNotFoundException("User not found with username/email: " + username);
        }

        logger.info("User found: {} (ID: {})", user.getUsername(), user.getId());
        return user;
    }
}