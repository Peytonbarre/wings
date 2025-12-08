package com.wings.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void initialize() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS users (" +
            "user_id TEXT PRIMARY KEY" +
            "username UNIQUE NOT NULL," +
            "current_streak INTEGER," +
            "total_birds_spotted INTEGER)"
        );
        
        statement.execute("CREATE TABLE IF NOT EXISTS birds (" +
            "bird_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "habitat TEXT," +
            "rarity DOUBLE)"
        );

        //TODO

        statement.close();
    }
}
