package com.example.qrmon;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

/**
 * This object represents a page within the app that occurs after the user takes a photo when posting a QR code
 * and prompts user to add their geo location and add a comment to their post
 * @author Ian M
 *
 */
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

    public Double longTude;
    public Double latTude;


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
                    longTude = location.getLongitude();
                    latTude = location.getLatitude();
                    // Set geolocation to QRCode object
                    if (String.valueOf(latitude) != null) {
                        //latitudeTextView.setText("Latitude: " + String.valueOf(latitude));

                        //newQRCode.setLongitude(null);
                        //newQRCode.setLatitude(null);
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

        ToggleButton tog = findViewById(R.id.on_off_toggle);
        geo = false;
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
    postButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String username = sharedPref.getString("username", "no username");

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Add the QRCode object to the user's database
            db.collection("user-list").document(username).collection("QRcodes").document(newQRCode.getHash()).set(newQRCode)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            // Add the QRCode object to the public database
            if (newQRCode.getLatitude() != null) {
                db.collection("global-public-QRCodes").document(newQRCode.getHash()).set(newQRCode)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding item", e);
                            }
                        });
            }
            EditText comment = findViewById(R.id.my_text_box);
            String commentValue = comment.getText().toString();
            if(commentValue != null) {
                DocumentReference docRef = db.collection("user-list").document(username).collection("QRcodes").document(newQRCode.getHash());
                Map<String, Object> User = new HashMap<>();
                String fieldNameComment = "comment";
                User.put(fieldNameComment, commentValue);
                docRef.update(User)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Comment added "))
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }

                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(geolocation.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);



        if(!geo){
            //newQRCode.setLongitude(null);
            //newQRCode.setLatitude(null);

        } else {
            newQRCode.setLongitude(longTude);
            newQRCode.setLatitude(latTude);
            Map<String, Object> User = new HashMap<>();
            User.put("latitude", latTude);
            User.put("longitude", longTude);
            DocumentReference docRef =  db.collection("user-list").document(username).collection("QRcodes").document(newQRCode.getHash());
            docRef.update(User)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
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
                //latitudeTextView.setText("Location permission has been denied");
                //longitudeTextView.setText("Location permission has been denied");
            }
        }
    }


    private void saveText(String text) {}

    /** Takes users photo captured and encodes it to bitmap for storage
     * @author Joel W
     * @param encodedString from SHAEncryptor
     * @return bitmap
     */
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
