package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 This class contains unit tests for the QRCode class.
 */
public class QRCodeTest {

    /**
     Creates a mock QRCode object for testing purposes.
     @return QRCode object with a name and score.
     */
    public QRCode mockQRCodeObj() {
        QRCode qr = new QRCode("testBot", null, null, null, null, null, null,  99);
        return qr;
    }

    /**
     Tests the getters of the QRCode class.
     Compares the expected values with the actual values retrieved using the getters.
     */
    @Test
    public void testQR() {
        QRCode qrObj = mockQRCodeObj();
        int score = 99;
        int scoreFromQRObj = qrObj.getScore();
        String name = "testBot";
        String botName = qrObj.getName();
        assertEquals(score, scoreFromQRObj);
        assertEquals(name, botName);

    }
}
