package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

    ArrayList<DocumentSnapshot> rawList = new ArrayList<>();
    ArrayList<LeaderboardObject> leaderboardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        collectTopCodes(10);
        Log.d("Leaderboard", "leaderboard running");

    }

    private void testList(){
        Log.d("Leaderboard", "testList called");
        if (rawList.size() >= 2) {
            String name = rawList.get(1).getString("Name");
            System.out.println("The name of the second player is: " + name);
        } else {
            System.out.println("There are not enough players in the list to access the second element");
        }

        if (leaderboardList.size() >= 2) {
            String name = leaderboardList.get(1).getName();
            System.out.println("The name of the second player is: " + name);
        } else {
            Log.d("Leaderboard", "leaderboard list empty");
        }
    }


    private void collectTopPlayerScores(int limit){
        CollectionReference playersRef = FirebaseFirestore.getInstance().collection("leaderboard-testing");
        playersRef.orderBy("Score", Query.Direction.DESCENDING).limit(limit).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Log.d("Leaderboard", "successfull database pull");
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("Name"), documentSnapshot.getLong("Score").intValue()));
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
                    Log.d("Leaderboard", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Log.d("Leaderboard", "successfull database pull in collectTopCodes");
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("name"), documentSnapshot.getLong("score").intValue()));
                        Log.d("Leaderboard", "name = " + documentSnapshot.getString("name"));
                        Log.d("Leaderboard", "score = " + documentSnapshot.getLong("score").intValue());
                    }
                    testList();
                    updateLeaderboard();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    private void updateLeaderboard(){
        //TODO: once adapter is built, update listView with values
    }

}