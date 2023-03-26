package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        collectTopScores(10);

    }

    private void testList(){
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
            System.out.println("There are not enough players in the list to access the second element");
        }
    }


    private void collectTopScores(int limit){
        CollectionReference playersRef = FirebaseFirestore.getInstance().collection("leaderboard-testing");
        playersRef.orderBy("Score", Query.Direction.DESCENDING).limit(limit).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println("Name: " + documentSnapshot.get("Name"));
                        System.out.println("Score: " + documentSnapshot.get("Score"));
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("Name"), documentSnapshot.getLong("Score").intValue()));
                    }
                    testList();
                    updateLeaderboard();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    private void collectTopCodes(){

    }

    private void updateLeaderboard(){
        //TODO: once adapter is built, update listView with values
    }

}