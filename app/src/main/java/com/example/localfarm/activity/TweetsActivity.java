package com.example.localfarm.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Tweet;
import com.example.localfarm.recyclerview.EstablishmentAdapter;
import com.example.localfarm.recyclerview.TweetsAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetsActivity extends Activity {

    private RecyclerView mRecyclerView;
    private TweetsAdapter mAdapter;
    private List<Tweet> mTweets;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        // Configuration de la référence à la base de données Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Configuration de la RecyclerView avec l'adapter
        mRecyclerView = findViewById(R.id.recycler_view_tweets);
        mTweets = new ArrayList<>();
        mAdapter = new TweetsAdapter(mTweets, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Récupération des tweets depuis Firebase
        mDatabase.child("tweets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTweets.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Tweet tweet = postSnapshot.getValue(Tweet.class);
                    mTweets.add(tweet);
                }
                // Notification à l'adapter que la liste de tweets a été mise à jour
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Simon_Tweet", "loadPost:onCancelled", databaseError.toException());
            }
        });

        // Ajout d'un listener sur le bouton flottant pour ajouter un nouveau tweet
        FloatingActionButton fab = findViewById(R.id.post_tweet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Affichage de la pop-up pour saisir le nom et le texte du tweet
                AlertDialog.Builder builder = new AlertDialog.Builder(TweetsActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_new_tweet, null);
                final TextView nameEditText = dialogView.findViewById(R.id.edit_text_name);
                final TextView tweetEditText = dialogView.findViewById(R.id.edit_text_tweet);
                builder.setView(dialogView)
                        .setPositiveButton("Post tweet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Récupération du nom et du texte saisis par l'utilisateur
                                String name = nameEditText.getText().toString();
                                String text = tweetEditText.getText().toString();
                                // Création d'un nouveau tweet avec la date actuelle et ajout à Firebase
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String currentDate = dateFormat.format(new Date());
                                Tweet newTweet = new Tweet(name, text, currentDate);
                                mDatabase.child("tweets").push().setValue(newTweet);
                            }
                        })
                        .setNegativeButton("Cancel tweet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Annulation de la création du tweet
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });
    }
}
