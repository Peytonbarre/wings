package com.wings.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.Bird;

public interface BirdRepository {
    void saveBird(Bird bird) throws SQLException;
    Bird getBirdById(UUID birdId) throws SQLException;
    List<Bird> getAllBirds() throws SQLException;
}
