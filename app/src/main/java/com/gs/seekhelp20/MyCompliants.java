package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCompliants extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myCompFire,myCompCrime,myComHos;

    RecyclerView recyclerView;

    ArrayList<CompDet> list;
    MyAdapter adapter;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser user;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_compliants);

        database = FirebaseDatabase.getInstance();
        myCompCrime = database.getReference("compliants").child("CRIME");
        myCompFire = database.getReference("compliants").child("FIRE");
        myComHos = database.getReference("compliants").child("HOSPITAL");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CompDet>();

        mFirebaseAuth = FirebaseAuth.getInstance();

        user = mFirebaseAuth.getCurrentUser();
        userId = user.getUid();

        myCompCrime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    CompDet c = dataSnapshot1.getValue(CompDet.class);
                    if (c.userid.equals(userId)){
                        list.add(c);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyCompliants.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        myCompFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    CompDet c = dataSnapshot1.getValue(CompDet.class);
                    if (c.userid.equals(userId)){
                        list.add(c);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyCompliants.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        myComHos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    CompDet c = dataSnapshot1.getValue(CompDet.class);
                    if (c.userid.equals(userId)){
                        list.add(c);
                    }
                }
                adapter = new MyAdapter(MyCompliants.this,list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyCompliants.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}