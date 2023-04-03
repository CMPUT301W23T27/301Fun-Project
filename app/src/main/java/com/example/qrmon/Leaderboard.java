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

/**
 * displays a list of users or codes with the highest scores
 */
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

    /**
     * initializes the activity. clears the arrayList, retrieves username, connects to firebase
     * and sets up the popup menu
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * collects the top players by score from firebase
     * @param limit the number of elements to be placed in the leaderboard
     */
    private void collectTopPlayerScores(int limit) {
        leaderboardList.clear();
        runOnUiThread(() -> {
            leaderboardAdapter.notifyDataSetChanged();
        });
        isTopPlayerBoard = true;
        playersRef.orderBy("score", Query.Direction.DESCENDING).limit(limit).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = 1;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("username"),
                                documentSnapshot.getLong("score").intValue(), count,
                                documentSnapshot.getString("avatar")));
                        count++;
                    }
                    collectUserPlacement();
                    runOnUiThread(() -> {
                        leaderboardAdapter.notifyDataSetChanged();
                    });
                    updateLeaderboard();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    /**
     * collects the top codes from firebase by score
     * @param limit the number of elements to be placed in the scoreboard
     */
    private void collectTopCodes(int limit) {
        leaderboardList.clear();
        runOnUiThread(() -> {
            leaderboardAdapter.notifyDataSetChanged();
        });
        isTopPlayerBoard = false;
        qrCodeRef.orderBy("score", Query.Direction.DESCENDING).limit(limit).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = 1;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        leaderboardList.add(new LeaderboardObject(documentSnapshot.getString("name"),
                                documentSnapshot.getLong("score").intValue(),
                                count,
                                documentSnapshot.getString("visual")));
                        count++;
                    }
                    collectCodePlacement();
                    runOnUiThread(() -> {
                        leaderboardAdapter.notifyDataSetChanged();
                    });
                    updateLeaderboard();
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failure to retrieve score from firebase");
                });
    }

    /**
     * updates the adapter and changes the column titles to reflect the new leaderboard
     */
    private void updateLeaderboard() {
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
    }

    /**
     * collects data on the users relative placement to be put at the end of the list
     */
    public void collectUserPlacement() {
        currentUserRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Access the "score" field and do something with it
                    long userscore = documentSnapshot.getLong("score");
                    Query query = playersRef.whereGreaterThan("score", userscore).
                            orderBy("score", Query.Direction.DESCENDING);
                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            int rank = queryDocumentSnapshots.size() + 1;
                            leaderboardList.add(new LeaderboardObject("your rank",
                                    documentSnapshot.getLong("score").intValue(),
                                    rank,
                                    documentSnapshot.getString("visual")));
                            runOnUiThread(() -> {
                                leaderboardAdapter.notifyDataSetChanged();
                            });
                        }
                    });
                } else {
                    throw new RuntimeException("User score not found");
                }
            }
        });
    }

    /**
     * finds the users highest scoring code and shows its ranking relative to the top codes
     */
    public void collectCodePlacement() {
        userqrCodeRef.orderBy("score", Query.Direction.DESCENDING).limit(1).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        QueryDocumentSnapshot highestScoreQRCodeDoc = (QueryDocumentSnapshot) queryDocumentSnapshots.getDocuments().get(0);
                        int highestScore = highestScoreQRCodeDoc.getLong("score").intValue();

                        Query query = qrCodeRef.whereGreaterThan("score", highestScore).
                                orderBy("score", Query.Direction.DESCENDING);
                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int rank = queryDocumentSnapshots.size() + 1;
                                leaderboardList.add(new LeaderboardObject(highestScoreQRCodeDoc.getString("name"),
                                        highestScore, rank, highestScoreQRCodeDoc.getString("visual")));
                                runOnUiThread(() -> {
                                    leaderboardAdapter.notifyDataSetChanged();
                                });
                            }
                        });
                    } else {
                    }
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failed to retrieve highest score QR code for user ", e);
                });
    }

    /**
     * this connects buttons, views, and adapters to their xml counterparts.
     */
    private void adapterSetup(){
        column1 = findViewById(R.id.column1Text);
        column2 = findViewById(R.id.column2Text);
        column3 = findViewById(R.id.column3Text);
        leaderboardListView = findViewById(R.id.leaderboardListView);
        leaderboardAdapter = new LeaderboardAdapter(this, R.layout.item_leaderboard, leaderboardList);
        leaderboardListView.setAdapter(leaderboardAdapter);
        backButton = findViewById(R.id.leaderboardBackButton);
    }

    /**
     * a pop up menu that allows the player to choose between viewing the different leaderboard
     * options
     * @param view
     */
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