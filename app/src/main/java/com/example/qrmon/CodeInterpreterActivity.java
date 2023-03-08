package com.example.qrmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.io.InputStream;
import java.net.URL;

public class CodeInterpreterActivity extends AppCompatActivity {

    QRCode newQRCode;
    public CodeInterpreter codeInterpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_interpreter);

        String scanned_code = getIntent().getStringExtra("scanned_code");

        //Interpret code in CodeInterpreter and return new QRCode object
        newQRCode = codeInterpreter.interpret(scanned_code);

        // Create image from dicebear based on url
        new LoadImage().execute();

        // Wait for url image to load before next screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(CodeInterpreterActivity.this,ScannedCodePage.class);
                intent.putExtra("QRCode", newQRCode);
                startActivity(intent);
                finish();
            }
        }, 1000);


    }

    public class LoadImage extends AsyncTask<Void,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap=null;
            try {
                bitmap= BitmapFactory.decodeStream((InputStream)new URL(newQRCode.getUrl()).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null) {
                newQRCode.setVisual(bitmap);
                //progress.dismiss();
            } else {}
        }
    }
}
