package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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