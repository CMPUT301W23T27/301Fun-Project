package com.example.qrmon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class FriendDetailsFragment extends Fragment {

    private static final String ARG_FRIEND_USERNAME = "friend_username";

    private String friendUsername;

    private TextView fullNameTextView;
    private TextView emailTextView;
    private TextView usernameTextView;

    private Button deleteFriendButton;

    // testing
    public interface OnFriendDetailsActionListener {
        void onFriendDetailsAction(int action, String friendUsername);
    }

    private OnFriendDetailsActionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFriendDetailsActionListener) {
            mListener = (OnFriendDetailsActionListener) context;
            Log.d("TAG", "Listener attached"); // Add this line
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFriendDetailsActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public FriendDetailsFragment() {
        // Required empty public constructor
    }

    public void setFriendDetailsActionListener(OnFriendDetailsActionListener listener) {
        mListener = listener;
    }

    public static FriendDetailsFragment newInstance(String friendUsername) {
        FriendDetailsFragment fragment = new FriendDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRIEND_USERNAME, friendUsername);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            friendUsername = getArguments().getString(ARG_FRIEND_USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_detail, container, false);

        fullNameTextView = view.findViewById(R.id.friend_full_name);
        emailTextView = view.findViewById(R.id.friend_email);
        usernameTextView = view.findViewById(R.id.friend_username);

        deleteFriendButton = view.findViewById(R.id.delete_friend_button);

        deleteFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFriend();
            }
        });

        fetchFriendDetails();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // testing
                if (mListener != null) {
                    mListener.onFriendDetailsAction(0, friendUsername);
                }
                // Create a new FriendsFragment and replace the current fragment with it
                ((MainActivity)getActivity()).replaceFragment(new FriendsFragment());
            }
        });
    }


    private void fetchFriendDetails() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user-list").document(friendUsername);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String email = document.getString("Email");
                        String phone = document.getString("PhoneNumber");
                        String username = document.getString("username");
                        String fullName = document.getString("FullName");

                        fullNameTextView.setText(fullName);
                        emailTextView.setText(email);
                        usernameTextView.setText(username);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void deleteFriend() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get the current user's username from SharedPreferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String currentUserUsername = sharedPref.getString("username", "no username");

        if (currentUserUsername.equals("no username")) {
            Log.w("TAG", "No current user");
            return;
        }

        Log.d("TAG", "Deleting Friend");
        removeFriendFromCurrentUser(currentUserUsername);
    }

    private void removeFriendFromCurrentUser(String currentUserUsername) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference currentUserDocRef = db.collection("user-list").document(currentUserUsername);


        Log.d("TAG", "Current user: " + currentUserUsername + ", Friend to remove: " + friendUsername);
        currentUserDocRef.update("friends", FieldValue.arrayRemove(friendUsername))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Friend successfully removed!");
                        // Remove the friend from the list in FriendsFragment

                        if (mListener != null) {
                            mListener.onFriendDetailsAction(1, friendUsername);
                            Log.d("TAG", "Delete Intent Sent");
                        }
                        // Create a new FriendsFragment and replace the current fragment with it
                        ((MainActivity)getActivity()).replaceFragment(new FriendsFragment());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FriendsFragment friendsFragment = (FriendsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("FriendsFragment");

                        Log.w("TAG", "Error removing friend", e);
                    }
                });
    }


}
