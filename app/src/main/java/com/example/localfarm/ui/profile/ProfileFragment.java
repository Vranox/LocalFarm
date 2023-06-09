package com.example.localfarm.ui.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.example.localfarm.adapteur.ProfileAdapter;
import com.example.localfarm.activity.MyEstablishementActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class ProfileFragment extends Fragment {
    private ListView lvOptions;
    private Button btnLogout;
    private CardView cardView;
    private Button btnAddEstablishment2;

    private SharedPreferences sharedPreferences;
    private static final String CARD_VIEW_VISIBILITY_KEY = "card_view_visibility";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            root = inflater.inflate(R.layout.fragment_profil_landscape, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment_profil, container, false);
        }

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


        //Modification du texte dans le bouton sur l'établissement en fonction de si le profil possède un établissement
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Boolean isOwner = sharedPrefs.getBoolean("isOwner",false);

        if(isOwner){
            Log.d("AAAAAAAA", "OUI OUI OUI");
            btnAddEstablishment2.setText("Modifier mon établissement");
        }else{
            Log.d("AAAAAAAA", "NON NON NON");
        }


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
        //On regarde ici quelle est la valeur écrite dans le bouton pour changer le comportement du on-click
        if(btnAddEstablishment2.getText().equals("Modifier mon établissement")){
            Intent intent = new Intent(getContext(), MyEstablishementActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getContext(), EstablishmentCreationActivity.class);
            startActivity(intent);
        }
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

    private void logout() {


        { //On retire de la data base l'ActiveToken lié a ce compte afin qu'il ne recoive plus de notification.
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            String id = sharedPrefs.getString("id", "");

            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    System.out.println("Messaging");
                    String TokenToSuppr = task.getResult();
                    FirebaseDatabase.getInstance().getReference("account").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()  {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task){
                            System.out.println("Database");
                            if (task.isSuccessful()) {
                                System.out.println("Database - succeess");
                                //On parcours tout les utilisateur et on regarde la valeur qu'ils ont dans leur attribut id
                                for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                                    String idFound = childSnapshot.child("id").getValue(String.class);
                                    System.out.println("id = "+childSnapshot.child("id").getValue(String.class));

                                    // Pour chaque utilisateur on regarde si il a l'id donnée en parametre de fonction
                                    if (idFound != null && idFound.equals(id)) {
                                        System.out.println("Found");


                                        for(DataSnapshot values : childSnapshot.child("ActiveToken").getChildren()){
                                            System.out.println(values.getValue(String.class) + " - " + TokenToSuppr);
                                            if(values.getValue(String.class).equals(TokenToSuppr)){
                                                values.getRef().removeValue();
                                                System.out.println("found");
                                            };

                                        }

                                        break;
                                    }
                                }
                            }

                        }
                    });

                }
            });
        }

        // Déconnectez l'utilisateur ici.
        // Redirigez l'utilisateur vers l'écran de connexion.
    }
}
