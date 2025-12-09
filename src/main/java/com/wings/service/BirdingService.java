package com.wings.service;

import java.io.IOException;
import java.sql.SQLException;
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

    //Spot Bird
}
