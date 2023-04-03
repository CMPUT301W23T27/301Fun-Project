package com.example.qrmon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends BaseAdapter {
    Context context;
    List<String> friendsList;
    int[] imagesList;
    LayoutInflater inflater;

    public FriendAdapter(Context ctx, List<String> mFriendsList, int[] mFriendsImages) {
        this.context = ctx;
        this.friendsList = mFriendsList;
        this.imagesList = mFriendsImages;
        inflater = LayoutInflater.from(ctx);
    }

    public FriendAdapter(Context context, ArrayList<QRCode> friendsList) {
    }

    @Override
    public int getCount() {
        if(friendsList==null) return 0;
        return friendsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.friend_item, null);
        TextView txtview = view.findViewById(R.id.friendUsername);
        ImageView imgView = view.findViewById(R.id.friendVisualRepresentation);
        txtview.setText(friendsList.get(i));
        // imgView.setImageResource(imagesList[i]);
        return view;
    }
}
