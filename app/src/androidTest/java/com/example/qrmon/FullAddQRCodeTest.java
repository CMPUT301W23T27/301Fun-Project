package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.widget.Button;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FullAddQRCodeTest {
    private Solo solo;


    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);


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
     * This UI test runs through the entire QRCode adding process, checking each activity along the way, and checking the QRCode was added to QRCode list
     * The default android studio emulator is able run this test only through a special "TestUser" username
     * This user bypasses the actual QRCode scanning by giving the user a predetermined QRCode string
     *
     * Note 1: THE USER MUST BE SIGNED IN AS "TestUser" FOR SCANCODE BYPASS TO WORK
     *
     * Note 2: Ensure the "TestUser" QRCode list is empty before running test
     */
    @Test
    public void AddCode(){
// Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        BottomNavigationItemView button1 = (BottomNavigationItemView) solo.getView(R.id.add_button);
        solo.clickOnView(button1);
        solo.sleep(2000);
        solo.assertCurrentActivity("Wrong Activity", ScannedCodePage.class);
        Button button2 = (Button) solo.getView(R.id.scannedCodeNextButton);
        solo.clickOnView(button2);
        solo.sleep(3000);
        solo.assertCurrentActivity("Wrong Activity", geolocation.class);
        Button button3 = (Button) solo.getView(R.id.my_rounded_button);
        solo.clickOnView(button3);
        solo.sleep(3000);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertEquals(solo.waitForText("Karen Lightfoot", 1, 5000), true);
        ListView list = (ListView) solo.getView(R.id.myCodesListView);
        solo.clickInList(0);
        solo.sleep(2000);
        Button button5 = (Button) solo.getView(R.id.myCodesDeleteButton);
        solo.clickOnView(button5);
        solo.sleep(2000);

    }
}
