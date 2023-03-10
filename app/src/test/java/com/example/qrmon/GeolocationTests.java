package com.example.qrmon;
import static org.junit.Assert.assertEquals;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.Color;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

/**
 This class contains JUnit test cases for the Geolocation class. The test verifies the
 functionality of the geolocationStringToBitMap() method. The test creates a bitmap image and
 converts it to a string representation using the BitMapToString() method from the
 CodeInterpreterActivity class. The test then passes the string to the StringToBitMap() method
 from the geolocation class to convert it back to a bitmap image. The test ensures that the
 bitmap images are not null and that the string representation of the bitmap image before and
 after conversion remains the same.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class GeolocationTests {

    @Test
    public void geolocationStringToBitMap(){
        geolocation geo = new geolocation();
        CodeInterpreterActivity activity = new CodeInterpreterActivity();

        //Its a square
        int widthAndHeight = 100;
        Bitmap bitmap = Bitmap.createBitmap(widthAndHeight, widthAndHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.GREEN);
        String stringOfBitmap = activity.BitMapToString(bitmap);

        Bitmap bitMapOfStringofbitmap = geo.StringToBitMap(stringOfBitmap);
        String stringOfBitmapofStringofbitmap = activity.BitMapToString(bitMapOfStringofbitmap);
        assertNotNull(bitMapOfStringofbitmap);


        assertEquals(stringOfBitmapofStringofbitmap, stringOfBitmap);
    }
}
