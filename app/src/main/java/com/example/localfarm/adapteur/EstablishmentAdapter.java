package com.example.localfarm.adapteur;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.localfarm.R;
import com.example.localfarm.activity.EstablishmentProfile;
import com.example.localfarm.models.actor.Establishment;
import com.example.localfarm.models.actor.EstablishmentWithDistance;
import com.example.localfarm.models.common.DayOfWeek;
import com.example.localfarm.models.common.Schedule;
import com.example.localfarm.models.common.Time;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.ViewHolder> {
    private List<EstablishmentWithDistance> establishmentsWithDistance;

    public EstablishmentAdapter(List<EstablishmentWithDistance> establishmentsWithDistance) {
        this.establishmentsWithDistance = establishmentsWithDistance;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EstablishmentWithDistance establishmentWithDistance = establishmentsWithDistance.get(position);
        holder.bind(establishmentWithDistance);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView nameTextView;
        TextView distanceTextView;
        TextView isOpenTextView;
        TextView closingTimeTextView;
        TextView locationView;

        ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            nameTextView = itemView.findViewById(R.id.textView);
            distanceTextView = itemView.findViewById(R.id.textDistance);
            isOpenTextView = itemView.findViewById(R.id.textSchedulePart1);
            closingTimeTextView = itemView.findViewById(R.id.textSchedulePart2);
            locationView = itemView.findViewById(R.id.locationView);
        }

        void bind(final EstablishmentWithDistance establishmentWithDistance) {
            Establishment establishment = establishmentWithDistance.getEstablishment();
            // Bind data to views
            nameTextView.setText(establishment.getTitle());
            distanceTextView.setText(String.format("%.2f km", establishmentWithDistance.distance / 1000));
            Time timeOfNow = new Time();
            Schedule schedule = establishment.getHoraires(DayOfWeek.valueOf(timeOfNow.getDayOfWeek()).getFrenchName());
            Geocoder geocoder = new Geocoder(itemView.getContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(establishment.position.getLat(), establishment.position.getLng(), 1);
                if (addresses != null && !addresses.isEmpty()) {
                    locationView.setText(addresses.get(0).getLocality());
                } else {
                    locationView.setText("Unknown location");
                }
            } catch (IOException e) {
                e.printStackTrace();
                locationView.setText("Cannot get location");
            }


            if (schedule != null) {
                if (schedule.isAvailable(timeOfNow)) {
                    isOpenTextView.setText("Ouvert");
                    isOpenTextView.setTextColor(itemView.getResources().getColor(R.color.confirm_green));
                    closingTimeTextView.setText("• Ferme à " + schedule.getCloseTime(timeOfNow));
                } else {
                    isOpenTextView.setText("Fermé");
                    isOpenTextView.setTextColor(itemView.getResources().getColor(R.color.red));
                    closingTimeTextView.setText("• Ouvre à " + schedule.getOpenTime(timeOfNow));
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
                    Intent intent = new Intent(v.getContext(), EstablishmentProfile.class);
                    intent.putExtra("selected_establishment_index", getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etablishment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return establishmentsWithDistance.size();
    }
}
