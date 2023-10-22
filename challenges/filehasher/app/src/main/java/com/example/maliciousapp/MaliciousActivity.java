package com.example.maliciousapp;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.maliciousapp.databinding.ActivityMaliciousBinding;

import java.security.MessageDigest;

public class MaliciousActivity extends AppCompatActivity {
    private static final String TAG ="MOBIOTSEC";

    private AppBarConfiguration appBarConfiguration;
    private ActivityMaliciousBinding binding;

    protected byte[] calcHash(String filePath) {
        byte[] message = filePath.getBytes();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"secondaryActivitystart");

        Intent intent = getIntent(); //get intent from victim
        Uri uri = intent.getData();
        Log.d(TAG, "attempt to parse Uri");
        //Log.d(TAG, uri.toString());
        byte[] hash = calcHash(uri.getPath()); //calc sha-256

        String hex = bytesToHex(hash); //translate to hexadecimal
        //hex works so far so good

        // return the hash in a "result" intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("hash", hex);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
}
