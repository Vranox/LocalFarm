package com.example.localfarm.ui.EstablishmentCreation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.localfarm.R;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Position;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class Step3Fragment extends Fragment {
    AutoCompleteTextView addressInput;
    String apiKey = "AIzaSyBWgAYTDL5VS5kp4DTwjJ1S8wFmf2PzBmw";
    private PlacesClient placesClient;
    private static final String TAG = "ADDRESS_AUTOCOMPLETE";
    private LatLng coordinates;
    Establishment establishment;

    private OnDataChangeListener mOnDataChangeListener;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                        addressInput.setText(place.getAddress());
                        coordinates = place.getLatLng();

                        if (coordinates != null) {
                            establishment.setPosition(new Position(coordinates.latitude, coordinates.longitude, place.getAddress()));
                        }

                        Log.i(TAG, "Place: " + place.getAddress() + ", " + place.getId() + ", " + place.getLatLng());
                    } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                        Status status = Autocomplete.getStatusFromIntent(result.getData());
                        Log.i(TAG, status.getStatusMessage());
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step3, container, false);

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey);
        }
        placesClient = Places.createClient(requireContext());
        addressInput = view.findViewById(R.id.address_input);

        addressInput.setOnClickListener(v -> startAutocompleteIntent());

        return view;
    }
    private void startAutocompleteIntent() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        System.out.println("AUTO COMPLETE INTENT");
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireContext());
        activityResultLauncher.launch(intent);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        establishment = mOnDataChangeListener.getEstablishment();

        if (establishment.position != null) {
            addressInput.setText(establishment.position.address);
        }
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
