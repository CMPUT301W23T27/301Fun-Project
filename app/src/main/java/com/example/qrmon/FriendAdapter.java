package com.example.qrmon;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends BaseAdapter {

    Bitmap bitmapImage;
    Context context;
    List<String> friendsList;
    int[] imagesList;
    LayoutInflater inflater;

    public FriendAdapter(Context ctx, List<String> mFriendsList, int[] mFriendsImages) {
        this.context = ctx;
        this.friendsList = mFriendsList;
        this.imagesList = mFriendsImages;
        inflater = LayoutInflater.from(ctx);
    }

    public FriendAdapter(Context context, ArrayList<QRCode> friendsList) {
    }

    @Override
    public int getCount() {
        if(friendsList==null) return 0;
        return friendsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.friend_item, null);
        TextView txtview = view.findViewById(R.id.friendUsername);
        ImageView imgView = view.findViewById(R.id.friendVisualRepresentation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

        txtview.setText(friendsList.get(i));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference currUserDocRef = db.collection("user-list").document(friendsList.get(i));

        currUserDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String avatarString = (String) documentSnapshot.get("avatar");
                            bitmapImage = StringToBitMap(avatarString);
                            imgView.setImageBitmap(bitmapImage);

                        }
                        else {
                            Toast.makeText(context, "Document not found1", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to fetch data ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 2000);


        return view;
    }

    //Adapted code from https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
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
