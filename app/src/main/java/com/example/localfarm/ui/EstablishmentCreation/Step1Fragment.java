package com.example.localfarm.ui.EstablishmentCreation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.localfarm.R;
import com.example.localfarm.models.Establishment;

public class Step1Fragment extends Fragment {
    private OnDataChangeListener mOnDataChangeListener;
    EditText establishmentName;
    EditText establishmentDescription;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button nextButton = requireActivity().findViewById(R.id.next_button);
        Button prevButton = requireActivity().findViewById(R.id.prev_button);
        establishmentName = requireActivity().findViewById(R.id.editText1);
        establishmentDescription = requireActivity().findViewById(R.id.editText2);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainer);
        Navigation.setViewNavController(nextButton, navController);
        if(mOnDataChangeListener.getEstablishment() != null) {
            establishmentName.setText(mOnDataChangeListener.getEstablishment().getTitle());
            establishmentDescription.setText(mOnDataChangeListener.getEstablishment().getDescription());
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = Step1FragmentDirections.actionStep1ToStep2();
                Navigation.findNavController(view).navigate(action);
                prevButton.setVisibility(View.VISIBLE);
                ImageView circle1 = requireActivity().findViewById(R.id.step1Circle);
                circle1.setImageResource(R.drawable.baseline_circle_variant_24);
                ImageView circle2 = requireActivity().findViewById(R.id.step2Circle);
                circle2.setImageResource(R.drawable.baseline_circle_24);
                Establishment establishment;
                if(mOnDataChangeListener.getEstablishment()==null)
                    establishment = new Establishment();
                else
                    establishment = mOnDataChangeListener.getEstablishment();
                establishment.setTitle(establishmentName.getText().toString());
                establishment.setDescription(establishmentDescription.getText().toString());
                mOnDataChangeListener.onDataChanged(establishment);
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDataChangeListener = (OnDataChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataChangeListener");
        }
    }
}
