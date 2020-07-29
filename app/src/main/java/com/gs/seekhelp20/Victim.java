package com.gs.seekhelp20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class Victim extends AppCompatActivity {
    Button newCom,myCom,signOut,profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim);

        newCom = findViewById(R.id.button6);
        myCom = findViewById(R.id.button7);
        profile = findViewById(R.id.button9);
        signOut = findViewById(R.id.button10);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Victim.this,MyProfile.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("status","loggedOut");
                editor.apply();
                Toast.makeText(getApplicationContext(),"LOGGED OUT SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                Intent out = new Intent(Victim.this,MainActivity.class);
                startActivity(out);
            }
        });


        newCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Victim.this,NewCompliant.class);
                startActivity(intent);
            }
        });

        myCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Victim.this,MyCompliants.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onBackPressed() {
    }
}