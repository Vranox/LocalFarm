package com.example.localfarm.activity;

import android.app.Activity;
import android.content.Context;
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
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
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
        mDatabase = FirebaseDatabase.getInstance().getReference("tweets");

        // Création de la liste de tweets
        mTweets = new ArrayList<>();

        // Configuration de la RecyclerView avec l'adapter
        mRecyclerView = findViewById(R.id.recycler_view_tweets);
        mAdapter = new TweetsAdapter(mTweets, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ajout d'un listener sur le bouton flottant pour ajouter un nouveau tweet
        FloatingActionButton fab = findViewById(R.id.post_tweet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Création d'un nouveau tweet et ajout à Firebase
                Tweet newTweet = new Tweet("David", "This is a new tweet!", "2023-05-04");
                mDatabase.push().setValue(newTweet)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Notification à l'adapter que la liste de tweets a été mise à jour
                                mAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });

        // Récupération des tweets depuis Firebase
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Ajout du nouveau tweet à la liste de tweets
                Tweet tweet = dataSnapshot.getValue(Tweet.class);
                mTweets.add(tweet);
                // Notification à l'adapter que la liste de tweets a été mise à jour
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Simon_Tweet", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
