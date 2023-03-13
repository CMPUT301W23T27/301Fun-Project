package com.example.qrmon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * a bridge between the UI my codes component that helps fill data into a selected code
 * on my codes page
 */
public class CodeAdapter extends ArrayAdapter<QRCode> {

    private Context mContext;
    private int mResource;

    public CodeAdapter(Context context, int resource, List<QRCode> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    /**
     * When the user clicks on a single code from my codes page it sets the code image based on the code object
     * and sets the name and points of the code object
     * @author Ian H
     * @param position position on page where user clicked
     * @param convertView sets the name and points of the code object
     * @param parent The parent that this view will eventually be attached to
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QRCode code = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        ImageView codeImageView = convertView.findViewById(R.id.QRCodeVisualRepresentation);
        TextView codeNameTextView = convertView.findViewById(R.id.QRCodeName);
        TextView codePointsTextView = convertView.findViewById(R.id.QRCodePoints);

        // Set the code image based on the code object
        if (code.getVisual() != null) {
            Bitmap decodedImage = StringToBitMap(code.getVisual());
            codeImageView.setImageBitmap(decodedImage);
        } else {
            codeImageView.setImageResource(R.drawable.account_navbar_icon);
        }

        // Set the name and points of the code object
        codeNameTextView.setText(code.getName());
        codePointsTextView.setText(String.valueOf(code.getScore()));

        return convertView;
    }

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