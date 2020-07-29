package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Hospital extends AppCompatActivity {

    FirebaseDatabase database;
    RecyclerView recyclerView;
    ArrayList<CompDet> list;
    MyAdapter adapter;
    DatabaseReference comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        database = FirebaseDatabase.getInstance();
        comp = database.getReference("compliants").child("HOSPITAL");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CompDet>();

        comp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    CompDet c = dataSnapshot1.getValue(CompDet.class);
                    list.add(c);
                }
                adapter = new MyAdapter(Hospital.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Hospital.this,"SOMETHING WENT WRONG",Toast.LENGTH_SHORT).show();
            }
        });
    }
}