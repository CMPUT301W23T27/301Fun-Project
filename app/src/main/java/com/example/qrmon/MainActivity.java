package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/** Functionality behind interacting, sorting and viewing the data on the My Codes page
 * @author Joel Weller
 * @see ScanCodePage
 */
public class MainActivity extends AppCompatActivity {

    private final static int SCAN_ACTIVITY_REQUEST_CODE = 1;
    private CodeAdapter codeAdapter;
    private ArrayList<QRCode> codesList = new ArrayList<>();
    public ArrayList<QRCode> testList;
    Bitmap imageBitmap;
    ImageView image;

    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Intent scanIntent = new Intent(MainActivity.this, ScanCodePage.class);
                    startActivityForResult(scanIntent, SCAN_ACTIVITY_REQUEST_CODE);
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

    private void replaceFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottomNavBar, fragment );
        fragmentTransaction.commit();
    }

}
