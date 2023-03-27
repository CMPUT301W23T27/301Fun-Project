package com.example.qrmon;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User implements Parcelable {

    private String name;
    private String usrName;
    private String visual;
    private String email;
    private String hash;
    private String phoneNum;

    private ArrayList<String> friendsList;

    /**
     * Constructor for User
     * @param name full name of User
     * @param usrName user-name of User
     * @param visual avatar visual for User
     * @param hash hashed password of the User
     * @param email email of the User
     * @param phoneNum phone Number of the User
     * @param friendsList User's Friends List
     */
    public User(String name, String usrName, String visual, String hash, String email, String phoneNum, ArrayList<String> friendsList) {
        this.name = name;
        this.usrName = usrName;
        this.visual = visual;
        this.hash = hash;
        this.email = email;
        this.phoneNum = phoneNum;
        this.friendsList = friendsList;
    }

    protected User(Parcel in) {
        name = in.readString();
        usrName = in.readString();
        visual = in.readString();
        email = in.readString();
        hash = in.readString();
        phoneNum = in.readString();
        friendsList = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(usrName);
        dest.writeString(visual);
        dest.writeString(email);
        dest.writeString(hash);
        dest.writeString(phoneNum);
        dest.writeStringList(friendsList);

    }

    public String getName() {return name;}
    public String getUsrName() {return usrName;}
    public String getVisual() {return visual;}
    public String getEmail() {return email;}
    public String getHash() {return hash;}
    public String getPhoneNum() {return phoneNum;}
    public ArrayList<String> getFriendsList() {return friendsList;}

    public void setName(String newName) { this.name = newName;}
    public void setUsrName(String newUsrName) {this.usrName = newUsrName;}
    public void setVisual(String newVisual) {this.visual = newVisual;}
    public void setEmail(String newEmail) {this.email = newEmail;}
    public void setHash(String newHash) {this.hash = newHash;}
    public void setPhoneNum(String newPhoneNum) {this.phoneNum = newPhoneNum;}
    public void setFriendsList(ArrayList<String> newFriendsList) {this.friendsList = newFriendsList;}

}
