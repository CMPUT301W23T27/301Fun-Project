package com.example.qrmon;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class CodeInterpreter {

    private String base;
    private Integer score;
    private String eyes;
    private String smile;
    private String name1;
    private String name2;
    private String full_name;
    private QRCode newCode;

    ProgressDialog progress;

    public CodeInterpreter() {
    }

    public QRCode interpret(String scanned_code) {
        // Make robot base from dicebear based on scanned_code position 0
        if (scanned_code.charAt(0) == '0') { base = "Harley"; }
        else if (scanned_code.charAt(0) == '1') { base = "Buster"; }
        else if (scanned_code.charAt(0) == '2') { base = "Callie"; }
        else if (scanned_code.charAt(0) == '3') { base = "Maggie"; }
        else if (scanned_code.charAt(0) == '4') { base = "Cuddles"; }
        else if (scanned_code.charAt(0) == '5') { base = "Bandit"; }
        else if (scanned_code.charAt(0) == '6') { base = "Midnight"; }
        else if (scanned_code.charAt(0) == '7') { base = "Jasmine";}
        else if (scanned_code.charAt(0) == '8') { base = "Bob"; }
        else if (scanned_code.charAt(0) == '9') { base = "Cali"; }
        else if (scanned_code.charAt(0) == 'a') { base = "Dusty"; }
        else if (scanned_code.charAt(0) == 'b') { base = "Coco"; }
        else if (scanned_code.charAt(0) == 'c') { base = "Bubba"; }
        else if (scanned_code.charAt(0) == 'd') { base = "Max"; }
        else if (scanned_code.charAt(0) == 'e') { base = "Lola"; }
        else { base = "Kitty"; } //(scanned_code.charAt(0) == 'f')


        // Make score based on scanned_code position 1
        if (scanned_code.charAt(1) == '0') { score = 0; }
        else if (scanned_code.charAt(1) == '1') { score = 1; }
        else if (scanned_code.charAt(1) == '2') { score = 2; }
        else if (scanned_code.charAt(1) == '3') { score = 3; }
        else if (scanned_code.charAt(1) == '4') { score = 4; }
        else if (scanned_code.charAt(1) == '5') { score = 5; }
        else if (scanned_code.charAt(1) == '6') { score = 6; }
        else if (scanned_code.charAt(1) == '7') { score = 7; }
        else if (scanned_code.charAt(1) == '8') { score = 8; }
        else if (scanned_code.charAt(1) == '9') { score = 9; }
        else if (scanned_code.charAt(1) == 'a') { score = 10; }
        else if (scanned_code.charAt(1) == 'b') { score = 11; }
        else if (scanned_code.charAt(1) == 'c') { score = 12; }
        else if (scanned_code.charAt(1) == 'd') { score = 13; }
        else if (scanned_code.charAt(1) == 'e') { score = 14; }
        else { score = 15; } //(scanned_code.charAt(1) == 'f')


        // Make first name based on scanned_code position 2
        if (scanned_code.charAt(2) == '0') { name1 = "Martin "; }
        else if (scanned_code.charAt(2) == '1') { name1 = "Joel "; }
        else if (scanned_code.charAt(2) == '2') { name1 = "Grace "; }
        else if (scanned_code.charAt(2) == '3') { name1 = "Ian "; }
        else if (scanned_code.charAt(2) == '4') { name1 = "Mostafa "; }
        else if (scanned_code.charAt(2) == '5') { name1 = "Greg "; }
        else if (scanned_code.charAt(2) == '6') { name1 = "Dr. "; }
        else if (scanned_code.charAt(2) == '7') { name1 = "Jacob ";}
        else if (scanned_code.charAt(2) == '8') { name1 = "Mr. "; }
        else if (scanned_code.charAt(2) == '9') { name1 = "Karen "; }
        else if (scanned_code.charAt(2) == 'a') { name1 = "Arriana "; }
        else if (scanned_code.charAt(2) == 'b') { name1 = "Ildar "; }
        else if (scanned_code.charAt(2) == 'c') { name1 = "Mrs. "; }
        else if (scanned_code.charAt(2) == 'd') { name1 = "Chad "; }
        else if (scanned_code.charAt(2) == 'e') { name1 = "Duncan "; }
        else { name1 = "Kristen "; } //(scanned_code.charAt(2) == 'f')


        // Make last name based on scanned_code position 3
        if (scanned_code.charAt(3) == '0') { name2 = "the Great "; }
        else if (scanned_code.charAt(3) == '1') { name2 = "Mcmunch "; }
        else if (scanned_code.charAt(3) == '2') { name2 = "the Horrible "; }
        else if (scanned_code.charAt(3) == '3') { name2 = "the IV "; }
        else if (scanned_code.charAt(3) == '4') { name2 = "'redacted' "; }
        else if (scanned_code.charAt(3) == '5') { name2 = "Trump "; }
        else if (scanned_code.charAt(3) == '6') { name2 = "who? "; }
        else if (scanned_code.charAt(3) == '7') { name2 = "West ";}
        else if (scanned_code.charAt(3) == '8') { name2 = "Dustfinger "; }
        else if (scanned_code.charAt(3) == '9') { name2 = "Lightfoot "; }
        else if (scanned_code.charAt(3) == 'a') { name2 = "Simpson "; }
        else if (scanned_code.charAt(3) == 'b') { name2 = "Wormtongue "; }
        else if (scanned_code.charAt(3) == 'c') { name2 = "Johnson "; }
        else if (scanned_code.charAt(3) == 'd') { name2 = "McGee "; }
        else if (scanned_code.charAt(3) == 'e') { name2 = "Youngblood "; }
        else { name2 = "Hitchcock "; } //(scanned_code.charAt(2) == 'f')

        full_name = name1 + name2;

        // Make url based on generated values
        String url = "https://api.dicebear.com/5.x/bottts/png?" +
                "seed=" + base;
        //"&eyes="+eyes+
        //"&smile="+smile;

        Bitmap visual = null;
        ImageView picture = null;
        String comment = "empty";
        String geolocation = "empty";
        String hash = scanned_code;


        newCode = new QRCode(full_name, visual, picture, comment, geolocation, hash, url, score);


        return newCode;
    }
}