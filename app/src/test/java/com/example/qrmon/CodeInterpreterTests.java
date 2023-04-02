package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**

 This class contains a test method to verify that the QRCode object is generated correctly
 by the CodeInterpreter class. The test generates a QRCode object using the interpret method of
 CodeInterpreter class and then compares the expected values with the actual values of the QRCode object.
 The expected values are hardcoded into the test case.
 */
public class CodeInterpreterTests {

    /**

     This test method checks if the QRCode object is generated correctly by the CodeInterpreter class.
     It generates a new QRCode object using the interpret method of CodeInterpreter class with a given
     input string "555555555555" and then compares the expected values of the QRCode object with the actual values.
     The expected values are Greg Trump for name, 5 for score, "https://api.dicebear.com/5.x/bottts/png?seed=Bandit"
     for url, null for visual and picture, and "empty" for comment and geolocation. If the actual values match the
     expected values, the test passes.
     */
    @Test
    public void QRCodeGenerationTest() {
        Integer testInteger = 9;
        CodeInterpreter codeInterpreter = new CodeInterpreter();
        QRCode newQRCode = codeInterpreter.interpret("3333333333333333333");
        assertEquals("Ian the IV ", newQRCode.getName());
        assertEquals(testInteger, newQRCode.getScore());
        assertEquals("https://api.dicebear.com/5.x/bottts/png?" +
                "seed=Maggie&eyes=frame1&mouth=grill02", newQRCode.getUrl());
        assertEquals(null, newQRCode.getVisual());
        assertEquals(null, newQRCode.getPicture());
        assertEquals("empty", newQRCode.getComment());
        assertEquals(null, newQRCode.getLongitude());
        assertEquals(null, newQRCode.getLatitude());
        assertEquals("3333333333333333333", newQRCode.getHash());
    }
}
