package com.example.localfarm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.create_account);
        } else {
            setContentView(R.layout.create_account_landscape);
        }

        EditText emailEditText = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);
        EditText phoneEditText = findViewById(R.id.phone_input);
        EditText nameEditText = findViewById(R.id.name_input);
        EditText surnameEditText = findViewById(R.id.surname_input);

        Button createAccount = findViewById(R.id.create_account_validate);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordInput.getText().toString();
                String phone = phoneEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String id = UUID.randomUUID().toString();
                Account newAccount = new Account(email, password, phone, name, surname, id);
                mDatabase.child("account").push().setValue(newAccount);

                SharedPreferences sharedPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putString("phone", phone);
                editor.putString("name", name);
                editor.putString("surname", surname);
                editor.apply();

                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        passwordInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_eye), null); // Set initial icon
        passwordInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        int selection = passwordInput.getSelectionEnd();
                        if (passwordInput.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_eye_closed), null);
                        } else {
                            passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_eye), null);
                        }
                        passwordInput.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    // Le reste de votre code...
}

