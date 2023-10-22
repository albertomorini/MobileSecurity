package com.example.maliciousapp;

import static androidx.core.content.FileProvider.getUriForFile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity{

    private String ACTION_HASHFILE = "com.mobiotsec.intent.action.HASHFILE";


    /**
     * convert bytes to hex string (lowercase)
     * @param bytes array of byte, in input
     * @return hex string, lowercase
     */
    protected static String bytesToHex (byte[] bytes) {

        StringBuilder out = new StringBuilder();
        for (byte b:bytes) {
            out.append(String.format("%02X", b));
        }
        return out.toString().toLowerCase();
    }

    /**
     * do the hash of a given string
     * @param text2hash
     * @return SHA-256 digest of the input
     */
    protected byte[] calcHash (String text2hash) {
        byte[] message = text2hash.getBytes();

        MessageDigest digest = null;
        try {
            digest=MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            System.err.println("exception well handled");
        }
        digest.update(message);
        return digest.digest();
    }

    /**
     * Return the value inside the file
     * @param uri the uri path
     * @return string with the line inside the file
     */
    protected String getDataFromFile(Uri uri) {

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getContentResolver().openInputStream(uri))
                    );
            String s = br.readLine();
            Log.w("HashApp","Valore file:"+s);
            return s;
        } catch (Exception e) {
            Log.w("HashApp",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void manageIntent() {
        System.out.println("ACTIVITY STARTED");
        Log.w("HashApp","ACTIVITY STARTED");

        Intent intent= getIntent(); //get intent from victim
        Uri pathUri = intent.getData(); //get the uri


        String messageInFile = getDataFromFile(pathUri); //retrieve the message inside the file

        byte[] hash = calcHash(messageInFile); //hash it

        String hex = bytesToHex(hash); //convert to HEX

        Log.w("HashApplication", "RESULTS OF DIGEST IN HEX: "+hex);


        //PASS THE RESULT VIA INTENT TO THE VICTIM
        Intent resultIntent=new Intent();
        resultIntent.putExtra("hash",hex);
        setResult(MainActivity.RESULT_OK,resultIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageIntent();
    }
}