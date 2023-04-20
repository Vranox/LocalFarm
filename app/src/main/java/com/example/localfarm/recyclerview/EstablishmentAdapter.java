package com.example.localfarm.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Time;

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
            distanceTextView.setText("1km");
            isOpenTextView.setText(establishment.getHoraires().isAvailable(new Time())? "Ouvert" : "Fermé");
            closingTimeTextView.setText(establishment.getHoraires().isAvailable(new Time())?" Ferme à " + establishment.getHoraires().getCloseTime(new Time()) : "Ouvre à " + new Time());

            // Set click listener on the view
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
