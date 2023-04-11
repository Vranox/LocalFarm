package com.example.myapplication;
// Import des bibliothèques nécessaires
import java.util.*;
import android.app.*;
import android.os.*;
import android.widget.*;
import twitter4j.*;
import twitter4j.conf.*;

public class TwitterActivity extends Activity {
    // Déclaration de la ListView
    private ListView mListViewTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_layout);

        // Récupération de la ListView
        mListViewTweets = (ListView) findViewById(R.id.listview_tweets);

        // Configuration de l'API Twitter
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("ktOfnu9vdbtdQ7cFGyiiB12NA")
                .setOAuthConsumerSecret("uoUFDc3p40dXXE94SnwXZnZHIVrJVf2vDfrR1pTdMBGJwOtc3j")
                .setOAuthAccessToken("753665181750792192-coRVLyMVkFIq967ds2oxVOKj6j04k0X")
                .setOAuthAccessTokenSecret("cZJXdqKEbVPeI6fhXP7cHQE41hoOfZvfPdnZtp60ZQZoS");

        // Création de l'objet Twitter avec la configuration
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();

        // Recherche des tweets avec le hashtag #food
        Query query = new Query("#food");
        query.setCount(10); // Limite à 10 tweets
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }

        // Création d'une liste de tweets
        List<String> tweets = new ArrayList<String>();
        for (Status status : result.getTweets()) {
            tweets.add("@" + status.getUser().getScreenName() + ": " + status.getText());
        }

        // Création d'un adaptateur pour la ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, tweets);

        // Attribution de l'adaptateur à la ListView
        mListViewTweets.setAdapter(adapter);
    }
}
