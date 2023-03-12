package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class QRCodeTest {



    public QRCode mockQRCodeObj() {
        QRCode qr = new QRCode("testBot", null, null, null, null, null, null,  99);
        return qr;
    }

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
