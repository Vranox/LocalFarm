package com.example.localfarm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.localfarm.R;
import com.example.localfarm.models.actor.Establishment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyEstablishementActivity extends Activity {

    private DatabaseReference mDatabase;
    private Establishment establishment;
    private SharedPreferences mPrefs;
    private String mEstablishmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myestablishement_layout);

        // Récupération de l'id de l'établissement depuis les SharedPreferences
        mPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        mEstablishmentId = mPrefs.getString("id", "");

        mDatabase = FirebaseDatabase.getInstance().getReference("establishment");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false;
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    Establishment est = accountSnapshot.getValue(Establishment.class);
                    if(est.getId_owner().equals(mEstablishmentId)){
                        establishment = est;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MyEstablishementActivity.this, "Erreur lors de la récupération des comptes", Toast.LENGTH_SHORT).show();
                }

        });

    }
}
