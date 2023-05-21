package com.example.localfarm.activity;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.localfarm.R;
import com.example.localfarm.models.Account;
import com.example.localfarm.models.DayOfWeek;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.EstablishmentWithDistance;
import com.example.localfarm.models.Schedule;
import com.example.localfarm.models.Time;
import com.example.localfarm.singleton.EstablishmentManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EstablishmentProfile extends AppCompatActivity {
    ImageView profileImage;
    ImageView establishmentImage;
    TextView titleEstablishment;
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
    Account ownerAccount;
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
                    ownerAccount = new Account();
                }
                initializeViews();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void initializeViews() {
        establishmentImage = findViewById(R.id.establismentImage);
        titleEstablishment = findViewById(R.id.establishmentName);
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
}
