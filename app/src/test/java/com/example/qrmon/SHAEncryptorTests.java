package com.example.qrmon;



import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
/**

 A class that tests the SHAEncryptor class for proper encryption of input.

 It includes a mockSHAEncryptor() method to create a new SHAEncryptor object.

 The testHashing() method tests the SHA256 encryption algorithm implemented in the getSHA256Hash() method.

 @author Martin M

 @version 3.0

 @since Mar 10 2023
 */

public class SHAEncryptorTests {

    /**
     Returns a new instance of the SHAEncryptor object.
     */
    public SHAEncryptor mockSHAEncryptor() {
        SHAEncryptor encryptor = new SHAEncryptor();
        return encryptor;
    }

    /**
     Tests the getSHA256Hash() method of the SHAEncryptor class for proper hashing of the input string.
     It compares the hashed input with the expected hashed value.
     @throws NoSuchAlgorithmException if the SHA-256 algorithm is not supported by the system.
     */
    @Test
    public void testHashing() throws NoSuchAlgorithmException {
        SHAEncryptor encryptor = mockSHAEncryptor();
        String input = "ABCD1234";
        String hasedInput = "1635c8525afbae58c37bede3c9440844e9143727cc7c160bed665ec378d8a262";
        String hashFromEncryptor = encryptor.getSHA256Hash(input);
        assertEquals(hashFromEncryptor, hasedInput);

    }


}
