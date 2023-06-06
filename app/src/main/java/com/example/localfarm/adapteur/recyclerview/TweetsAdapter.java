package com.example.localfarm.adapteur.recyclerview;

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

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetViewHolder> {
    private List<Tweet> tweetList;

    public class TweetViewHolder extends RecyclerView.ViewHolder {
        public TextView tweetContent;

        public TweetViewHolder(View view) {
            super(view);
            tweetContent = view.findViewById(R.id.tweet_content);
        }
    }

    public TweetsAdapter(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);

        return new TweetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = tweetList.get(position);
        holder.tweetContent.setText(tweet.getText());
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}
