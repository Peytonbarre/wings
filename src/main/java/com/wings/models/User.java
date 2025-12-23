package com.wings.models;

import java.util.UUID;

public class User {
    private UUID userId;
    private int currentStreak;
    private String username;

    public User(UUID userId, int currentStreak, String username){
        this.userId = userId;
        this.currentStreak = currentStreak;
        this.username = username;
    }

    public User(UUID userId, String username){
        this.userId = userId;
        this.currentStreak = 0;
        this.username = username;
    }

    public UUID getUserId() { return this.userId; };
    public int getCurrentStreak() { return this.currentStreak; };
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; };
    public String getUsername() {return this.username; };

    @Override
    public String toString(){
        return username + "\n" 
            + "Current Streak: " + Integer.toString(currentStreak);
    }
}
