package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
                    if (sharedPreferences.getString("status","").equals("logIn")) {
                        String email = sharedPreferences.getString("email","");
                        String password = sharedPreferences.getString("password","");
                        mFirebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(splash.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(splash.this, "WELCOME BACK", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(splash.this, Victim.class);
                                startActivity(intent);
                            }
                        });
                    }
                    else {
                        System.out.println(sharedPreferences.getString("email","no email"));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception ignored){
                    ignored.printStackTrace();
                }
            }
        },5000);
    }

    public void onDestroy(){
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }

}





//    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//    startActivity(intent);
//    finish();

