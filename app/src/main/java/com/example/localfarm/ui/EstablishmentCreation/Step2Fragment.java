package com.example.localfarm.ui.EstablishmentCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.localfarm.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class Step2Fragment extends Fragment {
    CheckBox morningCheck;
    CheckBox afternoonCheck;
    EditText morningTime1;
    EditText morningTime2;
    EditText afternoonTime1;
    EditText afternoonTime2;
    TextView afternoonTitle;
    TextView morningTitle;
    TextView a1;
    TextView a2;
    TextView textOpenClose;
    SwitchMaterial toggleOpenClose;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step2, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button nextButton = requireActivity().findViewById(R.id.next_button);
        morningCheck = requireActivity().findViewById(R.id.morningCheck);
        afternoonCheck = requireActivity().findViewById(R.id.afternoonCheck);
        morningTime1 = requireActivity().findViewById(R.id.morningTime1);
        morningTime2 = requireActivity().findViewById(R.id.morningTime2);
        morningTitle = requireActivity().findViewById(R.id.morningTitle);
        afternoonTime1 = requireActivity().findViewById(R.id.afternoonTime1);
        afternoonTime2 = requireActivity().findViewById(R.id.afternoonTime2);
        afternoonTitle = getActivity().findViewById(R.id.afternoonTitle);
        a1 = getActivity().findViewById(R.id.a1);
        a2 = getActivity().findViewById(R.id.a2);
        textOpenClose = getActivity().findViewById(R.id.textOpenclose);
        toggleOpenClose = getActivity().findViewById(R.id.toggleOpenClose);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainer);
        Navigation.setViewNavController(nextButton, navController);
        afternoonCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                afternoonIsActive(b);
            }
        });
        morningCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                morningIsActive(b);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = Step2FragmentDirections.actionStep2ToStep3();
                Navigation.findNavController(view).navigate(action);
                ImageView circle1 = requireActivity().findViewById(R.id.step2Circle);
                circle1.setImageResource(R.drawable.baseline_circle_variant_24);
                ImageView circle2 = requireActivity().findViewById(R.id.step3Circle);
                circle2.setImageResource(R.drawable.baseline_circle_24);

            }
        });
        Button prevButton = requireActivity().findViewById(R.id.prev_button);
        Navigation.setViewNavController(prevButton, navController);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = Step2FragmentDirections.actionStep2ToStep1();
                Navigation.findNavController(view).navigate(action);
                prevButton.setVisibility(View.INVISIBLE);
                ImageView circle1 = requireActivity().findViewById(R.id.step2Circle);
                circle1.setImageResource(R.drawable.baseline_circle_variant_24);
                ImageView circle2 = requireActivity().findViewById(R.id.step1Circle);
                circle2.setImageResource(R.drawable.baseline_circle_24);
            }
        });
    }

    private void afternoonIsActive(boolean b) {
        if(b){
            afternoonTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_700));
            a2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            afternoonTime1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            afternoonTime2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
        else{
            afternoonTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            a2.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            afternoonTime1.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            afternoonTime2.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));

        }
    }
    private void morningIsActive(boolean b) {
        if(b){
            morningTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_700));
            a1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            morningTime1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            morningTime2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
        else{
            morningTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            a1.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            morningTime1.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            morningTime2.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
        }
    }
}
