package com.example.localfarm.adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context mContext;

    public TweetsAdapter(List<Tweet> tweets, Context context) {
        mTweets = tweets;
        mContext = context;
    }

    //Création de la vue du tweet
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_tweet, parent, false);
        return new ViewHolder(view);
    }

    //Remplissage de la vue avec les données du tweet
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        holder.mNomTextView.setText(tweet.getUser());
        holder.mTexteTextView.setText(tweet.getText());
        holder.mDateTextView.setText(tweet.getDate());
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //Classe pour contenir les éléments de la vue du tweet
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNomTextView;
        public TextView mTexteTextView;
        public TextView mDateTextView;

        public ViewHolder(View view) {
            super(view);
            mNomTextView = view.findViewById(R.id.text_view_user);
            mTexteTextView = view.findViewById(R.id.text_view_tweet);
            mDateTextView = view.findViewById(R.id.text_view_date);
        }
    }
}
