package com.example.qrmon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class AvatarMaker {

    private String base;
    private String eyes;
    private String mouth;

    Bitmap bitmapImage;


    public AvatarMaker() {
    }

    /** Create unique Friend Avatar based on UserName
     * @author Martin M Johney
     * @return url for avatar
     */
    public String interpret(String user_name) {
        // Make avatar base from dicebear based on user_name position 0
        if (user_name.charAt(0) == '0') { base = "Harley"; }
        else if (user_name.charAt(0) == 'a') { base = "Buster"; }
        else if (user_name.charAt(0) == 'b') { base = "Callie"; }
        else if (user_name.charAt(0) == 'c') { base = "Maggie"; }
        else if (user_name.charAt(0) == 'd') { base = "Cuddles"; }
        else if (user_name.charAt(0) == 'e') { base = "Bandit"; }
        else if (user_name.charAt(0) == 'f') { base = "Midnight"; }
        else if (user_name.charAt(0) == 'g') { base = "Jasmine";}
        else if (user_name.charAt(0) == 'h') { base = "Bob"; }
        else if (user_name.charAt(0) == 'i') { base = "Cali"; }
        else if (user_name.charAt(0) == 'j') { base = "Jasper"; }
        else if (user_name.charAt(0) == 'k') { base = "Coco"; }
        else if (user_name.charAt(0) == 'l') { base = "Bubba"; }
        else if (user_name.charAt(0) == 'm') { base = "Max"; }
        else if (user_name.charAt(0) == 'n') { base = "Peanut"; }
        else if (user_name.charAt(0) == 'o') { base = "Kiki"; }
        else if (user_name.charAt(0) == 'p') { base = "Zoe"; }
        else if (user_name.charAt(0) == 'r') { base = "Molly"; }
        else if (user_name.charAt(0) == 's') { base = "Baby"; }
        else if (user_name.charAt(0) == 't') { base = "Cali"; }
        else { base = "Kitty"; } //(user_name.charAt(0) == 'f')




        // Make eyes based on user_name position 4
        if (user_name.charAt(4) == '0') { eyes = "bulging"; }
        else if (user_name.charAt(4) == '1') { eyes = "dizzy"; }
        else if (user_name.charAt(4) == '2') { eyes = "eva"; }
        else if (user_name.charAt(4) == '3') { eyes = "frame1"; }
        else if (user_name.charAt(4) == '4') { eyes = "frame2"; }
        else if (user_name.charAt(4) == '5') { eyes = "glow"; }
        else if (user_name.charAt(4) == '6') { eyes = "happy"; }
        else if (user_name.charAt(4) == '7') { eyes = "hearts";}
        else if (user_name.charAt(4) == '8') { eyes = "robocop"; }
        else if (user_name.charAt(4) == '9') { eyes = "round"; }
        else if (user_name.charAt(4) == 'a') { eyes = "roundFrame01"; }
        else if (user_name.charAt(4) == 'b') { eyes = "roundFrame02"; }
        else if (user_name.charAt(4) == 'c') { eyes = "sensor"; }
        else if (user_name.charAt(4) == 'd') { eyes = "shade01"; }
        else if (user_name.charAt(4) == 'e') { eyes = "bulging"; }
        else { eyes = "dizzy"; } //(user_name.charAt(2) == 'f')



        // Make url based on generated values
//        String url = "https://api.dicebear.com/5.x/bottts/png?" +
//                "seed=" + base +
//                "&eyes=" + eyes +
//                "&mouth=" + mouth;

        String url = "https://api.dicebear.com/6.x/notionists/png?" +
                "seed=" + base;



        return url;
    }
}

