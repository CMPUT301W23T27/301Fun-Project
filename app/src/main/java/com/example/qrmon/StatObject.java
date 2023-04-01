package com.example.qrmon;

import com.google.android.material.color.utilities.Score;

public class StatObject {
    private String text;
    private int value;

    public StatObject(String text, int value){
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
