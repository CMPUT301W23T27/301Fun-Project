package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MyCodesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_code); //This should be my_codes_page, not scan_code
        //TODO: Change ContentView to relevant xml when it exists

        ArrayList<QRCode> codesList = new ArrayList<>();
        codesList.add(new QRCode("bot1", null, null, null, null, null, null,  10)); //Any of the values can be null for testing
        codesList.add(new QRCode("bot2",null, null, null, null, null, null, 12));
        codesList.add(new QRCode("bot3",null, null, null, null, null, null, 17));
        codesList.add(new QRCode("bot4",null, null, null, null, null, null, 23));
        codesList.add(new QRCode("bot5",null, null, null, null, null, null, 2));
        codesList.add(new QRCode("bot6",null, null, null, null, null, null, 15));

        for (QRCode code : codesList) {
            System.out.println(code.getScore());
        }

        codesList = CodeSorting.descendingCodeSort(codesList);

        System.out.println("-------------------");

        for (QRCode code : codesList) {
            System.out.println(code.getScore());
        }

        codesList = CodeSorting.ascendingCodeSort(codesList);

        System.out.println("---------------------");

        for (QRCode code : codesList) {
            System.out.println(code.getScore());
        }

    }
}