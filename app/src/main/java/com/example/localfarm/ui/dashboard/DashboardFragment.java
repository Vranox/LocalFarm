package com.example.localfarm.ui.dashboard;

import android.content.Intent;
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
import com.example.localfarm.activity.FragmentProductCommandList;
import com.example.localfarm.activity.ListProductOrders;
import com.example.localfarm.activity.ProductPageActivity;
import com.example.localfarm.activity.TweetsActivity;
import com.example.localfarm.activity.UserListCommandActivity;
import com.example.localfarm.data.PricePerUnit;
import com.example.localfarm.data.Products;
import com.example.localfarm.data.QuantityUnits;
import com.example.localfarm.databinding.FragmentDashboardBinding;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Schedule;
import com.example.localfarm.models.Time;
import com.example.localfarm.recyclerview.EstablishmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
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
                System.out.println(establishments);
                adapter = new EstablishmentAdapter(establishments);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });


        Button buttonAdd = view.findViewById(R.id.buttonadd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EstablishmentCreationActivity.class);
                startActivity(intent);
            }
        });

        Button buttonTwitter = view.findViewById(R.id.button_twitter);
        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TweetsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void addEstablishment() {
//        System.out.println("establishmentRef: " + establishmentRef);
//        String key = establishmentRef.push().getKey();
//        System.out.println("Generated key: " + key);
//
//        Establishment newEstablishment = new Establishment("La ferme d'antan", "Ferme familial", new Schedule(new Time(8, 30), new Time(12, 30), new Time(13, 30), new Time(18, 30)));
//        System.out.println("New establishment: " + newEstablishment);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        establishmentRef.child(key).setValue(newEstablishment)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            System.out.println("Establishment added successfully");
//                        } else {
//                            System.out.println("Failed to add establishment: " + task.getException());
//                        }
//                    }
//                });
//    }
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