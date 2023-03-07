package com.example.qrmon;

import android.widget.ImageView;

public class QRCode {
    private String name;
    private ImageView visual;
    private ImageView picture;
    private String comment;
    private String geolocation;
    private int Score;

    public QRCode(String name, int score) {
        this.name = name;
        Score = score;
    }

    public int getScore() {
        return Score;
    }
}
