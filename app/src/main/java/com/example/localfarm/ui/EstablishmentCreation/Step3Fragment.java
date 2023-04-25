package com.example.localfarm.ui.EstablishmentCreation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.localfarm.R;

public class Step3Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step3, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button nextButton = requireActivity().findViewById(R.id.next_button);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainer);
        Navigation.setViewNavController(nextButton, navController);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = Step3FragmentDirections.actionStep3ToSubmit();
                Navigation.findNavController(view).navigate(action);
                nextButton.setText("Créer");
                nextButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.confirm_green));
                ImageView circle1 = requireActivity().findViewById(R.id.step3Circle);
                circle1.setImageResource(R.drawable.baseline_circle_variant_24);
                ImageView circle2 = requireActivity().findViewById(R.id.step4Circle);
                circle2.setImageResource(R.drawable.baseline_circle_24);

            }
        });
        Button prevButton = requireActivity().findViewById(R.id.prev_button);
        Navigation.setViewNavController(prevButton, navController);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = Step3FragmentDirections.actionStep3ToStep2();
                Navigation.findNavController(view).navigate(action);
                ImageView circle1 = requireActivity().findViewById(R.id.step3Circle);
                circle1.setImageResource(R.drawable.baseline_circle_variant_24);
                ImageView circle2 = requireActivity().findViewById(R.id.step2Circle);
                circle2.setImageResource(R.drawable.baseline_circle_24);

            }
        });
    }
}
