package com.example.qrmon;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the friends page or fragment in which users can see their friends
 * add friends and delete friends from a firebase database
 * Each user has a unique profile picture
 * @Author Martin, Grace, Joel
 */
public class FriendsFragment extends Fragment implements FriendDetailsFragment.OnFriendDetailsActionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private EditText searchFriends;
    private String mParam2;

    private ArrayList<String> testList = new ArrayList<>(Arrays.asList("jbweller", "iharding", "mullane", "mostafa", "test"));

    private ArrayList<String> friendsList1 = new ArrayList<>();
    private int friendImages[] = {};

    private Button addFriendsButton;

    private Button leaderboardsButton;
    private ListView friendsListView;

    private FriendAdapter friendAdapter;
    public ArrayList<QRCode> friendsList = new ArrayList<>();

    private int totalScore;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);

        addFriendsButton = view.findViewById(R.id.addFriendsButton);
        leaderboardsButton = view.findViewById(R.id.leaderboardsButton);
        friendsListView = view.findViewById(R.id.myFriendsListView);

        searchFriends = view.findViewById(R.id.searchFriends);

        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String friendUsername = searchFriends.getText().toString();
                if (friendUsername.isEmpty()) {
                    // Show an error message if the search bar is empty
                    Toast.makeText(getContext(), "Please enter a username to search for", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    DocumentReference userListRef = db.collection("user-list").document(friendUsername);
                    userListRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()){
                                            addFriend(friendUsername);
                                            fetchFriends();
                                    }
                                    else {
                                        Toast.makeText(getContext(), "Document not found", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to fetch data ", Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }
        });


        leaderboardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to execute when the button is clicked
                Intent intent = new Intent(getActivity(), Leaderboard.class);
                startActivity(intent);
            }
        });

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedFriend = testList.get(i);

                FriendDetailsFragment friendDetailsFragment = FriendDetailsFragment.newInstance(clickedFriend);
                friendDetailsFragment.setFriendDetailsActionListener(FriendsFragment.this);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(FriendsFragment.this);
                fragmentTransaction.add(R.id.fragment_container, friendDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        searchFriends = view.findViewById(R.id.searchFriends);

        return view;
    }

    public void removeFriendFromList(String friendUsername) {
        int index = -1;
        for (int i = 0; i < testList.size(); i++) {
            if (testList.get(i).equals(friendUsername)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            testList.remove(index);
            friendAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFriendDetailsAction(int action, String friendUsername) {
        Log.d("TAG", "Testing");
        if (action == 1) {
            // Handle "Delete" button action
            Log.d("TAG", "Delete Intent Received.");
            removeFriendFromList(friendUsername);
        }
    }

    private void fetchFriends(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "no username");
        DocumentReference currUserDocRef = db.collection("user-list").document(username);
        currUserDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    friendsList1 = (ArrayList<String>) documentSnapshot.get("friends");
                    friendAdapter = new FriendAdapter(getContext(), friendsList1, friendImages );
                    friendsListView.setAdapter(friendAdapter);
                    friendAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getContext(), "Document not found", Toast.LENGTH_LONG).show();
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(getContext(), "Failed to fetch data ", Toast.LENGTH_LONG).show();
          }
        });
    }

    private void addFriend(String friend){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "no username");
        DocumentReference currUserDocRef = db.collection("user-list").document(username);
        currUserDocRef.update("friends", FieldValue.arrayUnion(friend));
    }



}