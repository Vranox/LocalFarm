package com.example.localfarm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localfarm.R;
import com.example.localfarm.models.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        Button createAccount = findViewById(R.id.create_account_validate);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Account newAccount = new Account(email, password);
                mDatabase.child("account").push().setValue(newAccount);
            }
        });
    }

    // Le reste de votre code...
}

