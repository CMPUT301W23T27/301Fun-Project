package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        leaderboardListView = findViewById(R.id.leaderboardListView);
        leaderboardAdapter = new LeaderboardAdapter(this, R.layout.item_leaderboard, leaderboardList);
        leaderboardListView.setAdapter(leaderboardAdapter);



        //collectTopPlayerScores(10);
        collectTopCodes(10);
        Log.d("Leaderboard", "leaderboard running");

    }

    private void testList(){
        Log.d("Leaderboard", "testList called");
        if (rawList.size() >= 2) {
            String name = rawList.get(1).getString("Name");
        } else {
            System.out.println("");
        }

        if (leaderboardList.size() >= 2) {
            String name = leaderboardList.get(1).getName();
        } else {
            Log.d("Leaderboard", "leaderboard list empty");
        }
    }


    private void collectTopPlayerScores(int limit){
        CollectionReference playersRef = FirebaseFirestore.getInstance().collection("user-list");
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
                    testList();
                    updateLeaderboard();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    private void collectTopCodes(int limit){
        Log.d("Leaderboard", "collectTopCodes run successfully");
        CollectionReference qrCodeRef = FirebaseFirestore.getInstance().collection("global-public-QRCodes");
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
                    testList();
                    updateLeaderboard();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    private void updateLeaderboard(){
        Log.d("Leaderboard", "UPDATE LEADERBOARD REACHED");
        leaderboardAdapter.notifyDataSetChanged();
    }

}