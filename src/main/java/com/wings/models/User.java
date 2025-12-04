package com.wings.models;

import java.util.UUID;

public class User {
    private UUID userId;
    private int birdsSpotted;
    private int currentStreak;
    private String username;

    public User(UUID userId, int birdsSpotted, int currentStreak, String username){
        this.userId = userId;
        this.birdsSpotted = birdsSpotted;
        this.currentStreak = currentStreak;
        this.username = username;
    }

    public UUID getUserId() { return this.userId; };
    public int getBirdsSpotted() { return this.birdsSpotted; };
    public void setBirdsSpotted(int birdsSpotted) { this.birdsSpotted = birdsSpotted;};
    public int getCurrentStreak() { return this.currentStreak; };
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; };
    public String getUsername() {return this.username; };

    @Override
    public String toString(){
        return username + "\n" 
            + "Birds Spotted: " + Integer.toString(birdsSpotted) + "\n"
            + "Current Streak: " + Integer.toString(currentStreak);
    }
}
