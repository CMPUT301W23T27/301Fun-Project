package com.example.qrmon;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Creates a QRCode object that associates the QR code scanned with the following attributes:
 * name, visual, picture, comment, geolocation, hash, url and score
 * @author Joel Weller
 */
public class QRCode implements Parcelable {
    private String name;
    private String visual;
    private String picture;
    private String comment;
    private Double longitude;
    private Double latitude;
    private String hash;
    private String url;
    private Integer score;

    /**
     * Constructor for QRCode
     * @param name name of QRCode
     * @param visual creature visual for QRCode
     * @param picture picture for QRCode
     * @param comment comment for QRCode
     * @param longitude longitude for QRCode
     * @param latitude latitude for QRCode
     * @param hash hash string for QRCode
     * @param url url for QR Code
     * @param score score for QRCode
     */
    public QRCode(String name, String visual, String picture, String comment, Double longitude, Double latitude, String hash, String url, Integer score) {
        this.name = name;
        this.visual = visual;
        this.picture = picture;
        this.comment = comment;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hash = hash;
        this.url = url;
        this.score = score;
    }

    public QRCode() {
    }

    protected QRCode(Parcel in) {
        name = in.readString();
        visual = in.readString();
        picture = in.readString();
        comment = in.readString();
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
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
        dest.writeString(visual);
        dest.writeString(picture);
        dest.writeString(comment);
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
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
    public String getVisual() {return visual;}
    public String getPicture() {return picture;}
    public String getComment() {return comment;}
    public Double getLongitude() {return longitude;}
    public Double getLatitude() {return latitude;}
    public String getHash() {return hash;}
    public String getUrl() {return url;}
    public Integer getScore() {return score;}


    public void setName(String newName) {this.name= newName;}
    public void setVisual(String newVisual) {this.visual = newVisual;}
    public void setPicture(String newPicture) {this.picture = newPicture;}
    public void setComment(String newComment) {this.comment = newComment;}
    public void setLongitude(Double newLongitude) {this.longitude = newLongitude;}
    public void setLatitude(Double newLatitude) {this.latitude = newLatitude;}
    public void setHash(String newHash) {this.hash = newHash;}
    public void setUrl(String newUrl) {this.url = newUrl;}
    public void setScore(Integer newScore) {this.score = newScore;}

}