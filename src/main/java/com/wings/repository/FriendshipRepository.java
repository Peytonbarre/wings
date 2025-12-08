package com.wings.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.wings.models.Friendship;

public interface FriendshipRepository {
    void addFriendship(Friendship friendship) throws SQLException;
    List<Integer> getFriendshipByUserId(UUID userId) throws SQLException;
    void removeFriendship(UUID userId1, UUID userId2) throws SQLException;
}
