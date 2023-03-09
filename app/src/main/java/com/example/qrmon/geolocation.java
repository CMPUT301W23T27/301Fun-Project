package com.example.qrmon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import android.Manifest;
import android.widget.Toast;

public class geolocation extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private EditText myTextBox;
    Button postButton;
    ImageView image;

    QRCode newQRCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_comment);

        // Receive QRCode
        if(getIntent().getExtras() != null) {
            newQRCode = (QRCode) getIntent().getParcelableExtra("QRCode");
        }

        latitudeTextView = findViewById(R.id.latitude);
        postButton = findViewById(R.id.my_rounded_button);
        image = findViewById(R.id.Image);
        image.setImageBitmap(newQRCode.getPicture());


        // Get the FusedLocationProviderClient instance
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        // Request location updates
        // Check if the app has permission to access location


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000); // Update location every 1 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    // Do something with the latitude and longitude coordinates
                    if (String.valueOf(latitude) != null) {
                        latitudeTextView.setText("Latitude: " + String.valueOf(latitude));
                        //longitudeTextView.setText("Longitude: " + String.valueOf(longitude));
                    }

                }
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }

        myTextBox = findViewById(R.id.my_text_box);

        myTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveText(s.toString());
            }
        });

    postButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Firebase stuff
            //Toast.makeText(getBaseContext(), "Firebase stuff", Toast.LENGTH_SHORT).show();

        }
    });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, request location updates
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }
                
            } else {
                // Permission denied, show an error message
                latitudeTextView.setText("Location permission denied");
                longitudeTextView.setText("Location permission denied");
            }
        }
    }
    private void saveText(String text) {}


}
