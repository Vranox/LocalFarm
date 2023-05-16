package com.example.localfarm.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.EstablishmentWithDistance;
import com.example.localfarm.recyclerview.EstablishmentAdapter;
import com.example.localfarm.singleton.EstablishmentManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private List<EstablishmentWithDistance> establishmentsWithDistance;
    private RecyclerView recyclerView;
    private EstablishmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get the establishments data
        // This is simplified for the example; you will probably be fetching this data from a database or API
        establishmentsWithDistance = EstablishmentManager.getInstance().getEstablishments();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EstablishmentAdapter(establishmentsWithDistance);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 12.0f;
        for (int i = 0; i < establishmentsWithDistance.size(); i++) {
            Establishment establishment = establishmentsWithDistance.get(i).getEstablishment();
            LatLng position = new LatLng(establishment.getPosition().getLat(), establishment.getPosition().getLng());
            Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(establishment.getTitle()));
            marker.setTag(i);  // Store the index of the establishment in the marker for later use

            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    int position = (int) marker.getTag();
                    recyclerView.smoothScrollToPosition(position);
                    return false;
                }
            });
            LatLng nearestPoint = new LatLng(establishmentsWithDistance.get(0).getEstablishment().getPosition().getLat(), establishmentsWithDistance.get(0).getEstablishment().getPosition().getLng());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestPoint, zoomLevel));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

