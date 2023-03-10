package com.example.qrmon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CodeAdapter extends ArrayAdapter<QRCode> {

    private Context mContext;
    private int mResource;

    public CodeAdapter(Context context, int resource, List<QRCode> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

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
        if (code.getPicture() != null) {
            codeImageView.setImageBitmap(code.getPicture());
        } else {
            codeImageView.setImageResource(R.drawable.account_navbar_icon);
        }

        // Set the name and points of the code object
        codeNameTextView.setText(code.getName());
        codePointsTextView.setText(String.valueOf(code.getScore()));

        return convertView;
    }
}