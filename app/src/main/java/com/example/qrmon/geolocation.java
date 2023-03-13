package com.example.qrmon;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.Manifest;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

public class geolocation extends AppCompatActivity {

    private FusedLocationProviderClient FLClient;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private LocationRequest lRequest;
    private LocationCallback LCallback;
    private static final int RLPermission = 1;
    private EditText TBox;
    Button postButton;
    ImageView image;

    public Boolean geo;

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

        //decodePictureFile
        Bitmap decodedPicture = StringToBitMap(newQRCode.getPicture());
        image.setImageBitmap(decodedPicture);


        //This grabs location via wifi and data and GPS
        FLClient = LocationServices.getFusedLocationProviderClient(this);



        LocationRequest LRequest = LocationRequest.create();
        LRequest.setInterval(1000); // Update location every 1 seconds
        LRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //Take the location request and then outputs it to screen ( just to check if its good)
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    if (String.valueOf(latitude) != null) {
                        latitudeTextView.setText("Latitude: " + String.valueOf(latitude));
                        //longitudeTextView.setText("Longitude: " + String.valueOf(longitude));
                    }

                }
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Checks permissions and if not asks for permision otherwise it updates it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RLPermission);
        } else {
            FLClient.requestLocationUpdates(LRequest, LCallback, null);
        }

        TBox = findViewById(R.id.my_text_box);


        TBox.addTextChangedListener(new TextWatcher() {
            //Not sure if this has functionality I added when trying to make the enter button leave the textbox
            @Override
            public void beforeTextChanged(CharSequence s, int srt, int cnt, int aft) {

            }

            @Override
            public void onTextChanged(CharSequence s, int srt, int bef, int cnt) {

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
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> data = new HashMap<>();
            data.put("item", newQRCode);
            data.put("ownerId", "temp-user-id");

            // Add the data to the Firestore database
            db.collection("temp-user-id").add(newQRCode)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Item added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding item", e);
                        }
                    });

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(geolocation.this, MyCodesPage.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        }
    });

        ToggleButton tog = findViewById(R.id.on_off_toggle);
        tog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Not used now but will be used to check weither geo is on or off
            @Override
            public void onCheckedChanged(CompoundButton b, boolean On) {
                if (On) {
                    geo = true;
                } else {
                    geo = false;
                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int RCode, @NonNull String[] permish, @NonNull int[] GResults) {
        super.onRequestPermissionsResult(RCode, permish, GResults);

        //Basically we call permissions again and then update or say there is not location because we
        //Dont have permission
        if (RCode == RLPermission) {

            //If it is granted
            if (GResults.length > 0 && GResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    FLClient.requestLocationUpdates(lRequest, LCallback, null);
                }

            //If its not granted
            } else {
                latitudeTextView.setText("Location permission has been denied");
                longitudeTextView.setText("Location permission has been denied");
            }
        }
    }
    private void saveText(String text) {}

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}
