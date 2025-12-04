package com.wings.models;

import java.time.LocalDateTime;

public class Friendship {
    private int userId1;
    private int userId2;
    private LocalDateTime friendSince;

    public Friendship(int userId1, int userId2, LocalDateTime friendSince){
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.friendSince = friendSince;
    }

    public int getUserId1() { return this.userId1; };
    public int getUserId2() { return this.userId2; };
    public LocalDateTime getFriendSince() { return this.friendSince; };
}
