package com.wings.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.SpottedBird;

public interface SpottedBirdRepository {
    void saveSpottedBird(SpottedBird spottedBird) throws SQLException;
    List<SpottedBird> getSpottedBirdsByUserId(UUID userId) throws SQLException;
    List<SpottedBird> getAllSpottedBirds() throws SQLException;
}
