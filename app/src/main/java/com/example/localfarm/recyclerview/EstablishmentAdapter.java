package com.example.localfarm.recyclerview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.localfarm.R;
import com.example.localfarm.models.DayOfWeek;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Schedule;
import com.example.localfarm.models.Time;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.util.List;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.ViewHolder> {
    private List<Establishment> establishments;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView nameTextView;
        TextView distanceTextView;
        TextView isOpenTextView;
        TextView closingTimeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            nameTextView = itemView.findViewById(R.id.textView);
            distanceTextView = itemView.findViewById(R.id.textDistance);
            isOpenTextView = itemView.findViewById(R.id.textSchedulePart1);
            closingTimeTextView = itemView.findViewById(R.id.textSchedulePart2);
        }

        void bind(final Establishment establishment) {
            // Bind data to views
            nameTextView.setText(establishment.getTitle());
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(itemView.getContext());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Location establishmentLocation = new Location("");
                                establishmentLocation.setLatitude(establishment.getPosition().getLat());
                                establishmentLocation.setLongitude(establishment.getPosition().getLng());
                                System.out.println("Location : "+establishmentLocation.toString() + " " + location.toString());
                                float distanceInMeters = location.distanceTo(establishmentLocation);
                                distanceTextView.setText(String.format("%.2f km", distanceInMeters / 1000));
                            }
                        }
                    });
            Time timeOfNow = new Time();
            System.out.println("DAYOFWEEK: "+DayOfWeek.valueOf(timeOfNow.getDayOfWeek()).getFrenchName());
            Schedule schedule = establishment.getHoraires(DayOfWeek.valueOf(timeOfNow.getDayOfWeek()).getFrenchName());
            if (schedule != null) {
                if (schedule.isAvailable(timeOfNow)) {
                    isOpenTextView.setText("Ouvert");
                    closingTimeTextView.setText(" Ferme à " + schedule.getCloseTime(timeOfNow));
                } else {
                    isOpenTextView.setText("Fermé");
                    closingTimeTextView.setText("Ouvre à " + timeOfNow);
                }
            } else {
                isOpenTextView.setText("Non disponible");
                closingTimeTextView.setText("");
            }
            Glide.with(itemView)
                    .load(establishment.getImageUri())
                    .into(profileImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event
                }
            });
        }
    }


    public EstablishmentAdapter(List<Establishment> establishments) {
        this.establishments = establishments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etablishment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Establishment establishment = establishments.get(position);
        holder.bind(establishment);
    }

    @Override
    public int getItemCount() {
        return establishments.size();
    }
}
