package com.example.localfarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.fragment.app.FragmentActivity;

import com.example.localfarm.R;
import com.google.android.gms.maps.SupportMapFragment;

public class ListProductOrders extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_command_list_activity);

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

