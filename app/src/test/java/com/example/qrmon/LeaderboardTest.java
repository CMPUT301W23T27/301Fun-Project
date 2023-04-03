package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeaderboardTest {

    /**
     * Test that the leaderboard data is loaded from Firebase successfully
     */
    @Test
    public void testFirebaseDataLoading() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.collectTopPlayerScores(10);
        leaderboard.collectTopCodes(10);
        assertEquals(20, leaderboard.leaderboardList.size());
    }
    /**
     * Test that the updateLeaderboard() method updates the ListView
     */
    @Test
    public void testUpdateLeaderboard() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.collectTopPlayerScores(10);
        leaderboard.collectTopCodes(10);
        leaderboard.updateLeaderboard();
        assertEquals(20, leaderboard.leaderboardAdapter.getCount());
    }


}
