package com.example.maliciousapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    /*
    private static final String TAG = "tag";
    protected byte[] calcHash(String filePath){
        byte [] message = filePath.getBytes();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (java.security.NoSuchAlgorithmException e){
            System.err.println("exception well handled");
        }
        digest.update(message);
        return digest.digest();
    }

    protected static String bytesToHex(byte[] bytes) {
        StringBuilder out = new StringBuilder();
        for (byte b : bytes) {
            out.append(String.format("%02X", b));
        }
        return out.toString();
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        /*
        byte[] hash =calcHash("testHex"); //calc sha-256

        String hex = bytesToHex(hash); //translate to hexadecimal
        Log.d(TAG, hex);
         */

}