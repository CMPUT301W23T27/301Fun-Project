package com.example.qrmon;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEncryptor {
    public static String getSHA256Hash(String input) throws NoSuchAlgorithmException {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            BigInteger bigInt = new BigInteger(1,hashBytes);
            return bigInt.toString(16);
    }

    // Testing
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "BFG5DGW54";
        String hash = getSHA256Hash(input);
        System.out.println("Input string: " + input);
        System.out.println("SHA-256 hash: " + hash);
    }
}
