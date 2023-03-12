package com.example.qrmon;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCodesPage extends AppCompatActivity {

    private CodeAdapter codeAdapter;
    private ArrayList<QRCode> codesList = new ArrayList<>();
    public ArrayList<QRCode> testList;
    Bitmap imageBitmap;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_codes); //This should be my_codes_page, not scan_code

        Button filterButton = findViewById(R.id.myCodesFilterButton);

        ListView codeList = findViewById(R.id.myCodesListView);
        codeAdapter = new CodeAdapter(this, R.layout.item_code, codesList);
        codeList.setAdapter(codeAdapter);

        //image = findViewById(R.id.imageView);

        Button addCodeButton = findViewById(R.id.newCodeButton);
        testList = new ArrayList<>();


        // Retrieve QRCode list from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String ownerId = "temp-user-id";
        db.collection(ownerId)
                .whereEqualTo("geolocation", "empty")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        testList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            QRCode item = documentSnapshot.toObject(QRCode.class);
                            testList.add(item);
                            Toast.makeText(getBaseContext(), "successful from firebase",Toast.LENGTH_SHORT).show();
                        }

                        // Do something with the list of items here
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting items with ownerId " + ownerId, e);
                    }
                });






        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //decodeImageFile
                if (testList.size() != 0) {
                    //imageBitmap = StringToBitMap(testList.get(0).getVisual());
                    int count = 0;
                    while (count < testList.size()) {
                        codesList.add(testList.get(count));
                        codeAdapter.notifyDataSetChanged();
                        count = count + 1;
                    }
                }
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        //decodeImageFile
                        if (testList.size() != 0) {
                            //image.setImageBitmap(imageBitmap);
                        }
                    }}, 1000);
                }}, 1000);

        addCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send QRCode to next activity
                Intent intent = new Intent(MyCodesPage.this, ScanCodePage.class);
                startActivity(intent);
                finish();
            }
        });


        codeAdapter.notifyDataSetChanged();


        filterButton.setOnClickListener(this::showPopupMenu);

        //End of firebase stuff



        /*

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
        }*/


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

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}