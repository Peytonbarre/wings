package com.wings.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.wings.models.SpottedBird;
import com.wings.models.User;

public interface SpottedBirdRepository {
    void saveSpottedBird(SpottedBird spottedBird) throws SQLException;
    List<SpottedBird> getSpottedBirdsByUserId(UUID userId) throws SQLException;
    List<SpottedBird> getAllSpottedBirds() throws SQLException;
    int getTotalSpottedBirdsByUserId(UUID userId) throws SQLException;
    Map<User, Integer> getTopTenBirdsSpottedUsersByUserId() throws SQLException;
    int getStreakByUserId(UUID userId) throws SQLException;
}
