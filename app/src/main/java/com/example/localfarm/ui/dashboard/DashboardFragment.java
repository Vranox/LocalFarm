package com.example.localfarm.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.localfarm.R;
import com.example.localfarm.databinding.FragmentDashboardBinding;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Schedule;
import com.example.localfarm.models.Time;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DatabaseReference establishmentRef;

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
        Button buttonAdd = view.findViewById(R.id.buttonadd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEstablishment();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void addEstablishment() {
        System.out.println("ADDDIINGGGGG");
        System.out.println("establishmentRef: " + establishmentRef);
        String key = establishmentRef.push().getKey();
        System.out.println("Generated key: " + key);

        Establishment newEstablishment = new Establishment("La ferme d'antan", "Ferme familial", new Schedule(new Time(8, 30), new Time(12, 30), new Time(13, 30), new Time(18, 30)));
        System.out.println("New establishment: " + newEstablishment);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("Establishment added successfully");
                } else {
                    System.out.println("Failed to add establishment: " + task.getException());
                }
            }
        });;
        establishmentRef.child(key).setValue(newEstablishment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Establishment added successfully");
                        } else {
                            System.out.println("Failed to add establishment: " + task.getException());
                        }
                    }
                });
    }
}