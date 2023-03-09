package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ScannedCodePage extends AppCompatActivity {

    QRCode newQRCode;
    private ImageView imageView;
    private TextView scoreView;
    private TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_code_page);

        // Receive QRCode
        if(getIntent().getExtras() != null) {
            newQRCode = (QRCode) getIntent().getParcelableExtra("QRCode");
        }

        //Assign Views
        imageView = findViewById(R.id.monster);
        scoreView = findViewById(R.id.score);
        nameView = findViewById(R.id.name);

        // Set QRCode values
        imageView.setImageBitmap(newQRCode.getVisual());
        scoreView.setText(Integer.toString(newQRCode.getScore()));
        nameView.setText(newQRCode.getName());




    }
}