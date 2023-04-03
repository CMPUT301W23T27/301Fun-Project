package com.example.qrmon;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.anything;

import org.junit.Test;

public class LeaderboardTest {

    /**
     *  Verify that the ListView is displayed on the screen
     */
    @Test
    public void testListViewDisplayed() {
        onView(withId(R.id.leaderboardListView)).check(matches(isDisplayed()));
    }

    /**
     * Verify that the leaderboard items are displayed on the screen
     */
    @Test
    public void testLeaderboardItemsDisplayed() {
        onData(anything()).inAdapterView(withId(R.id.leaderboardListView)).atPosition(0).check(matches(isDisplayed()));
    }

}
