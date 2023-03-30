package com.example.qrmon;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendAdapter extends BaseAdapter {
    Context context;
    String friendsList[];
    int imagesList[];
    LayoutInflater inflater;

    public FriendAdapter(Context ctx, String[] mFriendsList, int[] mFriendsImages){
        this.context = ctx;
        this.friendsList = mFriendsList;
        this.imagesList = mFriendsImages;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return friendsList.length;
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
        view =  inflater.inflate(R.layout.friend_item, null);
        TextView txtview = view.findViewById(R.id.friendUsername);
        ImageView imgView = view.findViewById(R.id.friendVisualRepresentation);
        txtview.setText(friendsList[i]);
//        imgView.setImageResource(imagesList[i]);
        return view;
    }
}
