package com.example.qrmon;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FriendDetailsFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private FriendDetailsFragment fragment;

    @Before
    public void setUp() {
        String friendUsername = "test_friend";
        fragment = FriendDetailsFragment.newInstance(friendUsername);

        FragmentTransaction transaction = activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testFriendDetailsDisplayed() {
        onView(withId(R.id.friend_full_name)).check(matches(isDisplayed()));
        onView(withId(R.id.friend_email)).check(matches(isDisplayed()));
        onView(withId(R.id.friend_username)).check(matches(isDisplayed()));
    }

//    @Test
//    public void testDeleteFriendButton() {
//        onView(withId(R.id.delete_friend_button)).perform(click());
//        // check if FriendsFragment is displayed after deleting the friend
//        onView(withId(R.id.myFriendsListView)).check(matches(isDisplayed()));
//    }
}
