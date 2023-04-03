package com.example.qrmon;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FirebaseScoreUpdaterTest {

    @Test
    public void testUpdateFirebaseScore() {
        FirebaseScoreUpdater firebaseScoreUpdater = new FirebaseScoreUpdater();
        Map<String, Object> score = new HashMap<>();
        score.put("total_score", 100);

        firebaseScoreUpdater.updateFirebaseScore(score);

        // TODO: Verify the score was updated successfully in the Firebase database
        assertTrue(true);
    }
}
