package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class MyCodesPage extends AppCompatActivity {

    private CodeAdapter codeAdapter;
    private ArrayList<QRCode> codesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_codes); //This should be my_codes_page, not scan_code

        Button filterButton = findViewById(R.id.myCodesFilterButton);

        ListView codeList = findViewById(R.id.myCodesListView);
        codeAdapter = new CodeAdapter(this, R.layout.item_code, codesList);
        codeList.setAdapter(codeAdapter);

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

        codeAdapter.notifyDataSetChanged();


        filterButton.setOnClickListener(this::showPopupMenu);

    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.sorting_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sort_ascending:
                    codeAdapter.notifyDataSetChanged();
                    codesList = CodeSorting.ascendingCodeSort(codesList);
                    return true;
                case R.id.sort_descending:
                    codeAdapter.notifyDataSetChanged();
                    codesList = CodeSorting.descendingCodeSort(codesList);
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

}