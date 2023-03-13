package com.example.qrmon;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Transforms data using a hash function - receives string and creates a hash
 * @author Martin M
 * @see CodeInterpreter
 */
public class SHAEncryptor {
    public static String getSHA256Hash(String input) throws NoSuchAlgorithmException {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            BigInteger bigInt = new BigInteger(1,hashBytes);
            return bigInt.toString(16);
    }

}
