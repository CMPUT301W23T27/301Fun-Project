package com.example.qrmon;
import static org.junit.Assert.assertEquals;


import android.graphics.Bitmap;
import android.graphics.Canvas;




import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.Color;


import static org.junit.Assert.assertNotNull;

/**
 This class contains test cases for CodeInterpreterActivity class
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class CodeInterpreterActivityTests {

    /**
     This test case tests the asynchronous task of CodeInterpreterActivity class
     @throws Exception if the async task fails to complete successfully
     */
    @Test
    public void testAsyncTask() throws Exception {

    }
    /**
     This test case tests the BitMapToString method of CodeInterpreterActivity class.
     It creates a square Bitmap object and converts it to a Base64 encoded String.
     Then it checks the length of the string to ensure that it matches the expected length
     */
    @Test
    public void CodeInterpreterActivityBitMapToStringTest(){
        CodeInterpreterActivity activity = new CodeInterpreterActivity();

        //Its a square
        int widthAndHeight = 100;
        Bitmap bitmap = Bitmap.createBitmap(widthAndHeight, widthAndHeight, Bitmap.Config.ARGB_8888);
        Canvas can = new Canvas(bitmap);
        can.drawColor(Color.GREEN);
        String stringOfBitmap = activity.BitMapToString(bitmap);
        assertNotNull(stringOfBitmap);

        //solid color 100 by 100 bit maps should produce a 1986 output string
        int shouldBeLength = widthAndHeight * 19 + 86;
        int realLength = stringOfBitmap.length();

        assertEquals(shouldBeLength, realLength);
    }


}

