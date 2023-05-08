package com.example.localfarm.activity;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localfarm.R;

public class HomepageConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                //Todo
            }
        });
    }

}
