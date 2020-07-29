package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fire extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference comp;
    RecyclerView recyclerView;
    ArrayList<CompDet> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);

        database = FirebaseDatabase.getInstance();
        comp = database.getReference("compliants").child("FIRE");

        recyclerView = findViewById(R.id.recycler);
        list = new ArrayList<CompDet>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        comp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    CompDet c = dataSnapshot1.getValue(CompDet.class);
                    list.add(c);
                }
                adapter = new MyAdapter(Fire.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Fire.this,"SOMETHING WENT WRONG",Toast.LENGTH_SHORT).show();
            }
        });
    }
}