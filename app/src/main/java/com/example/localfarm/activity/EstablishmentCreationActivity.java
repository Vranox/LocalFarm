package com.example.localfarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.localfarm.R;
import com.example.localfarm.databinding.ActivityMainBinding;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.ui.EstablishmentCreation.OnDataChangeListener;
import com.example.localfarm.ui.EstablishmentCreation.Step1Fragment;

public class EstablishmentCreationActivity extends AppCompatActivity implements OnDataChangeListener {
    private NavController navController;
    Establishment establishment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the "up" button in the support ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragmentContainer);
        return NavigationUI.navigateUp(navController, (AppBarConfiguration) null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDataChanged(Object newData) {
        establishment = (Establishment) newData;
    }

    @Override
    public Establishment getEstablishment() {
        return establishment;
    }
}