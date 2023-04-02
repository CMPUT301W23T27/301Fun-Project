package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    private ArrayList<DocumentSnapshot> rawList = new ArrayList<>();
    private ArrayList<LeaderboardObject> leaderboardList = new ArrayList<>();

    private ListView leaderboardListView;

    private LeaderboardAdapter leaderboardAdapter;

    private CollectionReference playersRef;
    private CollectionReference qrCodeRef;

    private CollectionReference userqrCodeRef;

    private DocumentReference currentUserRef;

    private TextView column1;
    private TextView column2;
    private TextView column3;

    private boolean isTopPlayerBoard;

    private Button dropDownButton;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        leaderboardList.clear();

        playersRef = FirebaseFirestore.getInstance().collection("user-list");
        qrCodeRef = FirebaseFirestore.getInstance().collection("global-public-QRCodes");

        SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "no username");
        currentUserRef = playersRef.document(username);
        userqrCodeRef = currentUserRef.collection("QRcodes");

        adapterSetup();


        dropDownButton = findViewById(R.id.leaderboard_dropdown);
        dropDownButton.setOnClickListener(this::showPopupMenu);

        collectTopPlayerScores(10);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void collectTopPlayerScores(int limit) {
        leaderboardList.clear();
        isTopPlayerBoard = true;
        playersRef.orderBy("score", Query.Direction.DESCENDING).limit(limit).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("Leaderboard", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                    int count = 1;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Log.d("Leaderboard", "successfull database pull");
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("username"),
                                documentSnapshot.getLong("score").intValue(), count,
                                documentSnapshot.getString("visual")));
                        count++;
                        Log.d("Leaderboard", "username = " + documentSnapshot.getString("username"));
                        Log.d("Leaderboard", "score = " + documentSnapshot.getLong("score").intValue());
                        Log.d("Leaderboard", "rank = " + count);
                        Log.d("Leaderboard", "visual = " + documentSnapshot.getString("visual"));
                    }
                    Log.d("LeaderboardTest", "Size of leaderboard list " + leaderboardList.size());;
                    collectUserPlacement();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    private void collectTopCodes(int limit) {
        leaderboardList.clear();
        isTopPlayerBoard = false;
        Log.d("Leaderboard", "collectTopCodes run successfully");
        qrCodeRef.orderBy("score", Query.Direction.DESCENDING).limit(limit).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = 1;
                    Log.d("Leaderboard", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Log.d("Leaderboard", "successfull database pull in collectTopCodes");
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("name"),
                                documentSnapshot.getLong("score").intValue(),
                                count,
                                documentSnapshot.getString("visual")));
                        Log.d("Leaderboard", "name = " + documentSnapshot.getString("name"));
                        Log.d("Leaderboard", "score = " + documentSnapshot.getLong("score").intValue());
                        Log.d("Leaderboard", "rank = " + count);
                        Log.d("Leaderboard", "visual = " + documentSnapshot.getString("visual"));
                        count++;
                    }
                    collectCodePlacement();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    private void updateLeaderboard() {
        Log.d("LeaderboardTest", "UPDATE LEADERBOARD REACHED");
        if (isTopPlayerBoard){
            column1.setText("Top Rated");
            column2.setText("Player");
            column3.setText("Points");
        }
        else{
            column1.setText("Most Valuable");
            column2.setText("Code");
            column3.setText("Points");
        }
        leaderboardAdapter.notifyDataSetChanged();
    }

    public void collectUserPlacement() {
        currentUserRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Access the "score" field and do something with it
                    long userscore = documentSnapshot.getLong("score");
                    Log.d("LeaderboardUser", "score = " + userscore);

                    Query query = playersRef.whereGreaterThan("score", userscore).
                            orderBy("score", Query.Direction.DESCENDING);
                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            int rank = queryDocumentSnapshots.size() + 1;
                            Log.d("LeaderboardUser", "rank = " + rank);
                            leaderboardList.add(new LeaderboardObject("your rank",
                                    documentSnapshot.getLong("score").intValue(),
                                    rank,
                                    documentSnapshot.getString("visual")));
                            Log.d("LeaderboardTest", "Current user relative placement added");
                            Log.d("LeaderboardTest", "Size of leaderboard list " + leaderboardList.size());
                            updateLeaderboard();
                        }
                    });
                } else {
                    throw new RuntimeException("User score not found");
                }
            }
        });
    }

    public void collectCodePlacement() {
        userqrCodeRef.orderBy("score", Query.Direction.DESCENDING).limit(1).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        QueryDocumentSnapshot highestScoreQRCodeDoc = (QueryDocumentSnapshot) queryDocumentSnapshots.getDocuments().get(0);
                        int highestScore = highestScoreQRCodeDoc.getLong("score").intValue();
                        Log.d("LeaderboardPlacement", "User " + " highest score QR code score = " + highestScore);

                        Query query = qrCodeRef.whereGreaterThan("score", highestScore).
                                orderBy("score", Query.Direction.DESCENDING);
                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int rank = queryDocumentSnapshots.size() + 1;
                                Log.d("LeaderboardPlacement", "rank = " + rank);
                                leaderboardList.add(new LeaderboardObject(highestScoreQRCodeDoc.getString("name"),
                                        highestScore, rank, highestScoreQRCodeDoc.getString("visual")));
                                updateLeaderboard();
                            }
                        });
                    } else {
                        Log.d("LeaderboardPlacement", "User " + " has no QR codes.");
                    }
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failed to retrieve highest score QR code for user ", e);
                });
    }

    private void adapterSetup(){
        column1 = findViewById(R.id.column1Text);
        column2 = findViewById(R.id.column2Text);
        column3 = findViewById(R.id.column3Text);
        leaderboardListView = findViewById(R.id.leaderboardListView);
        leaderboardAdapter = new LeaderboardAdapter(this, R.layout.item_leaderboard, leaderboardList);
        leaderboardListView.setAdapter(leaderboardAdapter);
        backButton = findViewById(R.id.leaderboardBackButton);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.leaderboard_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.topPlayers:
                    collectTopPlayerScores(10);
                    return true;
                case R.id.topCodes:
                    collectTopCodes(10);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }
}