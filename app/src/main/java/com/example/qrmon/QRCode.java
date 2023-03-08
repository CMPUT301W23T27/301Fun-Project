package com.example.qrmon;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

public class QRCode implements Serializable {
    private String name;
    private Bitmap visual;
    private ImageView picture;
    private String comment;
    private String geolocation;
    private String hash;
    private String url;
    private Integer score;

    QRCode(String name, Bitmap visual, ImageView picture, String comment, String geolocation, String hash, String url, Integer score) {
        this.name = name;
        this.visual = visual;
        this.picture = picture;
        this.comment = comment;
        this.geolocation = geolocation;
        this.url = url;
        this.score = score;
    }

    public String getName() {return name;}
    public Bitmap getVisual() {return visual;}
    public ImageView getPicture() {return picture;}
    public String getComment() {return comment;}
    public String getGeolocation() {return geolocation;}
    public String getHash() {return hash;}
    public String getUrl() {return url;}
    public Integer getScore() {return score;}



    public void setName(String newName) {this.name= newName;}
    public void setVisual(Bitmap newVisual) {this.visual = newVisual;}
    public void setPicture(ImageView newPicture) {this.picture = newPicture;}
    public void setComment(String newComment) {this.comment = newComment;}
    public void setGeolocation(String newGeolocation) {this.geolocation = newGeolocation;}
    public void setHash(String newHash) {this.hash = newHash;}
    public void setUrl(String newUrl) {this.url = newUrl;}
    public void setScore(Integer newScore) {this.score = newScore;}

}