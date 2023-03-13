package com.example.qrmon;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 This class contains test cases for the ScannedCodePage activity in the QRMon application. It checks
 that the activity launches successfully, and verifies that the expected text appears on the screen
 after scanning a code. The tests use the Robotium library for UI testing and the ActivityTestRule
 class to launch the activity. Before each test, a new Solo instance is created.
 */
public class ScannedCodePageTest {

    private Solo solo;


    @Rule
    public ActivityTestRule<ScannedCodePage> rule =
            new ActivityTestRule<>(ScannedCodePage.class, true, true);


    /**
     *
     * @throws Exception Runs before all tests and creates solo instance.
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the activity
     * @throws Exception Gets the activity
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
    public void checkCode(){
// Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ScannedCodePage.class);
        assertTrue(solo.searchText("Congratulations"));
        assertTrue(solo.searchText("SCORE"));
        assertTrue(solo.searchText("You Just Caught"));
    }
}
