package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import android.widget.ListView;
import android.widget.SearchView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * unit test for the performSearch() method:
 */

public class FriendsFragmentTest {

    FriendsFragment friendsFragment;
    ListView friendsListView;
    SearchView searchFriends;
    FriendAdapter friendAdapter;

    @Before
    public void setUp() throws Exception {
        friendsFragment = new FriendsFragment();
        friendsListView = new ListView(friendsFragment.getContext());
        searchFriends = new SearchView(friendsFragment.getContext());
        friendAdapter = new FriendAdapter(friendsFragment.getContext(), friendsFragment.friendsList);
    }

    @Test
    public void performSearch_withMatchingQuery_updatesAdapter() {
        // Given a list of friends and a search query
        friendsFragment.friendsList = new ArrayList<>(
                Arrays.asList(
                        new QRCode("friend1", null, null, null, null, null, null, null, null),
                        new QRCode("friend2", null, null, null, null, null, null, null, null),
                        new QRCode("friend3", null, null, null, null, null, null, null, null)
                )
        );
        String query = "friend2";

        // When performSearch() is called with the query
        friendsFragment.performSearch(query);

        // Then the adapter should be updated with a list containing the matching friend
        ArrayList<QRCode> expectedList = new ArrayList<>(Arrays.asList(new QRCode("friend2", null, null, null, null, null, null, null, null)));
        assertEquals(expectedList, friendsFragment.friendsList);
        assertEquals(expectedList.size(), friendAdapter.getCount());
    }

    @Test
    public void performSearch_withNonMatchingQuery_updatesAdapterWithEmptyList() {
        // Given a list of friends and a search query
        friendsFragment.friendsList = new ArrayList<>(
                Arrays.asList(
                        new QRCode("friend1", null, null, null, null, null, null, null, null),
                        new QRCode("friend2", null, null, null, null, null, null, null, null),
                        new QRCode("friend3", null, null, null, null, null, null, null, null)
                )
        );
        String query = "non-matching-query";

        // When performSearch() is called with the query
        friendsFragment.performSearch(query);

        // Then the adapter should be updated with an empty list
        ArrayList<QRCode> expectedList = new ArrayList<>();
        assertEquals(expectedList, friendsFragment.friendsList);
        assertEquals(expectedList.size(), friendAdapter.getCount());
    }

}
