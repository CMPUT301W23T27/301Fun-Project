package com.example.qrmon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
//import com.google.firebase.auth.FirebaseAuth;
import android.view.View.OnFocusChangeListener;
import android.text.TextUtils;
import android.widget.TextView;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

/** Functionality behind interacting, sorting and viewing the data on the My Codes page
 * @author Joel Weller and Ian McCullough
 * @see ScanCodePage
 */
public class MainActivity extends AppCompatActivity implements FriendDetailsFragment.OnFriendDetailsActionListener {

    private final static int SCAN_ACTIVITY_REQUEST_CODE = 1;
    private CodeAdapter codeAdapter;
    private ArrayList<QRCode> codesList = new ArrayList<>();
    public ArrayList<QRCode> testList;
    Bitmap bitmapImage;
    String avString;
    String imgUrl;
    ImageView image;
    String blankEmail = "123@gmail.com";
    BottomNavigationView bnView;
    public static final String Name = "nameKey";
    public static final String pass = "passKey";
    public Boolean first_time = true;
    String ifUserHasAName;
    //public String newUserName = "bobby";
    // Get the SharedPreferences object



    @Override
    public void onFriendDetailsAction(int action, String friendUsername) {
        FriendsFragment friendsFragment = (FriendsFragment) getSupportFragmentManager().findFragmentByTag("FriendsFragment");
        if (friendsFragment != null) {
            friendsFragment.onFriendDetailsAction(action, friendUsername);
        }
    }
    /**
     * This is used to enter the app for all users. If you enter a user name
     * That is similar to another username or null it will prevent you from entering
     * if your username is unique then it will allow you to access the app and
     * remeber you locally for the next time you enter
     * (except TestUser which is used to test QRcodes on emulators)
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        // Save a value
        ifUserHasAName = sharedPref.getString("username", "no username");


        //If you have logged in before you will not go to the login page
        if (ifUserHasAName.equals("no username")){
            setContentView(R.layout.login_page);
            FirebaseApp.initializeApp(this);
            EditText usernameInput = findViewById(R.id.Username);


            Button entering = findViewById(R.id.enter);
            //Basically when you click this button thats how it knows your done typing
            //Then if what you have typed is not empty it will create a collection baised on that input
            entering.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //im just initalizing things here
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference usersRef = db.collection("user-list");
                    Query query = usersRef.orderBy("username");
                    Map<String, Object> Newuser = new HashMap<>();
                    String newUserName = usernameInput.getText().toString();
                    List<String> myList = new ArrayList<>();
                    SharedPreferences.Editor editingTool = sharedPref.edit();

                    query.get().addOnCompleteListener(task -> {
                        //This checks all other users to see if someone has the same username
                        if (task.isSuccessful()) {

                            boolean docBool = false;
                            for (QueryDocumentSnapshot docu : task.getResult()) {
                                String doc = docu.getString("username");
                                if (doc.equals(newUserName)) {
                                    // A document with the same name already exists
                                    docBool = true;
                                    break;
                                }
                            }

                            //bascially if the username is non empty and unqiue I create a new user and let you through
                            if (!TextUtils.isEmpty(newUserName) && !docBool) {
                                AvatarMaker am = new AvatarMaker();
                                imgUrl = am.interpret(newUserName);
                                Log.d("Here in Mainactivity", imgUrl);
                                new LoadImage().execute();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Log.d("Here In run", "bitmap is used");
                                        avString = BitMapToString(bitmapImage);
                                        editingTool.putString("username", newUserName);
                                        editingTool.apply();
                                        Newuser.put("username", newUserName);
                                        Newuser.put("AccountScore","0");
                                        Newuser.put("FullName","");
                                        Newuser.put("PhoneNumber", "");
                                        Newuser.put("Email", "");
                                        Newuser.put("friends", myList);
                                        Newuser.put("avatar", avString);
                                        Newuser.put("score", 0);


                                        db.collection("user-list")
                                                .document(newUserName)
                                                .set(Newuser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    //This is for the sub collection in the new user
                                                    public void onSuccess(Void aVoid) {
                                                        //db.collection("user-list")
                                                        //.document(newUserName)
                                                        //.collection("QRcodes")
                                                        //.document("first-QR")
                                                        //.set(new HashMap<String, Object>());

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                        ifUserHasAName = newUserName;
                                        appStart();
                                    }
                                 }, 2000);

                            }
                            else if (newUserName.equals("TestUser")) {
                                editingTool.putString("username", newUserName);
                                editingTool.apply();
                                ifUserHasAName = newUserName;
                                appStart();
                            }
                            else {
                                TextView pleaseEnter = findViewById(R.id.Please);
                                pleaseEnter.setText("Please enter a unique non empty username");
                            }
                        } else {
                            //Intent intent = new Intent(MainActivity.this, MainActivitynew.class);
                            //startActivity(intent);

                        }
                    });
                }
            });



        }else {
            // this was here before and it gets to here if you have already logged in before
            appStart();
        }


    }

    public void replaceFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottomNavBar, fragment );
        fragmentTransaction.commit();
    }

    public void appStart() {
        setContentView(R.layout.activity_main); //This should be my_codes_page, not scan_code


        replaceFragment(new MyCodesFragment());
        bnView = findViewById(R.id.bottomNavView);
        bnView.setSelectedItemId(R.id.my_codes);
        bnView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.my_codes:
                    replaceFragment(new MyCodesFragment());
                    break;

                case R.id.add_button:
                    if (ifUserHasAName.equals("TestUser")) {
                        Intent in = new Intent(MainActivity.this, CodeInterpreterActivity.class);
                        in.putExtra("scanned_code", "99999999999999999999999999");
                        startActivity(in);
                    } else {
                        Intent scanIntent = new Intent(MainActivity.this, ScanCodePage.class);
                        startActivityForResult(scanIntent, SCAN_ACTIVITY_REQUEST_CODE);
                    }
                    break;

                case R.id.account_settings:
                    replaceFragment(new ProfileFragment());
                    break;

                case R.id.message:
                    replaceFragment(new FriendsFragment());
                    break;

                case R.id.maps:
                    replaceFragment(new MapsFragment());
            }

            return true;
        });
    }

    public class LoadImage extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(imgUrl).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                //newQRCode.setVisual(bitmap);
                bitmapImage = bitmap;
                Log.d("Here In onPost2", "bitmap is saved");
                //progress.dismiss();
            } else {
            }
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
