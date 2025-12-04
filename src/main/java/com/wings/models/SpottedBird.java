package com.wings.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class SpottedBird {
    private int spottedBirdId;
    private UUID userId;
    private int birdId;
    private LocalDateTime dateSpotted;

    public SpottedBird(int spottedBirdId, UUID userId, int birdId, LocalDateTime dateSpotted){
        this.spottedBirdId = spottedBirdId;
        this.userId = userId;
        this.birdId = birdId;
        this.dateSpotted = dateSpotted;
    }

    public int getSpottedBirdId() { return this.spottedBirdId; };
    public UUID getUserId() { return this.userId; };
    public int getBirdId() { return this.birdId; };
    public LocalDateTime getDateSpotted() { return this.dateSpotted; };
}
