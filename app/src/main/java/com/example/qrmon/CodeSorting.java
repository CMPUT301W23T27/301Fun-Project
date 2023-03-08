package com.example.qrmon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CodeSorting {

    public static ArrayList<QRCode> ascendingCodeSort (ArrayList<QRCode> codes){
        Collections.sort(codes, new Comparator<QRCode>() {
            @Override
            public int compare(QRCode bot1, QRCode bot2) {
                return bot1.getScore() - bot2.getScore();
            }
        });
        return codes;
    }


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

