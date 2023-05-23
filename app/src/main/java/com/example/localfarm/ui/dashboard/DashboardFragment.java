package com.example.localfarm.ui.dashboard;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.activity.EstablishmentCreationActivity;
import com.example.localfarm.activity.MapActivity;
import com.example.localfarm.activity.TweetsActivity;
import com.example.localfarm.databinding.FragmentDashboardBinding;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.EstablishmentWithDistance;
import com.example.localfarm.models.Schedule;
import com.example.localfarm.models.Time;
import com.example.localfarm.recyclerview.EstablishmentAdapter;
import com.example.localfarm.singleton.EstablishmentManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private EstablishmentAdapter adapter;
    private FragmentDashboardBinding binding;
    private DatabaseReference establishmentRef;
    private List<Establishment> establishments = new ArrayList<>();

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
        establishmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                establishments = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
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
                                        establishmentLocation.setLatitude(establishment.getPosition().getLat());
                                        establishmentLocation.setLongitude(establishment.getPosition().getLng());
                                        float distance = location.distanceTo(establishmentLocation);
                                        establishmentsWithDistance.add(new EstablishmentWithDistance(establishment, distance));
                                    }
                                    Collections.sort(establishmentsWithDistance, (e1, e2) -> Float.compare(e1.distance, e2.distance));
                                    EstablishmentManager.getInstance().setEstablishments(establishmentsWithDistance);
                                    RecyclerView.Adapter adapter = new EstablishmentAdapter(establishmentsWithDistance);
                                    recyclerView.setAdapter(adapter);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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