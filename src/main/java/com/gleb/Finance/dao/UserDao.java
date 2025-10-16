package com.gleb.Finance.dao;

import com.gleb.Finance.models.User;

public interface UserDao {
    User getUser(long id);
    User findByEmail(String email);
    User findByUsername(String username);
}