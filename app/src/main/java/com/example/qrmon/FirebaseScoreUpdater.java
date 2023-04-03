package com.example.qrmon;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

public class FirebaseScoreUpdater {
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();

    //TODO: Make current user accurately reflect who is currently using the app
    private String currentUser = "Joel";


    /**
     * Updates the score on firebase of a specified user.
     *
     * @author Ian Harding
     * @param score
     */
    public void updateFirebaseScore(Map<String, Object> score){

        DocumentReference userDocRef = db.collection("user-list").document(currentUser);

        userDocRef.update(score)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User score updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding total score", e);
                    }
                });
    }
}
