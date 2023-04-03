package com.example.qrmon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * a bridge between the UI my leaderboard component that helps fill data into a selected code
 * on my codes page
 */
public class LeaderboardAdapter extends ArrayAdapter<LeaderboardObject> {

    private Leaderboard mContext;
    private int mResource;

    private int playerPlacementPosition = 2;

    /**
     * Constructs a new LeaderboardAdapter instance.
     *
     * @param context The context in which the adapter will be used.
     * @param resource The resource ID of the layout file that defines the UI for each item in the list.
     * @param objects The List of LeaderboardObject to be displayed in the list.
     */
    public LeaderboardAdapter(Leaderboard context, int resource, List<LeaderboardObject> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    /**
     * Returns a view for each item in the list.
     *
     * @param position The position of the item
     * @param convertView The old view
     * @param parent The parent of the view
     * @return A View corresponding the position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getCount() == 0) {
            // Return an empty view if the list is empty
            return new View(getContext());
        }

        LeaderboardObject leaderboardObject = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
        }

        ImageView codeImageView = convertView.findViewById(R.id.leaderboard_visual);
        TextView codeNameTextView = convertView.findViewById(R.id.secondColumn);
        TextView codePointsTextView = convertView.findViewById(R.id.thirdColumn);
        TextView codeRankTextView = convertView.findViewById(R.id.rank);

        //setting image
        try {
            if (leaderboardObject.getImage() != null) {
                Bitmap decodedImage = StringToBitMap(leaderboardObject.getImage());
                codeImageView.setImageBitmap(decodedImage);
            } else {
                codeImageView.setImageResource(R.drawable.account_navbar_icon);
            }
        }
        catch (Exception e){
            e.getMessage();
        }

        codeNameTextView.setText(leaderboardObject.getName());
        codePointsTextView.setText(String.valueOf(leaderboardObject.getScore()));
        codeRankTextView.setText(String.valueOf(leaderboardObject.getRank()));

        //setting the colour of the last item to differ from all the other items
        if (position == getCount() - 1) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_color));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        }

        return convertView;
    }



    /**
     * Decodes a base64 encoded string to a Bitmap image.
     *
     * @param encodedString The base64 encoded string.
     * @return The Bitmap image.
     */
    //Adapted code from https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}