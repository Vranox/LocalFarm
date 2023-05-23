package com.example.localfarm.ui.dashboard;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.activity.EstablishmentCreationActivity;
import com.example.localfarm.activity.FragmentProductCommandList;
import com.example.localfarm.activity.ListProductOrders;
import com.example.localfarm.activity.ProductPageActivity;
import com.example.localfarm.activity.MapActivity;
import com.example.localfarm.activity.TweetsActivity;
import com.example.localfarm.activity.UserListCommandActivity;
import com.example.localfarm.data.PricePerUnit;
import com.example.localfarm.data.Products;
import com.example.localfarm.data.QuantityUnits;
import com.example.localfarm.databinding.FragmentDashboardBinding;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.EstablishmentWithDistance;
import com.example.localfarm.models.Position;
import com.example.localfarm.recyclerview.EstablishmentAdapter;
import com.example.localfarm.singleton.EstablishmentManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private EstablishmentAdapter adapter;
    private FragmentDashboardBinding binding;
    private DatabaseReference establishmentRef;
    private PlacesClient placesClient;
    String addressResult;
    String apiKey = "AIzaSyBWgAYTDL5VS5kp4DTwjJ1S8wFmf2PzBmw";
    private LatLng coordinates;
    private Position position;
    View popupView;
    private List<Establishment> establishments = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        establishmentRef = database.getReference("establishment");
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                        addressResult = place.getAddress();
                        coordinates = place.getLatLng();
                        printResult();
                        if (coordinates != null) {
                            position = new Position(coordinates.latitude, coordinates.longitude, place.getAddress());
                        }
                        List<EstablishmentWithDistance> establishmentsWithDistance = new ArrayList<>();
                        for (Establishment establishment : establishments) {
                            Location establishmentLocation = new Location("");
                            establishmentLocation.setLatitude(establishment.getPosition().getLat());
                            establishmentLocation.setLongitude(establishment.getPosition().getLng());
                            Location newLocation = new Location("");
                            newLocation.setLatitude(coordinates.latitude);
                            newLocation.setLongitude(coordinates.longitude);
                            float distance = newLocation.distanceTo(establishmentLocation);
                            establishmentsWithDistance.add(new EstablishmentWithDistance(establishment, distance));
                        }
                        Collections.sort(establishmentsWithDistance, (e1, e2) -> Float.compare(e1.distance, e2.distance));
                        EstablishmentManager.getInstance().setEstablishments(establishmentsWithDistance);
                        RecyclerView.Adapter adapter = new EstablishmentAdapter(establishmentsWithDistance);
                        recyclerView.setAdapter(adapter);
                    } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                        Status status = Autocomplete.getStatusFromIntent(result.getData());
                        Log.i(TAG, status.getStatusMessage());

                    }
                }
        );

        establishmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                establishments = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Establishment establishment = snapshot.getValue(Establishment.class);
                    establishments.add(establishment);
                }

                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    List<EstablishmentWithDistance> establishmentsWithDistance = new ArrayList<>();
                                    for (Establishment establishment : establishments) {
                                        Location establishmentLocation = new Location("");
                                        System.out.println(establishment.toString());
                                        establishmentLocation.setLatitude(establishment.getPosition().getLat());
                                        establishmentLocation.setLongitude(establishment.getPosition().getLng());
                                        float distance = location.distanceTo(establishmentLocation);
                                        establishmentsWithDistance.add(new EstablishmentWithDistance(establishment, distance));
                                    }
                                    Collections.sort(establishmentsWithDistance, (e1, e2) -> Float.compare(e1.distance, e2.distance));
                                    EstablishmentManager.getInstance().setEstablishments(establishmentsWithDistance);
                                    RecyclerView.Adapter adapter = new EstablishmentAdapter(establishmentsWithDistance);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    showAddressPopup();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        Button buttonTwitter = view.findViewById(R.id.button_twitter);
        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void printResult() {
        EditText addressInput = popupView.findViewById(R.id.edit_text_address);
        addressInput.setText(addressResult);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showAddressPopup() {
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey);
        }
        placesClient = Places.createClient(requireContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nous n'avons pas pu vous localiser");
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_address, null);
        builder.setView(popupView);
        EditText addressInput = popupView.findViewById(R.id.edit_text_address);
        addressInput.setOnClickListener(v -> startAutocompleteIntent());
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String address = addressInput.getText().toString();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void startAutocompleteIntent() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireContext());
        activityResultLauncher.launch(intent);
    }

    private void getEstablishment() {
        establishmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                establishments = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Establishment establishment = snapshot.getValue(Establishment.class);
                    establishments.add(establishment);
                }
                System.out.println(establishments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
}
