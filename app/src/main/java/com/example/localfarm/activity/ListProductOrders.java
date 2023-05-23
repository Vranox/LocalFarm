package com.example.localfarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localfarm.R;

public class ListProductOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_command_list_activity);


        FragmentProductCommandList fragment = FragmentProductCommandList.newInstance(null);
        // Utilisez le gestionnaire de fragments pour ajouter le fragment à l'activité
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ListProductOrders_ProductCommandList, fragment)
                .commit();

        // initialisation des vues et des listeners
        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent link = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(link);
            }
        });

        // ...
    }

    // autres méthodes nécessaires pour gérer l'activité

}

