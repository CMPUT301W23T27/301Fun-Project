package com.example.qrmon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment implements FriendDetailsFragment.OnFriendDetailsActionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> testList = new ArrayList<>(Arrays.asList("jbweller", "iharding", "mullane", "mostafa", "test"));
    private int friendImages[] = {};

    private Button addFriendsButton;
    private ListView friendsListView;

    private FriendAdapter friendAdapter;
    private ArrayList<QRCode> friendsList = new ArrayList<>();

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
        // Infla    te the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);

        addFriendsButton = view.findViewById(R.id.addFriendsButton);
        friendsListView = view.findViewById(R.id.myFriendsListView);
        friendAdapter = new FriendAdapter(getContext(), testList, friendImages );
        friendsListView.setAdapter(friendAdapter);
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


}