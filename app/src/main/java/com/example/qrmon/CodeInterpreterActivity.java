package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 CodeInterpreterActivity is an activity that displays a QR code image and the interpreted data from the scanned code.
 It uses an AsyncTask to load the image from a URL obtained from a QR code object created by interpreting the scanned code.
 The activity also converts the loaded image to a string using the BitMapToString method.
 The interpreted QR code data is passed to the next activity using an intent.
 @author Joel Weller
 */
public class CodeInterpreterActivity extends AppCompatActivity {

    QRCode newQRCode;
    public CodeInterpreter codeInterpreter;
    Bitmap bitmapImage;
    /**
     Called when the activity is starting. Retrieves the scanned code from the intent extras and
     interprets it using the CodeInterpreter object. Creates a LoadImage AsyncTask to load the image
     from the URL obtained from the interpreted QR code object. After loading the image, the BitMapToString
     method is used to convert it to a string and set it as the visual data for the interpreted QR code object.
     The QR code object is then passed to the next activity using an intent.
     @param savedInstanceState the saved instance state bundle.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_interpreter);

        String scanned_code = getIntent().getStringExtra("scanned_code");

//        String scanned_code = "a26h90";
        codeInterpreter = new CodeInterpreter();
        System.out.println("here in CodeInterpreter: " + scanned_code);


        //Interpret code in CodeInterpreter and return new QRCode object
        newQRCode = codeInterpreter.interpret(scanned_code);

        // Create image from dicebear based on url
        new LoadImage().execute();

        // Wait for url image to load before next screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String imageEncoded = BitMapToString(bitmapImage);
                newQRCode.setVisual(imageEncoded);
                Intent intent = new Intent(CodeInterpreterActivity.this, ScannedCodePage.class);
                intent.putExtra("QRCode", newQRCode);
                startActivity(intent);
                finish();
            }
        }, 2000);


    }
    /**
     An AsyncTask to load the image from the URL obtained from the QR code object.
     Loads the image from the URL.
     */

    // Code adapted from https://www.thecrazyprogrammer.com/2015/10/android-load-image-from-url-internet-example.html
    public class LoadImage extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(newQRCode.getUrl()).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                //newQRCode.setVisual(bitmap);
                bitmapImage = bitmap;
                //progress.dismiss();
            } else {
            }
        }
    }

    //Adapted code from https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}

