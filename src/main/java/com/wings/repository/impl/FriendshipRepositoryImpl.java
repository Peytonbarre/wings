package com.wings.repository.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.Friendship;
import com.wings.repository.FriendshipRepository;
public class FriendshipRepositoryImpl implements FriendshipRepository {

    @Override
    public void addFriendship(Friendship friendship) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFriendship'");
    }

    @Override
    public List<Integer> getFriendshipByUserId(UUID userId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFriendshipByUserId'");
    }

    @Override
    public void removeFriendship(UUID userId1, UUID userId2) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFriendship'");
    }
    
}
