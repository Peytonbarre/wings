package com.wings.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS users (" +
            "user_id TEXT PRIMARY KEY," +
            "username TEXT UNIQUE NOT NULL," +
            "current_streak INTEGER DEFAULT 0," +
            "total_birds_spotted INTEGER DEFAULT 0)"
        );
        
        statement.execute("CREATE TABLE IF NOT EXISTS birds (" +
            "bird_id TEXT PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "habitat TEXT," +
            "rarity REAL)"
        );

        statement.execute("CREATE TABLE IF NOT EXISTS spotted_birds (" +
            "spotted_bird_id TEXT PRIMARY KEY," +
            "user_id TEXT NOT NULL," +
            "bird_id TEXT NOT NULL," +
            "date_spotted TIMESTAMP NOT NULL," +
            "FOREIGN KEY (user_id) REFERENCES users(user_id)," +
            "FOREIGN KEY (bird_id) REFERENCES birds(bird_id))"
        );

        statement.execute("CREATE TABLE IF NOT EXISTS friendships (" +
            "user_id_1 TEXT NOT NULL," +
            "user_id_2 TEXT NOT NULL," +
            "friend_since TIMESTAMP NOT NULL," +
            "PRIMARY KEY (user_id_1, user_id_2)," +
            "FOREIGN KEY (user_id_1) REFERENCES users(user_id)," +
            "FOREIGN KEY (user_id_2) REFERENCES users(user_id))"
        );

        statement.close();
    }
}
