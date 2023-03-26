package com.example.qrmon;

public class LeaderboardObject {
    private String name;
    private int score;

    public LeaderboardObject(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

}
