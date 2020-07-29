package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText emailId,password,name,phone;
    Button signup;
    FirebaseAuth mFirebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailId = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        name = findViewById(R.id.editTextTextPersonName);
        signup = findViewById(R.id.button3);
        phone = findViewById(R.id.editTextPhone);
        mFirebaseAuth = FirebaseAuth.getInstance();
        


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString().trim();
                String pwd = password.getText().toString().trim();


                if (email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()) {
                    password.setError("Please enter a valid password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Signup.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                }

                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(Signup.this,"Signup Unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                emailId = findViewById(R.id.editTextTextEmailAddress2);
                                password = findViewById(R.id.editTextTextPassword2);
                                name = findViewById(R.id.editTextTextPersonName);
                                signup = findViewById(R.id.button3);
                                phone = findViewById(R.id.editTextPhone);
                                mFirebaseAuth = FirebaseAuth.getInstance();


                                String username = name.getText().toString().trim();
                                String phn = phone.getText().toString().trim();
                                String email = emailId.getText().toString().trim();
                                Toast.makeText(Signup.this,"Signup successful",Toast.LENGTH_SHORT).show();
                                user User = new user(username,email,phn);
                                FirebaseUser user =mFirebaseAuth.getCurrentUser();
                                String userId = user.getUid();
                                myRef.child(userId).setValue(User);



                                Intent intent = new Intent(Signup.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else
                    Toast.makeText(Signup.this,"Error Occured",Toast.LENGTH_SHORT).show();
            }
        });

    }
}