package com.example.localfarm.activity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localfarm.R;
import com.example.localfarm.models.Account;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomepageConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.homepage_connection);

        // Création d'un compte
        Button createAccount = findViewById(R.id.button_create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageConnectionActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        //Connexion d'un compte
        Button connexionButton = findViewById(R.id.button_homepage_connexion);
        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEditText = findViewById(R.id.email_input);
                EditText passwordEditText = findViewById(R.id.password_input);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("account");
                accountsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean isFound = false;
                        for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                            Account account = accountSnapshot.getValue(Account.class);

                            if(account.getEmail().equals(email)) Log.d("Simon", "EMAIL");
                            if(account.getPassword().equals(password)) Log.d("Simon", "PASSWORD");


                            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                                // Le couple e-mail/mot de passe correspond à un compte dans la base de données
                                isFound = true;

                                SharedPreferences sharedPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPrefs.edit();
                                editor.putString("email", account.getEmail());
                                editor.putString("password", account.getPassword());
                                editor.putString("phone", account.getPhone());
                                editor.putString("name", account.getName());
                                editor.putString("id", account.getId());
                                editor.apply();

                                break;
                            }
                        }
                        if (isFound) {
                            Toast.makeText(HomepageConnectionActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomepageConnectionActivity.this, MainActivity.class); // Intent pour démarrer HomepageConnectionActivity
                            startActivity(intent); // Démarrer HomepageConnectionActivity
                        } else {
                            Toast.makeText(HomepageConnectionActivity.this, "E-mail ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(HomepageConnectionActivity.this, "Erreur lors de la récupération des comptes", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
