package com.example.localfarm.ui.EstablishmentCreation;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
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
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Schedule;
import com.example.localfarm.models.Time;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

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
    TextView Lundi;
    TextView Mardi;
    TextView Mercredi;
    TextView Jeudi;
    TextView Vendredi;
    TextView Samedi;
    TextView Dimanche;
    TextView currentDay;
    Button nextButton;
    List<TextView> dayView;
    List<EditText> editTexts;
    NavController navController;
    Button prevButton;
    Schedule currentDaySchedule;
    Establishment establishment;

    private OnDataChangeListener mOnDataChangeListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            root = inflater.inflate(R.layout.fragment_step2_landscape, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment_step2, container, false);
        }
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        initUIElements(view);
        establishment = mOnDataChangeListener.getEstablishment();
        currentDaySchedule = establishment.horaires.get("Lundi");
        currentDay = Lundi;
        // Load current Schedule
        refreshSchedule();

        // Set up navigation
        setupNavigation();

        // Set up listeners
        setupListeners();
    }

    private void setupListeners() {
        // Set up listeners for time pickers
        for(EditText editText : editTexts){
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTimePickerDialog(editText);
                }
            });
        }
        // Set up listeners for day of the week selection
        for(TextView dayOfTheWeek: dayView){
            dayOfTheWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentDaySchedule = establishment.horaires.get(getResources().getResourceEntryName(dayOfTheWeek.getId()));
                    currentDay = dayOfTheWeek;
                    refreshSchedule();
                }
            });
        }
        afternoonCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                currentDaySchedule.setAfternoonOpen(b);
                refreshSchedule();
            }
        });
        morningCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                currentDaySchedule.setMorningOpen(b);
                refreshSchedule();
            }
        });
        toggleOpenClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isDayOpen(b);
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
                mOnDataChangeListener.onDataChanged(establishment);
            }
        });

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
                mOnDataChangeListener.onDataChanged(establishment);
            }
        });
    }

    private void isDayOpen(boolean b) {
        if(b){
            textOpenClose.setText("Ouvert");
            textOpenClose.setTextColor(ContextCompat.getColor(requireContext(), R.color.confirm_green));
            morningIsActive(morningCheck.isChecked());
            afternoonIsActive(afternoonCheck.isChecked());
            morningCheck.setEnabled(true);
            afternoonCheck.setEnabled(true);
            currentDaySchedule.setDayOpen(true);
        }
        else{
            textOpenClose.setText("Fermé");
            textOpenClose.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
            morningIsActive(false);
            afternoonIsActive(false);
            morningCheck.setEnabled(false);
            afternoonCheck.setEnabled(false);
            currentDaySchedule.setDayOpen(false);
        }
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainer);
        Navigation.setViewNavController(nextButton, navController);
        Navigation.setViewNavController(prevButton, navController);
    }

    private void initUIElements(View view) {
        nextButton = requireActivity().findViewById(R.id.next_button);
        prevButton = requireActivity().findViewById(R.id.prev_button);
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
        editTexts = new ArrayList<>();
        editTexts.add(morningTime1);
        editTexts.add(morningTime2);
        editTexts.add(afternoonTime1);
        editTexts.add(afternoonTime2);
        Lundi = getActivity().findViewById(R.id.Lundi);
        Mardi = getActivity().findViewById(R.id.Mardi);
        Mercredi = getActivity().findViewById(R.id.Mercredi);
        Jeudi = getActivity().findViewById(R.id.Jeudi);
        Vendredi = getActivity().findViewById(R.id.Vendredi);
        Samedi = getActivity().findViewById(R.id.Samedi);
        Dimanche = getActivity().findViewById(R.id.Dimanche);
        dayView = new ArrayList<>();
        dayView.add(Lundi);
        dayView.add(Mardi);
        dayView.add(Mercredi);
        dayView.add(Jeudi);
        dayView.add(Vendredi);
        dayView.add(Samedi);
        dayView.add(Dimanche);
        textOpenClose = getActivity().findViewById(R.id.textOpenclose);
        toggleOpenClose = getActivity().findViewById(R.id.toggleOpenClose);
    }

    private void refreshSchedule() {
        morningTime1.setText(currentDaySchedule.getStartMorning().toString());
        morningTime2.setText(currentDaySchedule.getEndMorning().toString());
        afternoonTime1.setText(currentDaySchedule.getStartAfternoon().toString());
        afternoonTime2.setText(currentDaySchedule.getEndAfternoon().toString());
        morningIsActive(currentDaySchedule.isMorningOpen());
        afternoonIsActive(currentDaySchedule.isAfternoonOpen());
        isDayOpen(currentDaySchedule.isDayOpen());
        toggleOpenClose.setChecked(currentDaySchedule.isDayOpen());
        for(TextView dayOfTheWeek: dayView){
            Schedule scheduleOfTheDay = establishment.horaires.get(getResources().getResourceEntryName(dayOfTheWeek.getId()));
            dayOfTheWeek.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            if(scheduleOfTheDay.isDayOpen()){
                dayOfTheWeek.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_background));
            }
            else{
                dayOfTheWeek.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.search_view_background));
            }
            if(currentDay == dayOfTheWeek){
                currentDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.open_day_background));
                currentDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            }
        }
    }

    private void showTimePickerDialog(EditText editTimeView) {
        int hour = Integer.parseInt(editTimeView.getText().toString().split(":")[0]);
        int min = Integer.parseInt(editTimeView.getText().toString().split(":")[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute) -> {
                    editTimeView.setText(String.format("%02d:%02d", hourOfDay, minute));
                    if(editTimeView == morningTime1){
                        currentDaySchedule.setStartMorning(new Time(hourOfDay, minute));
                    }
                    else if(editTimeView == morningTime2){
                        currentDaySchedule.setEndMorning(new Time(hourOfDay, minute));
                    }
                    else if(editTimeView == afternoonTime1){
                        currentDaySchedule.setStartAfternoon(new Time(hourOfDay, minute));
                    }
                    else if(editTimeView == afternoonTime2){
                        currentDaySchedule.setEndAfternoon(new Time(hourOfDay, minute));
                    }
                    refreshSchedule();
                }, hour, min, true); // Initialize with 0 hours and 0 minutes, and 24-hour format
        timePickerDialog.show();
    }

    private void afternoonIsActive(boolean b) {
        if(b){
            afternoonTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_700));
            a2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            afternoonTime1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            afternoonTime2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            afternoonTime1.setEnabled(true);
            afternoonTime2.setEnabled(true);
        }
        else{
            afternoonTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            a2.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            afternoonTime1.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            afternoonTime2.setTextColor(ContextCompat.getColor(getContext(), R.color.desactivate));
            afternoonTime1.setEnabled(false);
            afternoonTime2.setEnabled(false);

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
