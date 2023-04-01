package com.example.qrmon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StatAdapter extends ArrayAdapter<StatObject> {

        private MyCodesFragment mContext;
        private int mResource;

        public StatAdapter(MyCodesFragment context, int resource, List<StatObject> objects) {
            super(context.getContext(), resource, objects);
            mContext = context;
            mResource = resource;
        }

        /**
         * When the user clicks on a single code from my codes page it sets the code image based on the code object
         * and sets the name and points of the code object
         *
         * @param position    position on page where user clicked
         * @param convertView sets the name and points of the code object
         * @param parent      The parent that this view will eventually be attached to
         * @return convertView
         * @author Ian H
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StatObject statObject = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            }


            TextView stat = convertView.findViewById(R.id.myCodesStats);
            TextView statValue = convertView.findViewById(R.id.myCodesStatsValue);

            // Set the name and points of the code object
            stat.setText(statObject.getText());
            statValue.setText(String.valueOf(statObject.getValue()));

            return convertView;
        }
    }
