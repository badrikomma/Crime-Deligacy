package com.gs.seekhelp20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class NewCompliant extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> al;
    ArrayAdapter<String> ad;
    String type;

    Button cancel,choose,submit;

    EditText victimNameid,areaid,landmarkid,pincodeid,cityid,descriptionid;

    private static final int PICK_IMAGE_REQUEST = 1;
    Uri compliantImageUri;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase database;
    DatabaseReference comp;

    StorageReference mStorageRef;

    String userid;

    CompDet details;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compliant);

        spinner = findViewById(R.id.spinner2);
        al = new ArrayList<String>();
        al.add("CHOOSE TYPE");
        al.add("CRIME");
        al.add("HOSPITAL");
        al.add("FIRE");

        ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, al);
        spinner.setAdapter(ad);

        cancel = findViewById(R.id.button15);
        choose = findViewById(R.id.chooseImage);
        submit = findViewById(R.id.button16);

        victimNameid = findViewById(R.id.nameid);
        areaid = findViewById(R.id.areaid);
        landmarkid = findViewById(R.id.landmarkid);
        pincodeid = findViewById(R.id.pincodeid);
        cityid = findViewById(R.id.cityid);
        descriptionid = findViewById(R.id.descriptionid);

        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        comp = database.getReference("compliants");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        imgView = findViewById(R.id.imageView9);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = spinner.getItemAtPosition(position).toString();
                Toast.makeText(NewCompliant.this,type,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(NewCompliant.this,"PLEASE SELECT YOUR COMPLIANT TYPE",Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCompliant.this,Victim.class);
                startActivity(intent);
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (victimNameid.getText().toString().isEmpty()){
                    victimNameid.setError("PLEASE ENTER VICTIM NAME");
                    victimNameid.requestFocus();
                }
                else if (areaid.getText().toString().isEmpty()){
                    areaid.setError("PLEASE ENTER AREA NAME");
                    areaid.requestFocus();
                }
                else if (cityid.getText().toString().isEmpty()){
                    cityid.setError("PLEASE ENTER THE CITY");
                    cityid.requestFocus();
                }
                else if (descriptionid.getText().toString().isEmpty()){
                    descriptionid.setError("PLEASE EXPLAIN YOUR CASE");
                    descriptionid.requestFocus();
                }
                else if (type.equals("CHOOSE TYPE")){
                    Toast.makeText(NewCompliant.this,"PLEASE SELECT TYPE OF CASE",Toast.LENGTH_SHORT).show();
                }
                else if (compliantImageUri == null){
                    Toast.makeText(NewCompliant.this,"PLEASE CHOOSE AN IMAGE",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadCompImage();
                    Toast.makeText(NewCompliant.this, "REPORTED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewCompliant.this, Victim.class);
                    startActivity(intent);
                }
            }
        });


    }




    private void openFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        compliantImageUri = data.getData();
        if (compliantImageUri == null){
            Toast.makeText(NewCompliant.this,"IMAGE NOT SELECTED",Toast.LENGTH_SHORT).show();
            imgView.setImageURI(compliantImageUri);
        }
    }

    public void uploadCompData(String url){
        String victimName = victimNameid.getText().toString();
        String area = areaid.getText().toString();
        String landmark = landmarkid.getText().toString();
        String pincode = pincodeid.getText().toString();
        String city = cityid.getText().toString();
        String description = descriptionid.getText().toString();


        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        userid = user.getUid();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm:ss");
        String date = simpleDateFormat.format(calendar.getTime());
        String time = simpleTime.format(calendar.getTime());


        DatabaseReference caseComp = comp.child(type);
        DatabaseReference myComp = caseComp.child(victimName);


        details = new CompDet(victimName,area,landmark,pincode,city,description,userid,url,date,time);
        myComp.setValue(details);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    void uploadCompImage(){
        if (compliantImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(type).child(victimNameid.getText().toString()+"."+getFileExtension(compliantImageUri));
            fileReference.putFile(compliantImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(NewCompliant.this,uri.toString(),Toast.LENGTH_LONG).show();
                                    uploadCompData(uri.toString());
                                }
                            });
                            Toast.makeText(NewCompliant.this,"UPLOAD SUCCESSFUL",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewCompliant.this,"IMAGE UPLOAD FAILED",Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(NewCompliant.this,"IMAGE IS NOT SELECTED",Toast.LENGTH_SHORT).show();
        }

    }




}