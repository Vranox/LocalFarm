package com.example.localfarm.factory;

import com.example.localfarm.factory.ConcreteTweet;
import com.example.localfarm.factory.Tweet;

public class TweetFactory {
    public static Tweet createTweet(String content) {
        return new ConcreteTweet(content);
    }
}
