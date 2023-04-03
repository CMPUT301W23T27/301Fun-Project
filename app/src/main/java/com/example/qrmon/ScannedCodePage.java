package com.example.qrmon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Once the user scans the QRCode, it will show the user which creature they got
 * due to each QR code having unique attributes
 *
 * This page allows the user to add a picture or click next while adding comment and/or geolocation
 * @author Joel Weller
 */
public class ScannedCodePage extends AppCompatActivity {

    QRCode newQRCode;
    private ImageView imageView;
    private TextView scoreView;
    private TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_code_page);

        //Assign Views
        imageView = findViewById(R.id.monster);
        scoreView = findViewById(R.id.score);
        nameView = findViewById(R.id.name);
        Button takePhotoButton = findViewById(R.id.snapAPicButton);
        Button skipPhotoButton = findViewById(R.id.scannedCodeNextButton);

        // Receive QRCode
        if(getIntent().getExtras() != null) {
            newQRCode = (QRCode) getIntent().getParcelableExtra("QRCode");

            //decodeImageFile
            Bitmap image = StringToBitMap(newQRCode.getVisual());

            // Set QRCode values
            imageView.setImageBitmap(image);
            scoreView.setText(Integer.toString(newQRCode.getScore()));
            nameView.setText(newQRCode.getName());

        }

        SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "no username");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("user-list").document(username).collection("QRcodes").document(newQRCode.getHash());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // QRCode exists
                        Toast.makeText(getApplicationContext(), "You have already scanned this code!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScannedCodePage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // QRCode does not exist
                        DocumentReference docRef2 = db.collection("global-public-QRCodes").document(newQRCode.getHash());

                        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // QRCode exists
                                        Toast.makeText(getApplicationContext(), "Other people have found this QRCode as well!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // QRCode does not exist
                                        Toast.makeText(getApplicationContext(), "You are the first one to find this QRCode!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Error getting document
                                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    // Error getting document
                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                }
            }
        });




        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send QRCode to next activity
                Intent intent = new Intent(ScannedCodePage.this, PhotoPage.class);
                intent.putExtra("QRCode", newQRCode);
                startActivity(intent);
            }
        });

        skipPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScannedCodePage.this, geolocation.class);
                intent.putExtra("QRCode", newQRCode);
                startActivity(intent);
                finish();
            }
        });


    }

    //Adapted code from https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}