package com.example.raport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InputCatatan extends Fragment {

    // Deklarasi variabel
    TextView namas;
    EditText txCatat;
    DatabaseReference db;
    String kodee = MenuInputData.kode;

    public InputCatatan() { // Konstruktor FragmentCatatan
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_input_catatan, container, false);
        namas = v.findViewById(R.id.tv_namaS);
        txCatat = v.findViewById(R.id.txCatatan);
        FloatingActionButton fab = v.findViewById(R.id.floatingActionButton3);
        Bundle b = getActivity().getIntent().getExtras(); // Get bundle dari intent
        namas.setText("Nama     :   "+b.getString("nama"));

        db = FirebaseDatabase.getInstance().getReference();
        db.child("siswa").child(kodee).child("nilai").child("catatan").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Nilai n =  ds.getValue(Nilai.class);
                        txCatat.setText(n.getCatatan());
                }
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String tx = txCatat.getText().toString();
                    inputCatatan(new Nilai(tx));
                    }
            });
            return v;
        }
        private void inputCatatan(Nilai n) {
        db.child("siswa").child(kodee).child("nilai").child("catatan").removeValue();
            db.child("siswa").child(kodee).child("nilai").child("catatan").push().setValue(n).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(),"Catatan berhasil diinput!",Toast.LENGTH_LONG).show();
                }
            });
    }
}