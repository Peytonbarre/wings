package com.wings.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
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
        SpottedBird spottedBird = new SpottedBird(UUID.randomUUID(), currentUser.getUserId(), birdId, LocalDateTime.now());
        spottedBirdRepo.saveSpottedBird(spottedBird);
    }

    public List<Bird> getAllBirds() throws SQLException {
        return birdRepo.getAllBirds();
    }

    public List<SpottedBird> getMyBirds(User currentUser) throws SQLException {
        return spottedBirdRepo.getSpottedBirdsByUserId(currentUser.getUserId());
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
}
