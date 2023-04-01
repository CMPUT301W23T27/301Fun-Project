package com.example.qrmon;

import static android.content.ContentValues.TAG;
import android.content.pm.PackageManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import static android.content.ContentValues.TAG;

import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.content.ContextWrapper;




import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCodesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCodesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RLPermission = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button filterButton;
    private Button deleteButton;
    private ListView codeList;

    private CodeAdapter codeAdapter;
    private ArrayList<QRCode> codesList = new ArrayList<>();

    private int totalScore;
    int pos;
    public ArrayList<QRCode> testList;
    Bitmap imageBitmap;
    ImageView image;
    public QRCode deleteCode;


    public MyCodesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCodesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCodesFragment newInstance(String param1, String param2) {
        MyCodesFragment fragment = new MyCodesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.my_codes, container, false);
       filterButton = view.findViewById(R.id.myCodesFilterButton);
       deleteButton = view.findViewById(R.id.myCodesDeleteButton);
       codeList = view.findViewById(R.id.myCodesListView);
       codeAdapter = new CodeAdapter(this, R.layout.item_code, codesList);
       codeList.setAdapter(codeAdapter);
       testList = new ArrayList<>();
       pos = -1;

        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db1.collection("temp-user-id");

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Checks permissions and if not asks for permision otherwise it updates it
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RLPermission);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == -1){
                    return;
                }
                // accesses objects by getting object, then getting objects name
                String code_document = ((QRCode)codeList.getItemAtPosition(pos)).getHash();
                // The set method sets a unique id for the document
                SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String username = sharedPref.getString("username", "no username");
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("user-list").document(username).collection("QRcodes")
                        .document(code_document)
                        // .whereEqualTo("name", ((QRCode) codeList.getItemAtPosition(pos)).getName())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // These are a method which gets executed when the task is succeeded
                                Log.d(TAG,"Data has been deleted successfully!");
                                codeAdapter.notifyDataSetChanged();
                                ((MainActivity)getActivity()).replaceFragment(new MyCodesFragment());


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {
                                // These are a method which gets executed if there’s any problem
                                Log.d(TAG," Data could not be deleted" + e.toString());
                            }
                        });
                pos = -1;

            }
        });

        codeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                deleteCode = (QRCode)adapterView.getItemAtPosition(i);
            }
        });


       //TODO: change currentUser to reflect who ever is using app
       String currentUser = "Joel";

        /**
         * Retrieve code list from firestore
         *
         */
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "no username");

        db.collection("user-list").document(username).collection("QRcodes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        testList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            QRCode item = documentSnapshot.toObject(QRCode.class);
                            testList.add(item);
//                            Toast.makeText(getContext(), "successful from firebase",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting items with ownerId " + username, e);
                    }
                });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //decodeImageFile
                if (testList.size() != 0) {
                    //imageBitmap = StringToBitMap(testList.get(0).getVisual());
                    int count = 0;
                    while (count < testList.size()) {
                        codesList.add(testList.get(count));
                        codeAdapter.notifyDataSetChanged();
                        // totalScore += testList.get(count).getScore();
                        count = count + 1;
                    }
                    //Calls firebaseScoreUpdater with sum of scores
                    Map<String, Object> totalScoreMap = new HashMap<>();
                    totalScoreMap.put("score", totalScore);
                    FirebaseScoreUpdater firebaseScoreUpdater = new FirebaseScoreUpdater();
                    firebaseScoreUpdater.updateFirebaseScore(totalScoreMap);
                }
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        //decodeImageFile
                        if (testList.size() != 0) {
                            //image.setImageBitmap(imageBitmap);
                        }
                    }}, 1000);
            }}, 1000);


        codeAdapter.notifyDataSetChanged();

        filterButton.setOnClickListener(this::showPopupMenu);

        return view;
    }

    /** When you click on the filter button - a popup menu will display to sort the users codes
     * @author Ian Harding
     * @param view
     * @return Boolean
     */
    private void showPopupMenu(View view) {
        //Toast here for debugging
        Toast.makeText(getContext(), "Total Score = " + totalScore,Toast.LENGTH_SHORT).show();
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.sorting_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sort_ascending:
                    codeAdapter.notifyDataSetChanged();
                    codesList = CodeSorting.ascendingCodeSort(codesList);
                    return true;
                case R.id.sort_descending:
                    codeAdapter.notifyDataSetChanged();
                    codesList = CodeSorting.descendingCodeSort(codesList);
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    /**
     * Takes encoded QR code string and converts it to bitmap for representing the QR Code as an image
     * @author Joel Weller
     * @param encodedString from SHAEncryptor
     * @return bitmap
     */

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}