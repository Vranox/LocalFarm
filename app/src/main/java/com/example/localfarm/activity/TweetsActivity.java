package com.example.localfarm.activity;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;


import androidx.recyclerview.widget.RecyclerView;


import com.example.localfarm.R;

import com.example.localfarm.models.Tweet;

import com.example.localfarm.recyclerview.TweetsAdapter;

import com.google.firebase.database.DatabaseReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import java.util.List;


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
                    URL url = new URL("https://twitter135.p.rapidapi.com/Search/?q=Dogecoin&count=5");
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
                        workJsonData(response.toString());
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

    public void workJsonData(String response){

    }
}
