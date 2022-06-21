package com.example.resim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resim.adapter.ReSIMAdapter;
import com.example.resim.user.ReSIM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data extends AppCompatActivity {
    private RecyclerView recyclerView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<ReSIM> list = new ArrayList<>();
    private ReSIMAdapter Adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        recyclerView = findViewById(R.id.rycView);

        progressDialog = new ProgressDialog(Data.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        Adapter = new ReSIMAdapter(getApplicationContext(), list);
        Adapter.setDialog(new ReSIMAdapter.Dialog() {
            @Override
            public void onClick(final int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus", "Lihat Data"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Data.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), Daftar.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nik", list.get(pos).getNik());
                                intent.putExtra("nohp", list.get(pos).getNohp());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                            case 2:
                                Intent intent1 = new Intent(getApplicationContext(), Dataresim.class);
                                intent1.putExtra("id", list.get(pos).getId());
                                intent1.putExtra("nik", list.get(pos).getNik());
                                intent1.putExtra("nohp", list.get(pos).getNohp());
                                startActivity(intent1);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(Adapter);

    }


    @Override
    protected void onStart(){
        super.onStart();
        getData();
    }


    private void getData() {
        progressDialog.show();

        db.collection("Data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        ReSIM user = new ReSIM(document.getString("nik"), document.getString("nohp"));
                        user.setId(document.getId());
                        list.add(user);
                    }
                    Adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private void deleteData(String id){
        progressDialog.show();
        db.collection("Data").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Data gagal di hapus!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                getData();
            }
        });
    }

    public void fb(View view) {
        Intent intent = new Intent(getApplicationContext(), Daftar.class);
        startActivity(intent);
    }
}