package com.example.raport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditSiswa extends AppCompatActivity {

    // Deklarasi Variabel
    EditText edNama,edNis,edKls;
    Button btSave;
    String nm,kls,nis,kd;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_siswa);

        edNama = findViewById(R.id.txEdNamaS);
        edNis = findViewById(R.id.txEdNIS);
        edKls = findViewById(R.id.txEdKelasS);
        btSave = findViewById(R.id.btnEditS);
        db = FirebaseDatabase.getInstance().getReference(); // Get Firebase Realtime Database

        Bundle b = getIntent().getExtras(); // Get Bundle dari intent

        nm = b.getString("nm");
        kls = b.getString("kls");
        nis = b.getString("nis");
        kd = b.getString("kd");

        edNama.setText(nm);
        edKls.setText(kls);
        edNis.setText(nis);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSiswa(new Siswa(edNama.getText().toString(),edKls.getText().toString(),edNis.getText().toString()));
            }
        });
    }
    private void editSiswa(Siswa s){
        db.child("siswa").child(kd).setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Data Berhasil Diupdate",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DaftarSiswa.class));
            }
        });
    }
}