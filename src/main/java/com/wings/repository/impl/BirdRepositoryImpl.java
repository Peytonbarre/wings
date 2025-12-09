package com.wings.repository.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wings.database.QueryExecuter;
import com.wings.models.Bird;
import com.wings.repository.BirdRepository;
public class BirdRepositoryImpl implements BirdRepository {

    @Override
    public void saveBird(Bird bird) throws SQLException {
        String sql = "INSERT INTO birds (name, habitat, rarity) VALUES (?, ?, ?)";
        QueryExecuter.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, bird.getName());
            pstmt.setString(2, bird.getHabitat());
            pstmt.setDouble(3, bird.getRarity());
        });
    }

    @Override
    public Bird getBirdById(UUID birdId) throws SQLException {
        String sql = "SELECT * FROM birds WHERE id = ?";
        return QueryExecuter.executeQuery(sql, pstmt -> {
            pstmt.setString(1, birdId.toString());
        }, rs -> {
            if(rs.next()){
                Bird bird = new Bird(
                    UUID.fromString(rs.getString("bird_id")),
                    rs.getString("name"),
                    rs.getString("habitat"),
                    rs.getDouble("rarity")
                );
                return bird;
            }
            return null;
        });
    }

    @Override
    public List<Bird> getAllBirds() throws SQLException {
        String sql = "SELECT * FROM birds";
        List<Bird> birdList = new ArrayList<>();
        return QueryExecuter.executeQuery(sql, pstmt -> {}, rs -> {
            while(rs.next()){
                Bird bird = new Bird(
                    UUID.fromString(rs.getString("bird_id")),
                    rs.getString("name"),
                    rs.getString("habitat"),
                    rs.getDouble("rarity")
                );
                allBirds.add(bird);
            }
            return birdList;
        });
    }
    
}
