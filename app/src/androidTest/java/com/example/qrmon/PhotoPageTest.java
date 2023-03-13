package com.example.qrmon;

import static junit.framework.TestCase.assertTrue;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 The PhotoPageTest class contains tests that verify the functionality of the PhotoPage activity in the QRMon application.
 It uses the Robotium testing framework to interact with UI elements and verify their behavior.
 */

public class PhotoPageTest {
    private Solo solo;


    @Rule
    public ActivityTestRule<PhotoPage> rule =
            new ActivityTestRule<>(PhotoPage.class, true, true);


    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception if it does not create solo instance
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Gets the Activity
     * @throws Exception if it does not get the activity
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    /**
     * Add a city to the listview and check the city name using assertTrue
     * Clear all the cities from the listview and check again with assertFalse
     */
    @Test
    public void checkPhoto(){
// Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", PhotoPage.class);
        solo.getView(R.id.retakePhotoButton);
        assertTrue(solo.searchText("Retake"));
        solo.getView(R.id.confirmPhotoButton);
        assertTrue(solo.searchText("Confirm"));

    }
}
