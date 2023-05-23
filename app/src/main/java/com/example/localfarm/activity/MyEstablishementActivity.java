package com.example.localfarm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.localfarm.R;
import com.example.localfarm.models.Account;
import com.example.localfarm.models.Establishment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyEstablishementActivity extends Activity {

    private DatabaseReference mDatabase;
    private Establishment establishment;
    private SharedPreferences mPrefs;
    private String mEstablishmentId;
    private EditText editTitle;
    private EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myestablishement_layout);

        // Récupération de l'id de l'établissement depuis les SharedPreferences
        mPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        mEstablishmentId = mPrefs.getString("id", "");

        mDatabase = FirebaseDatabase.getInstance().getReference("establishment");

        editTitle = findViewById(R.id.edit_title_establishment);
        editDescription = findViewById(R.id.edit_description_establishment);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false;
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    Establishment est = accountSnapshot.getValue(Establishment.class);
                    if (est.getId_owner().equals(mEstablishmentId)) {
                        establishment = est;
                        editTitle.setText(establishment.getTitle());
                        editDescription.setText(establishment.getDescription());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    Toast.makeText(MyEstablishementActivity.this, "Aucun établissement trouvé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyEstablishementActivity.this, "Erreur lors de la récupération de l'établissement", Toast.LENGTH_SHORT).show();
            }
        });


        //Set-up le comportement du bouton validation des modifications


    }

}
