package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CodeInterpreterTests {

    @Test
    public void QRCodeGenerationTest() {
        Integer testInteger = 5;
        CodeInterpreter codeInterpreter = new CodeInterpreter();
        QRCode newQRCode = codeInterpreter.interpret("555555555555");
        assertEquals("Greg Trump", newQRCode.getName());
        assertEquals(testInteger, newQRCode.getScore());
        assertEquals("https://api.dicebear.com/5.x/bottts/png?" +
                "seed=Bandit", newQRCode.getUrl());
        assertEquals(null, newQRCode.getVisual());
        assertEquals(null, newQRCode.getPicture());
        assertEquals("empty", newQRCode.getComment());
        assertEquals("empty", newQRCode.getGeolocation());
        assertEquals("555555555555", newQRCode.getHash());
    }
}
