package com.example.qrmon;



import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class SHAEncryptorTests {

    public SHAEncryptor mockSHAEncryptor() {
        SHAEncryptor encryptor = new SHAEncryptor();
        return encryptor;
    }

    @Test
    public void testHashing() throws NoSuchAlgorithmException {
        SHAEncryptor encryptor = mockSHAEncryptor();
        String input = "ABCD1234";
        String hasedInput = "1635c8525afbae58c37bede3c9440844e9143727cc7c160bed665ec378d8a262";
        String hashFromEncryptor = encryptor.getSHA256Hash(input);
        assertEquals(hashFromEncryptor, hasedInput);

    }


}
