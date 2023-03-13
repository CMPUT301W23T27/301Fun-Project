package com.example.qrmon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Sorts the codes in my codes based on ascending or descending order
 * @author Ian H
 */
public class CodeSorting {

    /**
     * Ascending order calculation
     * @param codes from arraylist
     * @return new calculated scores of codes or code given
     */
    public static ArrayList<QRCode> ascendingCodeSort (ArrayList<QRCode> codes){
        Collections.sort(codes, new Comparator<QRCode>() {
            @Override
            public int compare(QRCode bot1, QRCode bot2) {
                return bot1.getScore() - bot2.getScore();
            }
        });
        return codes;
    }

    /**
     * Descending order calculation
     * @param codes from arraylist
     * @return new calculated scores of codes or code given
     */
    public static ArrayList<QRCode> descendingCodeSort (ArrayList<QRCode> codes){
        Collections.sort(codes, new Comparator<QRCode>() {
            @Override
            public int compare(QRCode bot1, QRCode bot2) {
                return bot2.getScore() - bot1.getScore();
            }
        });
        return codes;
    }
}

