package com.example.qrmon;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button confirmButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    /**
     * This is used to update the setting to firestore and grab the
     *current settings from firestore
     * author is Ian McCullough (mostly)
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account_settings_page, container, false);
        TextView userNameForXML = view.findViewById(R.id.username_value);
        confirmButton = view.findViewById(R.id.save_changes_btn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collectionPath = "user-list";
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String CurrentUserName = sharedPref.getString("username", "no username");
        String documentId = CurrentUserName;
        DocumentReference docRef = db.collection("user-list").document(documentId);
        EditText emailValueEdit = view.findViewById(R.id.email_value_edit);
        EditText phoneValueEdit = view.findViewById(R.id.phone_value_edit);
        EditText nameValueEdit = view.findViewById(R.id.name_value_edit);
        TextView pointsvalueText= view.findViewById(R.id.points_textview);
        ImageView image = view.findViewById(R.id.avatar_image);
        String fieldNameEmail = "Email";
        String fieldNamePhone = "PhoneNumber";
        String fieldNameName = "FullName";
        String fieldNamePoints = "score";
        String fieldNameAvatar = "avatar";
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String EmailValueOne = document.getString(fieldNameEmail);
                        String PhoneValueOne = document.getString(fieldNamePhone);
                        String NameValueOne = document.getString(fieldNameName);
                        Long pointsValueOne = (Long) document.get(fieldNamePoints);
                        String avatarValueOne = document.getString(fieldNameAvatar);
                        Bitmap bitmap = StringToBitMap(avatarValueOne);
                        image.setImageBitmap(bitmap);
                        emailValueEdit.setText(EmailValueOne);
                        phoneValueEdit.setText(PhoneValueOne);
                        nameValueEdit.setText(NameValueOne);
                        pointsvalueText.setText(new StringBuilder().append("Points: ").append(Long.toString(pointsValueOne)).toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        userNameForXML.setText(CurrentUserName);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() called");
                String emailValue = emailValueEdit.getText().toString();
                String phoneValue = phoneValueEdit.getText().toString();
                String nameValue = nameValueEdit.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String collectionPath = "user-list";
                String documentId = CurrentUserName;
                String fieldNamePhone = "PhoneNumber";
                String fieldNameName = "FullName";
                DocumentReference docRef = db.collection("user-list").document(documentId);
                String fieldNameEmail = "Email";

// Create a Map with the field and its new value
                Map<String, Object> User = new HashMap<>();
                User.put(fieldNamePhone, phoneValue);
                User.put(fieldNameEmail, emailValue);
                User.put(fieldNameName, nameValue);
                docRef.update(User)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });
        return view;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}