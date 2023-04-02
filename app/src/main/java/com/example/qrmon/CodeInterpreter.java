package com.example.qrmon;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.widget.ImageView;

/** CodeIntepreter is an object that is created from scanning a QR Code while instantiating the following attributes
 * base, score, name1 (first name), name2 (surname), full_name, newCode
 * @author Joel Weller
 *
 */
public class CodeInterpreter {

    private String base;
    private String eyes;
    private String mouth;
    private Integer score1;
    private Integer score2;
    private Integer score;
    private String name1;
    private String name2;
    private String full_name;
    private QRCode newCode;

    ProgressDialog progress;

    public CodeInterpreter() {
    }

    /** Create unique QR Code names based on string from scanned QRCode
     * @author Joel Weller
     * @return newCode
     */
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


        // Make score1 based on scanned_code position 1
        if (scanned_code.charAt(1) == '0') { score1 = 0; }
        else if (scanned_code.charAt(1) == '1') { score1 = 1; }
        else if (scanned_code.charAt(1) == '2') { score1 = 2; }
        else if (scanned_code.charAt(1) == '3') { score1 = 3; }
        else if (scanned_code.charAt(1) == '4') { score1 = 4; }
        else if (scanned_code.charAt(1) == '5') { score1 = 5; }
        else if (scanned_code.charAt(1) == '6') { score1 = 6; }
        else if (scanned_code.charAt(1) == '7') { score1 = 7; }
        else if (scanned_code.charAt(1) == '8') { score1 = 8; }
        else if (scanned_code.charAt(1) == '9') { score1 = 9; }
        else if (scanned_code.charAt(1) == 'a') { score1 = 10; }
        else if (scanned_code.charAt(1) == 'b') { score1 = 11; }
        else if (scanned_code.charAt(1) == 'c') { score1 = 12; }
        else if (scanned_code.charAt(1) == 'd') { score1 = 13; }
        else if (scanned_code.charAt(1) == 'e') { score1 = 14; }
        else { score1 = 15; } //(scanned_code.charAt(1) == 'f')


        // Make first name based on scanned_code position 2
        if (scanned_code.charAt(2) == '0') { name1 = "Martin "; }
        else if (scanned_code.charAt(2) == '1') { name1 = "Joel "; }
        else if (scanned_code.charAt(2) == '2') { name1 = "Grace "; }
        else if (scanned_code.charAt(2) == '3') { name1 = "Ian "; }
        else if (scanned_code.charAt(2) == '4') { name1 = "Mostafa "; }
        else if (scanned_code.charAt(2) == '5') { name1 = "Devi "; }
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
        else if (scanned_code.charAt(3) == '1') { name2 = "McMunch "; }
        else if (scanned_code.charAt(3) == '2') { name2 = "Fitzgerald "; }
        else if (scanned_code.charAt(3) == '3') { name2 = "the IV "; }
        else if (scanned_code.charAt(3) == '4') { name2 = "'redacted' "; }
        else if (scanned_code.charAt(3) == '5') { name2 = "Trump "; }
        else if (scanned_code.charAt(3) == '6') { name2 = "Jenkins "; }
        else if (scanned_code.charAt(3) == '7') { name2 = "West ";}
        else if (scanned_code.charAt(3) == '8') { name2 = "Hendrix "; }
        else if (scanned_code.charAt(3) == '9') { name2 = "Lightfoot "; }
        else if (scanned_code.charAt(3) == 'a') { name2 = "Simpson "; }
        else if (scanned_code.charAt(3) == 'b') { name2 = "Shepherd "; }
        else if (scanned_code.charAt(3) == 'c') { name2 = "Johnson "; }
        else if (scanned_code.charAt(3) == 'd') { name2 = "McGee "; }
        else if (scanned_code.charAt(3) == 'e') { name2 = "Youngblood "; }
        else { name2 = "Hitchcock "; } //(scanned_code.charAt(2) == 'f')

        // Make eyes based on scanned_code position 4
        if (scanned_code.charAt(4) == '0') { eyes = "bulging"; }
        else if (scanned_code.charAt(4) == '1') { eyes = "dizzy"; }
        else if (scanned_code.charAt(4) == '2') { eyes = "eva"; }
        else if (scanned_code.charAt(4) == '3') { eyes = "frame1"; }
        else if (scanned_code.charAt(4) == '4') { eyes = "frame2"; }
        else if (scanned_code.charAt(4) == '5') { eyes = "glow"; }
        else if (scanned_code.charAt(4) == '6') { eyes = "happy"; }
        else if (scanned_code.charAt(4) == '7') { eyes = "hearts";}
        else if (scanned_code.charAt(4) == '8') { eyes = "robocop"; }
        else if (scanned_code.charAt(4) == '9') { eyes = "round"; }
        else if (scanned_code.charAt(4) == 'a') { eyes = "roundFrame01"; }
        else if (scanned_code.charAt(4) == 'b') { eyes = "roundFrame02"; }
        else if (scanned_code.charAt(4) == 'c') { eyes = "sensor"; }
        else if (scanned_code.charAt(4) == 'd') { eyes = "shade01"; }
        else if (scanned_code.charAt(4) == 'e') { eyes = "bulging"; }
        else { eyes = "dizzy"; } //(scanned_code.charAt(2) == 'f')

        // Make score2 based on scanned_code position 5
        if (scanned_code.charAt(5) == '0') { score2 = 0; }
        else if (scanned_code.charAt(5) == '1') { score2 = 1; }
        else if (scanned_code.charAt(5) == '2') { score2 = 2; }
        else if (scanned_code.charAt(5) == '3') { score2 = 3; }
        else if (scanned_code.charAt(5) == '4') { score2 = 4; }
        else if (scanned_code.charAt(5) == '5') { score2 = 5; }
        else if (scanned_code.charAt(5) == '6') { score2 = 6; }
        else if (scanned_code.charAt(5) == '7') { score2 = 7; }
        else if (scanned_code.charAt(5) == '8') { score2 = 8; }
        else if (scanned_code.charAt(5) == '9') { score2 = 9; }
        else if (scanned_code.charAt(5) == 'a') { score2 = 10; }
        else if (scanned_code.charAt(5) == 'b') { score2 = 11; }
        else if (scanned_code.charAt(5) == 'c') { score2 = 12; }
        else if (scanned_code.charAt(5) == 'd') { score2= 13; }
        else if (scanned_code.charAt(5) == 'e') { score2= 14; }
        else { score2 = 15; } //(scanned_code.charAt(1) == 'f')

        // Make mouth based on scanned_code position 6
        if (scanned_code.charAt(6) == '0') { mouth = "bite"; }
        else if (scanned_code.charAt(6) == '1') { mouth = "diagram"; }
        else if (scanned_code.charAt(6) == '2') { mouth = "grill01"; }
        else if (scanned_code.charAt(6) == '3') { mouth = "grill02"; }
        else if (scanned_code.charAt(6) == '4') { mouth = "grill03"; }
        else if (scanned_code.charAt(6) == '5') { mouth = "smile01"; }
        else if (scanned_code.charAt(6) == '6') { mouth = "smile02"; }
        else if (scanned_code.charAt(6) == '7') { mouth = "square01"; }
        else if (scanned_code.charAt(6) == '8') { mouth = "square02"; }
        else if (scanned_code.charAt(6) == '9') { mouth = "diagram"; }
        else if (scanned_code.charAt(6) == 'a') { mouth = "grill01"; }
        else if (scanned_code.charAt(6) == 'b') { mouth = "grill02"; }
        else if (scanned_code.charAt(6) == 'c') { mouth = "grill03"; }
        else if (scanned_code.charAt(6) == 'd') { mouth= "smile01"; }
        else if (scanned_code.charAt(6) == 'e') { mouth= "smile02"; }
        else { mouth = "square01"; } //(scanned_code.charAt(1) == 'f')

        full_name = name1 + name2;

        score = score1 * score2;

        // Make url based on generated values
        String url = "https://api.dicebear.com/5.x/bottts/png?" +
                "seed=" + base +
                "&eyes=" + eyes +
                "&mouth=" + mouth;



        String visual = null;
        String picture = null;
        String comment = "empty";
        Double longitude = null;
        Double latitude = null;
        String hash = scanned_code;

        newCode = new QRCode(full_name, visual, picture, comment, longitude, latitude, hash, url, score);

        return newCode;
    }
}