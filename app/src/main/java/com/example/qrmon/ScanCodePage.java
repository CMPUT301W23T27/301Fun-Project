package com.example.qrmon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.security.NoSuchAlgorithmException;

/** This page opens and has access to device camera and prompts user to scan a QR Code
 * @author Martin M
 */
public class ScanCodePage extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private TextView scannedText;
    private String hashedCode;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_code);

        scanCode();
    }

    private void scanCode(){
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannedText = findViewById(R.id.scanned_text);
        setupPermissions();
        mCodeScanner = new CodeScanner(this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            hashedCode = SHAEncryptor.getSHA256Hash(result.getText());
                            System.out.println("here: " + hashedCode);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                        scannedText.setText(result.getText());
                        Intent in = new Intent(getApplicationContext(), CodeInterpreterActivity.class);
                        in.putExtra("scanned_code", hashedCode);
                        startActivity(in);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void setupPermissions() {
//        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

        }
    }

}