package com.example.localfarm.ui.EstablishmentCreation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.localfarm.models.actor.Establishment;

public class Step1Fragment extends Fragment {
    private OnDataChangeListener mOnDataChangeListener;
    EditText establishmentName;
    EditText establishmentDescription;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            root = inflater.inflate(R.layout.fragment_step1_landscape, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment_step1, container, false);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button nextButton = requireActivity().findViewById(R.id.next_button);
        Button prevButton = requireActivity().findViewById(R.id.prev_button);
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                boolean enableButton = !establishmentName.getText().toString().trim().isEmpty() &&
                        !establishmentDescription.getText().toString().trim().isEmpty();
                nextButton.setEnabled(enableButton);
                nextButton.setAlpha(enableButton ? 1.0f : 0.5f);
            }
        };
        establishmentName = requireActivity().findViewById(R.id.editText1);
        establishmentDescription = requireActivity().findViewById(R.id.editText2);
        establishmentName.addTextChangedListener(textWatcher);
        establishmentDescription.addTextChangedListener(textWatcher);
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
                if(mOnDataChangeListener.getEstablishment()==null){
                    establishment = new Establishment();
                    establishment.setId_owner(getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE).getString("id", ""));
                }

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
