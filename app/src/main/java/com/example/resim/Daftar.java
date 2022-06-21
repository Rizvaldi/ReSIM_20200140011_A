package com.example.resim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Daftar extends AppCompatActivity {
    private EditText edtNik, edtNohp;
    private Button btnSUBMIT;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        edtNik = findViewById(R.id.nik);
        edtNohp = findViewById(R.id.nohp);
        btnSUBMIT = findViewById(R.id.btn_submit);

        progressDialog = new ProgressDialog(Daftar.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan Data");

        btnSUBMIT.setOnClickListener(view ->{
            if (edtNik.getText().length()> 0 && edtNohp.getText().length() > 0){
                savedata(edtNik.getText().toString(), edtNohp.getText().toString());
            }else {
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        if(intent!= null){
            id = intent.getStringExtra("id");
            edtNik.setText(intent.getStringExtra("nik"));
            edtNohp .setText(intent.getStringExtra("nohp"));
        }

    }



    private void savedata(String nik, String nohp) {
        Map<String, Object> user = new HashMap<>();
        user.put("nik", nik);
        user.put("nohp", nohp);

        progressDialog.show();
        if (id != null) {
            db.collection("Data").document(id).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            db.collection("Data")
                    .add(user)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getApplicationContext(), "Berhasil di tambah", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Data.class);
                            progressDialog.dismiss();
                            startActivity(i);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    public void Daftar(View view) {
        Intent intent = new Intent(getApplicationContext(), Data.class);
        startActivity(intent);
    }
}