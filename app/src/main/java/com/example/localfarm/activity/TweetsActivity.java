package com.example.localfarm.activity;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.localfarm.R;


import com.example.localfarm.models.Tweet;

import com.example.localfarm.adapteur.recyclerview.TweetsAdapter;

import com.google.firebase.database.DatabaseReference;

import com.example.localfarm.models.Tweet;
import com.example.localfarm.adapteur.recyclerview.TweetsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TweetsActivity extends Activity {

    private RecyclerView mRecyclerView;
    private TweetsAdapter mAdapter;
    private List<Tweet> mTweets;
    private DatabaseReference mDatabase;

    private static final String TAG = "TwitterDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://twitter135.p.rapidapi.com/v2/UserTweets/?id=1666119886706208769&count=40");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Définir les en-têtes de la requête
                    connection.setRequestProperty("X-Rapidapi-Key", "0817c2c3cbmsh6292acae860e9e2p151a9ejsn40fd1a9b40e5");
                    connection.setRequestProperty("X-Rapidapi-Host", "twitter135.p.rapidapi.com");

                    // Envoyer la requête GET
                    connection.setRequestMethod("GET");

                    // Lire la réponse
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();

                        // Afficher la réponse dans les logs
                        Log.d("HTTP Response", response.toString());
                        ArrayList<Tweet> tweetsList = workJsonData(response.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                printTweetsOnPage(tweetsList);
                            }
                        });

                    } else {
                        Log.e("HTTP Error", "Response Code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public ArrayList<Tweet> workJsonData(String response){

        ArrayList<Tweet> tweets = new ArrayList<>();
        String regex = "full_text.*?is_quote";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String match = matcher.group();
            match = match.replace("full_text\":\"", "");
            match = match.replace("\",\"is_quote","");
            match = match.replace("\\\\n", "");

            Pattern unicodePattern = Pattern.compile("\\\\u[0-9a-fA-F]{4}");
            Matcher unicodeMatcher = unicodePattern.matcher(match);
            match = unicodeMatcher.replaceAll("");

            Log.d("TEST MATCH", match);
            tweets.add(new Tweet(match));
        }

        return tweets;
    }

    public void printTweetsOnPage(ArrayList<Tweet> tweets){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_tweets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Ajoutez cette ligne pour définir le gestionnaire de disposition
        TweetsAdapter adapter = new TweetsAdapter(tweets); // tweetList est une liste d'objets Tweet
        recyclerView.setAdapter(adapter);

    }
}
