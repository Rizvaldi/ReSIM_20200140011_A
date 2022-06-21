package com.example.resim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class Dataresim extends AppCompatActivity {
    private TextView nik,nohp;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataresim);

        nik = findViewById(R.id.nik);
        nohp = findViewById(R.id.nohp);

        progressDialog = new ProgressDialog(Dataresim.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");

        Intent intent = getIntent();
        if(intent != null){
            nik.setText(intent.getStringExtra("nik"));
            nohp.setText(intent.getStringExtra("nohp"));
        }
    }
}