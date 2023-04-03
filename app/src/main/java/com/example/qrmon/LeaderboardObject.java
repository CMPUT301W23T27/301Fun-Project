package com.example.qrmon;

public class LeaderboardObject {
    private String name;
    private int score;

    private String image;

    private int rank;

    /**
     * represents leaderboard object to be used in leaderboard list
     * @param name name of leaderboard object (name of player or code)
     * @param score total score of player or score of code
     * @param rank rank of code or player
     * @param image players profile avatar or qr codes visual
     */
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
