package com.example.qrmon;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class QRCode implements Parcelable {
    private String name;
    private Bitmap visual;
    private Bitmap picture;
    private String comment;
    private String geolocation;
    private String hash;
    private String url;
    private Integer score;

    QRCode(String name, Bitmap visual, Bitmap picture, String comment, String geolocation, String hash, String url, Integer score) {
        this.name = name;
        this.visual = visual;
        this.picture = picture;
        this.comment = comment;
        this.geolocation = geolocation;
        this.hash = hash;
        this.url = url;
        this.score = score;
    }

    protected QRCode(Parcel in) {
        name = in.readString();
        visual = in.readParcelable(Bitmap.class.getClassLoader());
        picture = in.readParcelable(Bitmap.class.getClassLoader());
        comment = in.readString();
        geolocation = in.readString();
        hash = in.readString();
        url = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(visual, flags);
        dest.writeParcelable(picture, flags);
        dest.writeString(comment);
        dest.writeString(geolocation);
        dest.writeString(hash);
        dest.writeString(url);
        if (score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(score);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QRCode> CREATOR = new Creator<QRCode>() {
        @Override
        public QRCode createFromParcel(Parcel in) {
            return new QRCode(in);
        }

        @Override
        public QRCode[] newArray(int size) {
            return new QRCode[size];
        }
    };

    public String getName() {return name;}
    public Bitmap getVisual() {return visual;}
    public Bitmap getPicture() {return picture;}
    public String getComment() {return comment;}
    public String getGeolocation() {return geolocation;}
    public String getHash() {return hash;}
    public String getUrl() {return url;}
    public Integer getScore() {return score;}



    public void setName(String newName) {this.name= newName;}
    public void setVisual(Bitmap newVisual) {this.visual = newVisual;}
    public void setPicture(Bitmap newPicture) {this.picture = newPicture;}
    public void setComment(String newComment) {this.comment = newComment;}
    public void setGeolocation(String newGeolocation) {this.geolocation = newGeolocation;}
    public void setHash(String newHash) {this.hash = newHash;}
    public void setUrl(String newUrl) {this.url = newUrl;}
    public void setScore(Integer newScore) {this.score = newScore;}

}