package com.wings.repository.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wings.database.QueryExecuter;
import com.wings.models.SpottedBird;
import com.wings.repository.SpottedBirdRepository;

public class SpottedBirdRepositoryImpl implements SpottedBirdRepository {

    @Override
    public void saveSpottedBird(SpottedBird spottedBird) throws SQLException {
        String sql = "INSERT INTO spotted_birds (spotted_bird_id, user_id, bird_id, date_spotted) VALUES (?, ?, ?, ?)";
        QueryExecuter.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, spottedBird.getSpottedBirdId().toString());
            pstmt.setString(2, spottedBird.getUserId().toString());
            pstmt.setString(3, spottedBird.getBirdId().toString());
            pstmt.setString(4, spottedBird.getDateSpotted().toString());
        });
        
        sql = "UPDATE users SET total_birds_spotted = total_birds_spotted + 1 WHERE user_id = ?";
        QueryExecuter.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, spottedBird.getUserId().toString());
        });
    }

    @Override
    public List<SpottedBird> getSpottedBirdsByUserId(UUID userId) throws SQLException {
        String sql = "SELECT * FROM spotted_birds WHERE user_id = ?";
        List<SpottedBird> spottedBirdList = new ArrayList<>();
        return QueryExecuter.executeQuery(sql, pstmt -> {
            pstmt.setString(1, userId.toString());
        }, rs -> {
            while(rs.next()) {
                SpottedBird spottedBird = new SpottedBird(
                    UUID.fromString(rs.getString("spotted_bird_id")),
                    UUID.fromString(rs.getString("user_id")),
                    UUID.fromString(rs.getString("bird_id")),
                    LocalDateTime.parse(rs.getString("date_spotted"))
                );
                spottedBirdList.add(spottedBird);
            }
            return spottedBirdList;
        });
    }

    @Override
    public List<SpottedBird> getAllSpottedBirds() throws SQLException {
        String sql = "SELECT * FROM spotted_birds";
        List<SpottedBird> spottedBirdList = new ArrayList<>();
        return QueryExecuter.executeQuery(sql, pstmt -> {}, rs -> {
            while(rs.next()) {
                SpottedBird spottedBird = new SpottedBird(
                    UUID.fromString(rs.getString("spotted_bird_id")),
                    UUID.fromString(rs.getString("user_id")),
                    UUID.fromString(rs.getString("bird_id")),
                    LocalDateTime.parse(rs.getString("date_spotted"))
                );
                spottedBirdList.add(spottedBird);
            }
            return spottedBirdList;
        });
    }
}
