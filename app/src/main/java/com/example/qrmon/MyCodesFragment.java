package com.example.qrmon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import static android.content.ContentValues.TAG;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button filterButton;
    private ListView codeList;

    private CodeAdapter codeAdapter;
    private ArrayList<QRCode> codesList = new ArrayList<>();
    public ArrayList<QRCode> testList;
    Bitmap imageBitmap;
    ImageView image;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.my_codes, container, false);
       filterButton = view.findViewById(R.id.myCodesFilterButton);
       codeList = view.findViewById(R.id.myCodesListView);
       codeAdapter = new CodeAdapter(this, R.layout.item_code, codesList);
       codeList.setAdapter(codeAdapter);
       testList = new ArrayList<>();

        /**
         * Retrieve code list from firestore
         */
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String ownerId = "temp-user-id";
        db.collection(ownerId)
                .whereEqualTo("geolocation", "empty")
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

                        // Do something with the list of items here
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting items with ownerId " + ownerId, e);
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
                        count = count + 1;
                    }
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