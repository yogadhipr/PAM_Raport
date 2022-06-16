package com.example.raport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    List<String> judul;
    List<Integer> gambar;
    TextView sapaUser;
    Button btKeluar;
    String uid;
    RecyclerView rv;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btKeluar = findViewById(R.id.btnLogout);
        sapaUser = findViewById(R.id.sapaUser);
        rv = findViewById(R.id.listMenu);

        judul = new ArrayList<>();
        gambar = new ArrayList<>();

        // Set tampilan judul menu
        judul.add("Edit Profil");
        judul.add("Daftar Siswa");
        judul.add("Input Nilai");
        judul.add("Hasil Raport");
        // Set tampilan gambar menu
        gambar.add(R.drawable.ic_baseline_edit_24);
        gambar.add(R.drawable.ic_baseline_person_24);
        gambar.add(R.drawable.ic_round_event_note_24);
        gambar.add(R.drawable.ic_round_file_copy_24);

        // Set adapter
        adapter = new Adapter(this, judul, gambar);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        uid = fAuth.getCurrentUser().getUid(); // Get ID user login
        DocumentReference dr = fStore.collection("akun").document(uid);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    if (ds.exists()){ // Jika ada
                        sapaUser.setText("Selamat Datang, " + ds.getString("nama"));
                        Log.d(TAG,"Data : "+ds.getData());
                    }else{
                        Log.d(TAG,"Tidak ada data");
                    }
                }else{
                    Log.d(TAG,"Status gagal dengan : "+task.getException());
                }
            }
        });

        btKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });
    }
    private void logout(View v){
        FirebaseAuth.getInstance().signOut(); // Firebase melakukan aksi logout user
        startActivity(new Intent(getApplicationContext(),LoginAkun.class));
        finish();
    }
    @Override
    // Kondisi ketika menekan tombol back
    public void onBackPressed() {
        // Akhiri aplikasi
        finishAffinity();
        finish();
    }
}