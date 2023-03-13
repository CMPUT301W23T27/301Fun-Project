package com.example.qrmon;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 This class contains test cases for the ScanCodePage class of the QRMon Android application.
 It tests whether the activity starts, whether the text in the scanned_text EditText is correct,
 and whether a city can be added to and removed from the ListView.
 It uses the Solo library for testing user interface components, and the ActivityTestRule class to start the activity.
 @author Martin M
 */
public class ScanCodePageTest {

    private Solo solo;


    @Rule
    public ActivityTestRule<ScanCodePage> rule =
            new ActivityTestRule<>(ScanCodePage.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception throws exception if rule does not complete
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
    public void checkText(){
// Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", ScanCodePage.class);
        solo.getView(R.id.scanned_text);
        assertTrue(solo.searchText("Ready to Scan"));
    }
}
