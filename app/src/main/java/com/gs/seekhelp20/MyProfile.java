package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MyProfile extends AppCompatActivity {
    TextView nameId, emailId, phn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("users");

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        nameId = findViewById(R.id.textView7);
        emailId = findViewById(R.id.textView9);
        phn = findViewById(R.id.textView11);

        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        final String userId = user.getUid();
        if (user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String phone = user.getPhoneNumber();

            nameId.setText(name);
            emailId.setText(email);
            phn.setText(phone);
        }

       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot messageSnap : dataSnapshot.getChildren()){
                  if (messageSnap.getKey().equals(userId)){
                      user retrievedUser = messageSnap.getValue(user.class);
                      nameId.setText(retrievedUser.name.trim());
                      phn.setText(retrievedUser.phone.trim());
                  }
              }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }
}