package com.wings.repository.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public int getTotalSpottedBirdsByUserId(UUID userId) throws SQLException {
        String sql = "SELECT count(*) AS total_birds_spotted FROM spotted_birds WHERE user_id = ?";
        return QueryExecuter.executeQuery(sql, pstmt -> {
            pstmt.setString(1, userId.toString());
        }, rs -> {
            if(rs.next()){
                int count = rs.getInt("total_birds_spotted");
                return count;
            }
            return null;
        });
    }

    @Override
    public Map<UUID, Integer> getTopTenBirdsSpottedUsersByUserId() throws SQLException {
        String sql = "SELECT user_id, COUNT(bird_id) AS bird_count FROM spotted_birds GROUP BY user_id ORDER BY bird_count DESC";
        Map<UUID, Integer> topTenList = new HashMap<>();
        return QueryExecuter.executeQuery(sql, pstmt->{}, rs -> {
            while(rs.next()){
                topTenList.put(UUID.fromString(rs.getString("user_id")),Integer.valueOf(rs.getInt("bird_count")));
            }
            return topTenList;
        });
    }

    @Override
    public int getStreakByUserId(UUID userId) throws SQLException {
        String sql = "SELECT \n" +
                    "  SUM(count(*)) OVER (ORDER BY strftime('%D', date_spotted)) as streak\n" +
                    "FROM spotted_birds\n" +
                    "WHERE user_id = ?\n" +
                    "GROUP BY strftime('%D', date_spotted)\n" +
                    "ORDER BY strftime('%D', date_spotted)";
        return QueryExecuter.executeQuery(sql,pstmt -> {
            pstmt.setString(1, userId.toString());
        }, rs -> {
            if(rs.next()){
                return rs.getInt("streak");
            }
            return 0;
        });
    }
}
