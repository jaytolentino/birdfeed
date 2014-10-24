package com.codepath.apps.birdfeed.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.codepath.apps.birdfeed.utils.TimestampParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jay on 10/17/14.
 */
public class Tweet implements Serializable {
    private String body;
    private long tweetId;
    private String createdAt;
    private User user;
    private String retweetCount;
    private String favoriteCount;
    private String mediaUrl;


    public Tweet() {

    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.tweetId = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.retweetCount = jsonObject.getString("retweet_count");
            tweet.favoriteCount =  jsonObject.getString("favorite_count");
            if (jsonObject.has("extended_entities")) {
                tweet.setMediaUrl(jsonObject.getJSONObject("extended_entities"));
            } else {
                tweet.mediaUrl = null;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            }
            catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Tweet tweet = Tweet.fromJSON(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }

    @Override
    public String toString() {
        return getBody();
    }

    public String getBody() {
        return body;
    }

    public long getTweetId() {
        return tweetId;
    }

    public String getRelativeTimetamp() {
        return TimestampParser.getRelativeTimeAgo(createdAt);
    }

    public User getUser() {
        return user;
    }

    public String getRetweetCount() {
        return retweetCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public boolean hasMedia() {
        return mediaUrl != null;
    }

    private void setMediaUrl(JSONObject extEntities) {
        try {
            JSONArray media = extEntities.getJSONArray("media");
            JSONObject mediaObject = media.getJSONObject(0);
            mediaUrl = (String) mediaObject.get("media_url");
            Log.d("debug", "Media URL: " + mediaUrl);
        }
        catch (JSONException e) {
            e.printStackTrace();
            mediaUrl = null;
        }
    }
}