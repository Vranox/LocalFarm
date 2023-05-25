package com.example.localfarm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.localfarm.R;
import com.example.localfarm.adapteur.ProductEstablishmentProfilAdapter;
import com.example.localfarm.models.actor.Account;
import com.example.localfarm.models.actor.Establishment;
import com.example.localfarm.models.actor.EstablishmentWithDistance;
import com.example.localfarm.models.common.DayOfWeek;
import com.example.localfarm.models.common.Schedule;
import com.example.localfarm.models.common.Time;
import com.example.localfarm.models.products.Products;
import com.example.localfarm.singleton.EstablishmentManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EstablishmentProfile extends AppCompatActivity {
    ImageView profileImage;
    ImageView establishmentImage;
    TextView addressEstablishment;
    TextView descriptionEstablishment;
    TextView scheduleEstablishment;
    TextView nameProfile;
    TextView surnameProfile;
    TextView shoppingCount;
    TextView openCloseSchedule;
    Button buttonSchedule;
    Button buttonProfile;
    Button buttonRating;
    Button buttonMap;
    EstablishmentWithDistance selectedEstablishment;
    Establishment establishment;
    com.example.localfarm.models.actor.Account ownerAccount;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_profile);
        Intent intent = getIntent();
        int index = intent.getIntExtra("selected_establishment_index", -1);
        if (index != -1) {
            selectedEstablishment = EstablishmentManager.getInstance().getEstablishments().get(index);
            establishment = selectedEstablishment.getEstablishment();
        }
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("account");
        DatabaseReference userRef = usersRef.child(establishment.getId_owner());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ownerAccount = dataSnapshot.getValue(Account.class);

                }
                else {
                    ownerAccount = new com.example.localfarm.models.actor.Account();
                }
                initializeViews();
                RecyclerView productsRecyclerView = findViewById(R.id.products_recycler_view);
                productsRecyclerView.setLayoutManager(new LinearLayoutManager(EstablishmentProfile.this));

                List<Products> productsList = Products.staticList();
                ProductEstablishmentProfilAdapter adapter = new ProductEstablishmentProfilAdapter(EstablishmentProfile.this, productsList);
                productsRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void initializeViews() {
        establishmentImage = findViewById(R.id.establismentImage);
        addressEstablishment = findViewById(R.id.textAddress);
        profileImage = findViewById(R.id.ivProfile);
        descriptionEstablishment = findViewById(R.id.description);
        nameProfile= findViewById(R.id.tvName);
        surnameProfile = findViewById(R.id.tvSurname);
        scheduleEstablishment = findViewById(R.id.textSchedulePart2);
        openCloseSchedule = findViewById(R.id.textSchedulePart1);
        shoppingCount = findViewById(R.id.numberOfProduct);
        buttonSchedule = findViewById(R.id.schedule_button);
        buttonProfile = findViewById(R.id.profile_button);
        buttonMap = findViewById(R.id.map_button);
        buttonRating = findViewById(R.id.view_ratings_button);
        toolbar = findViewById(R.id.toolbar);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Schedule button clicked");
                showScheduleDialog(establishment);
            }
        });
        Glide.with(establishmentImage)
                .load(establishment.getImageUri())
                .into(establishmentImage);
        toolbar.setTitle("Profil de "+establishment.getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        descriptionEstablishment.setText(establishment.getDescription());
        addressEstablishment.setText(establishment.getPosition().address);
        nameProfile.setText(ownerAccount.getName());
        surnameProfile.setText(ownerAccount.getSurname());
        Time timeOfNow = new Time();
        Schedule schedule = establishment.getHoraires(DayOfWeek.valueOf(timeOfNow.getDayOfWeek()).getFrenchName());
                if (schedule != null) {
                    if (schedule.isAvailable(timeOfNow)) {
                        openCloseSchedule.setText("Ouvert");
                        openCloseSchedule.setTextColor(getResources().getColor(R.color.confirm_green));
                        scheduleEstablishment.setText("• Ferme à " + schedule.getCloseTime(timeOfNow));
                    } else {
                        openCloseSchedule.setText("Fermé");
                        openCloseSchedule.setTextColor(getResources().getColor(R.color.red));
                        scheduleEstablishment.setText("• Ouvre à " + schedule.getOpenTime(timeOfNow));
                    }
                }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showScheduleDialog(Establishment establishment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Horaires");

        // Define the order of the days of the week
        List<String> daysOfWeek = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");

        // Create a StringBuilder to build the schedule string
        StringBuilder scheduleBuilder = new StringBuilder();

        // Find the maximum length of the day names
        int maxDayNameLength = 0;
        for (String dayOfWeek : daysOfWeek) {
            int dayNameLength = dayOfWeek.length();
            if (dayNameLength > maxDayNameLength) {
                maxDayNameLength = dayNameLength;
            }
        }

        // Loop through each day of the week in the specified order and append the schedule information
        for (String dayOfWeek : daysOfWeek) {
            Schedule schedule = establishment.getHoraires(dayOfWeek);

            // Skip the day if it is not open
            if (!schedule.isDayOpen()) {
                continue;
            }

            // Calculate the number of spaces to add for alignment
            int numSpaces = maxDayNameLength - dayOfWeek.length();

            // Append the day of the week with additional spaces for alignment
            scheduleBuilder.append(dayOfWeek);
            for (int i = 0; i < numSpaces; i++) {
                scheduleBuilder.append(" ");
            }
            scheduleBuilder.append("  ");

            // Check if the day is open in the morning
            if (schedule.isMorningOpen()) {
                scheduleBuilder.append(schedule.getStartMorning())
                        .append("-")
                        .append(schedule.getEndMorning());
            } else {
                scheduleBuilder.append("Fermé");
            }

            // Append a dot if the day is open in the afternoon
            if (schedule.isAfternoonOpen()) {
                scheduleBuilder.append(" • ")
                        .append(schedule.getStartAfternoon())
                        .append("-")
                        .append(schedule.getEndAfternoon());
            }

            // Append a new line
            scheduleBuilder.append("\n");
        }

        // Remove the trailing new line character
        if (scheduleBuilder.length() > 0) {
            scheduleBuilder.deleteCharAt(scheduleBuilder.length() - 1);
        }

        // Set the schedule text in the dialog
        builder.setMessage(scheduleBuilder.toString());

        // Set a positive button to close the dialog
        builder.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
