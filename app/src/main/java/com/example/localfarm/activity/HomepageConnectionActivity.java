package com.example.localfarm.activity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        SharedPreferences sharedPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        boolean rememberMe = sharedPrefs.getBoolean("rememberMe", false);
        if(rememberMe) {
            Intent intent = new Intent(HomepageConnectionActivity.this, MainActivity.class);
            startActivity(intent);
        }

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
        EditText passwordInput = findViewById(R.id.password_input);
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

                            CheckBox rememberMeCheckbox = findViewById(R.id.remember_me_checkbox);
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
                                if (rememberMeCheckbox.isChecked()) {
                                    editor.putBoolean("rememberMe", true);
                                } else {
                                    editor.putBoolean("rememberMe", false);
                                }
                                editor.apply();

                                break;
                            }
                        }
                        if (isFound) {
                            Toast.makeText(HomepageConnectionActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomepageConnectionActivity.this, MainActivity.class);
                            startActivity(intent);
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
