package com.example.localfarm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.localfarm.R;
import com.example.localfarm.models.Account;
import com.example.localfarm.ui.dashboard.DashboardFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
public class CreateAccountActivity extends AppCompatActivity{

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configuration de la référence à la base de données Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        EditText emailEditText = findViewById(R.id.create_account_email);
        EditText passwordEditText = findViewById(R.id.create_account_password);
        EditText phoneEditText = findViewById(R.id.create_account_phone);
        EditText nameEditText = findViewById(R.id.create_account_name);

        Button createAccount = findViewById(R.id.create_account_validate);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String id = UUID.randomUUID().toString();
                Account newAccount = new Account(email, password, phone, name, id);
                mDatabase.child("account").push().setValue(newAccount);

                SharedPreferences sharedPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putString("phone", phone);
                editor.putString("name", name);
                editor.apply();

                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class); // Intent pour démarrer HomepageConnectionActivity
                startActivity(intent); // Démarrer HomepageConnectionActivity
            }
        });
    }

    // Le reste de votre code...
}

