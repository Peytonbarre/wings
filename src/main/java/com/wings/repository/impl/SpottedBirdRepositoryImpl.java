package com.wings.repository.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.Bird;
import com.wings.repository.SpottedBirdRepository;

public class SpottedBirdRepositoryImpl implements SpottedBirdRepository {

    @Override
    public void saveBird(Bird bird) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveBird'");
    }

    @Override
    public Bird getBirdById(UUID birdId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBirdById'");
    }

    @Override
    public List<Bird> getAllBirds() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllBirds'");
    }
    
}
