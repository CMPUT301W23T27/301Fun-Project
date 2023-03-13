package com.example.qrmon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class PhotoPage extends AppCompatActivity {

    QRCode newQRCode;

    public static final int PHOTO_PERMISSION_CODE = 301;
    public static final int CAM_REQUEST_CODE = 222;
    private ImageView photoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_page);

        // Receive QRCode
        if(getIntent().getExtras() != null) {
            newQRCode = (QRCode) getIntent().getParcelableExtra("QRCode");
        }



        photoImage = findViewById(R.id.photoPreview);
        Button retakePhoto = findViewById(R.id.retakePhotoButton);
        Button backButton = findViewById(R.id.backButton);
        Button confirmButton = findViewById(R.id.confirmPhotoButton);

        cameraPermissionCheck();

        retakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPermissionCheck();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQRCode.setPicture(null);
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send QRCode to next activity
                Intent intent = new Intent(PhotoPage.this, geolocation.class);
                intent.putExtra("QRCode", newQRCode);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cameraPermissionCheck(){
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PHOTO_PERMISSION_CODE);
        }
        else{
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PHOTO_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Enable Camera Permission To Use This Feature",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Bitmap takenPhoto = (Bitmap) data.getExtras().get("data");
            photoImage.setImageBitmap(takenPhoto);
            String imageEncoded = BitMapToString(takenPhoto);
            newQRCode.setPicture(imageEncoded);
        }
    }

    //Adapted code from https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}