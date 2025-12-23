package com.wings.repository.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wings.database.QueryExecuter;
import com.wings.models.Friendship;
import com.wings.repository.FriendshipRepository;
public class FriendshipRepositoryImpl implements FriendshipRepository {

    @Override
    public void addFriendship(Friendship friendship) throws SQLException {
        String sql = "INSERT INTO friendships (user_id_1, user_id_2, friend_since) VALUES (?, ?, ?)";
        QueryExecuter.executeUpdate(sql, pstmt -> {
            if(friendship.getUserId1().compareTo(friendship.getUserId2()) < 0){
                pstmt.setString(1, friendship.getUserId1().toString());
                pstmt.setString(2, friendship.getUserId2().toString());
            }else{
                pstmt.setString(1, friendship.getUserId2().toString());
                pstmt.setString(2, friendship.getUserId1().toString());
            }
            pstmt.setObject(3, friendship.getFriendSince());
        });
    }

    @Override
    public List<Friendship> getFriendshipByUserId(UUID userId) throws SQLException {
        String sql = "SELECT * FROM friendships WHERE user_id_1 = ? UNION ALL SELECT * FROM friendships WHERE user_id_2 = ?";
        List<Friendship> frienshipList = new ArrayList<>();
        return QueryExecuter.executeQuery(sql, pstmt -> {
            pstmt.setString(1, userId.toString());
            pstmt.setString(2, userId.toString());
        }, rs -> {
            while(rs.next()) {
                Friendship friendship = new Friendship(
                    UUID.fromString(rs.getString("user_id_1")),
                    UUID.fromString(rs.getString("user_id_2")),
                    LocalDateTime.parse(rs.getString("friend_since"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                )
                );
                frienshipList.add(friendship);
            }
            return frienshipList;
        });
    }

    @Override
    public void removeFriendship(UUID userId1, UUID userId2) throws SQLException {
        String sql = "DELETE FROM friendships WHERE (user_id_1 = ? AND user_id_2 = ?) OR (user_id_1 = ? AND user_id_2 = ?)";
        QueryExecuter.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, userId1.toString());
            pstmt.setString(2, userId2.toString());
            pstmt.setString(3, userId2.toString());
            pstmt.setString(4, userId1.toString());
        });
    }
    
}
