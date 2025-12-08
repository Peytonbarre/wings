package com.wings.repository.impl;

import java.sql.SQLException;
import java.util.UUID;

import com.wings.database.QueryExecuter;
import com.wings.models.User;
import com.wings.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, current_streak, total_birds_spotted) VALUES (?, ?, ?)";
        QueryExecuter.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, user.getCurrentStreak());
            pstmt.setInt(3, user.getBirdsSpotted());
        });
    }

    @Override
    public User getUserById(UUID id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return QueryExecuter.executeQuery(sql, pstmt -> {
            pstmt.setString(1, id.toString());
        }, rs -> {
            if (rs.next()) {
                User user = new User (
                    UUID.fromString(rs.getString("user_id")),
                    rs.getInt("birds_spotted"),
                    rs.getInt("current_streak"),
                    rs.getString("username")
                );
                return user;
            }
            return null;
        });
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByUsername'");
    }

    @Override
    public User[] getAllUsers() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }
    
}
