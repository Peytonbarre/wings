package com.wings.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.wings.models.*;
import com.wings.repository.*;

public class BirdingService {
    private UserRepository userRepo;
    private BirdRepository birdRepo;
    private SpottedBirdRepository spottedBirdRepo;
    private FriendshipRepository friendshipRepo;

    public BirdingService(UserRepository userRepo, BirdRepository birdRepo, SpottedBirdRepository spottedBirdRepo, FriendshipRepository friendshipRepo) {
        this.userRepo = userRepo;
        this.birdRepo = birdRepo;
        this.spottedBirdRepo = spottedBirdRepo;
        this.friendshipRepo = friendshipRepo;
    }

    public User createUser(String username) throws SQLException {
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if(userRepo.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User(UUID.randomUUID(), username);
        userRepo.saveUser(user);
        return user;
    }

    public User loginUser(String username) throws SQLException {
        User user = userRepo.getUserByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    public void spotBird(User currentUser, UUID birdId) throws SQLException {
        if(currentUser == null || birdId == null) {
            throw new IllegalArgumentException("User and birdId cannot be null");
        }
        SpottedBird spottedBird = new SpottedBird(UUID.randomUUID(), currentUser.getUserId(), birdId, LocalDateTime.now());
        spottedBirdRepo.saveSpottedBird(spottedBird);
        currentUser.setCurrentStreak(getCurrentStreak(currentUser.getUserId()) + 1);
    }

    public int getCurrentStreak(UUID userId) throws SQLException {
        return spottedBirdRepo.getStreakByUserId(userId);
    }

    public List<Bird> getAllBirds() throws SQLException {
        return birdRepo.getAllBirds();
    }

    public List<SpottedBird> getMyBirds(User currentUser) throws SQLException {
        List<SpottedBird> spottedBirdList= spottedBirdRepo.getSpottedBirdsByUserId(currentUser.getUserId());

        return spottedBirdList;
    }

    public Bird getBirdById(UUID birdId) throws SQLException{
        return birdRepo.getBirdById(birdId);
    }

    public void addFriend(User currentUser, String username) throws SQLException {
        if(username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Friend username cannot be empty");
        }
        if(username == currentUser.getUsername()) {
            throw new IllegalArgumentException("Username must not be your own");
        }
        User friend = userRepo.getUserByUsername(username);
        Friendship friendship = new Friendship(currentUser.getUserId(), friend.getUserId(), LocalDateTime.now());
        friendshipRepo.addFriendship(friendship);
    }

    public List<User> getFriends(UUID userId) throws SQLException {
        List<Friendship> friends = friendshipRepo.getFriendshipByUserId(userId);
        List<User> friendList = new ArrayList<>();
        for(Friendship friend : friends) {
            UUID friendId = (userId.equals(friend.getUserId1()) ? friend.getUserId2() : friend.getUserId1());
            User user = new User(
                friendId,
                userRepo.getUserById(friendId).getUsername()
            );
            friendList.add(user);
        }
        return friendList;
    }

    public Map<User, Integer> getLeaderboard() throws SQLException {
        return spottedBirdRepo.getTopTenBirdsSpottedUsersByUserId();
    }

    public int getTotalBirdsSpotted(User user) throws SQLException {
        return spottedBirdRepo.getTotalSpottedBirdsByUserId(user.getUserId());
    }

}
