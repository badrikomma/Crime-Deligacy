package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText et1,et2;
    TextView tv1,tv2;
    FirebaseAuth mFirebaseAuth;

    String adminEmail = "admin";
    String adminPassword = "admin";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = findViewById(R.id.button2);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        et1 = findViewById(R.id.editTextTextEmailAddress);
        et2 = findViewById(R.id.editTextTextPassword);


        mFirebaseAuth = FirebaseAuth.getInstance();

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et1.getText().toString().trim();
                String password = et2.getText().toString().trim();

                if (!(email.isEmpty() && password.isEmpty())){
                if (email.equals("admin") && password.equals("admin")){
                    Intent admin = new Intent(MainActivity.this,Admin.class);
                    startActivity(admin);
                }
                else {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "LOGGED IN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                saveCred();
                                Intent intent = new Intent(MainActivity.this, Victim.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "LOGIN FAILED,CHECK YOUR CREDIENTIALS", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }}
                else if (email.isEmpty()){
                    et1.setError("EMAIL ID IS EMPTY");
                    et1.requestFocus();
                }
                else if (et2.getText().toString().isEmpty()){
                    et2.setError("PASSWORD IS EMPTY");
                    et2.requestFocus();
                }
            }
        });

    }

    public void saveCred(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("email",et1.getText().toString());
        editor.putString("password",et2.getText().toString());
        editor.putString("status","logIn");

        editor.apply();
        System.out.println(et2.getText().toString());
    }

}