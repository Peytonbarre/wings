package com.wings.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.User;

public interface UserRepository {
    void saveUser(User user) throws SQLException;
    User getUserById(UUID id) throws SQLException;
    User getUserByUsername(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    User[] getTopTenUsers() throws SQLException;
}
