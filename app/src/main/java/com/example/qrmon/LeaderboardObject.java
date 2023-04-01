package com.example.qrmon;

public class LeaderboardObject {
    private String name;
    private int score;

    private String image;

    private int rank;

    public LeaderboardObject(String name, int score, int rank, String image) {
        this.name = name;
        this.score = score;
        this.rank = rank;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getImage() {
        return image;
    }

    public int getRank() {
        return rank;
    }
}
