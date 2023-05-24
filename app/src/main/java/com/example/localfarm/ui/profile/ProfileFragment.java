package com.example.localfarm.ui.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.localfarm.R;
import com.example.localfarm.activity.EstablishmentCreationActivity;
import com.example.localfarm.activity.HomepageConnectionActivity;
import com.example.localfarm.adapteur.recyclerview.ProfileAdapter;

public class ProfileFragment extends Fragment {
    private ListView lvOptions;
    private Button btnLogout;
    private CardView cardView;
    private Button btnAddEstablishment2;

    private SharedPreferences sharedPreferences;
    private static final String CARD_VIEW_VISIBILITY_KEY = "card_view_visibility";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        lvOptions = root.findViewById(R.id.lvOptions);
        btnLogout = root.findViewById(R.id.btnLogout);
        cardView = root.findViewById(R.id.cardView);
        btnAddEstablishment2 = root.findViewById(R.id.btnAddEstablishment2);

        // Adapter pour la ListView
        String[] options = {"Modifier profil", "Notification", "Historique", "Chat"};
        int[] icons = {R.drawable.ic_edit, R.drawable.ic_notification, R.drawable.ic_history, R.drawable.ic_chat};
        ProfileAdapter adapter = new ProfileAdapter(getContext(), options, icons);
        lvOptions.setAdapter(adapter);

        // Écouteur d'événements pour la ListView
        lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Code à exécuter lorsque l'option est cliquée.
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String email = sharedPrefs.getString("email", "");
        String name = sharedPrefs.getString("name", "");
        String surname = sharedPrefs.getString("surname", "");
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvSurname = view.findViewById(R.id.tvSurname);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        tvName.setText(name);
        tvSurname.setText(surname);
        tvEmail.setText(email);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getContext(), HomepageConnectionActivity.class); // Intent pour démarrer HomepageConnectionActivity
                startActivity(intent);
                requireActivity().finish();
            }
        });
        boolean isCardViewVisible = sharedPreferences.getBoolean(CARD_VIEW_VISIBILITY_KEY, true);

        if (!isCardViewVisible) {
            cardView.setVisibility(View.GONE);
            btnAddEstablishment2.setVisibility(View.VISIBLE);
        }

        ImageButton btnMinimize = view.findViewById(R.id.btnMinimize);

        btnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int initialHeight = cardView.getMeasuredHeight();
                ValueAnimator animator = slideAnimator(initialHeight, 0, cardView);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cardView.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean(CARD_VIEW_VISIBILITY_KEY, false).apply();
                    }
                });
                animator.start();
                btnAddEstablishment2.setVisibility(View.VISIBLE);
            }
        });
        btnAddEstablishment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEstablishment();
            }
        });
        Button btnAddEstablishment = view.findViewById(R.id.btnAddEstablishment);
        btnAddEstablishment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEstablishment();
            }
        });
    }

    private void addEstablishment() {
        Intent intent = new Intent(getContext(), EstablishmentCreationActivity.class);
        startActivity(intent);
    }

    private ValueAnimator slideAnimator(int start, int end, final CardView cardView) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                layoutParams.height = value;
                cardView.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
