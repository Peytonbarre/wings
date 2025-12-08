package com.wings.repository.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.SpottedBird;
import com.wings.repository.SpottedBirdRepository;

public class SpottedBirdRepositoryImpl implements SpottedBirdRepository {

    @Override
    public void saveSpottedBird(SpottedBird spottedBird) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveSpottedBird'");
    }

    @Override
    public List<SpottedBird> getSpottedBirdsByUserId(UUID userId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpottedBirdsByUserId'");
    }

    @Override
    public List<SpottedBird> getAllSpottedBirds() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSpottedBirds'");
    }
    
}
