package com.codepath.apps.birdfeed.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.birdfeed.R;
import com.codepath.apps.birdfeed.adapters.EndlessScrollListener;
import com.codepath.apps.birdfeed.adapters.TweetsAdapter;
import com.codepath.apps.birdfeed.fragments.ComposeTweetFragment;
import com.codepath.apps.birdfeed.models.Tweet;
import com.codepath.apps.birdfeed.networking.TwitterApplication;
import com.codepath.apps.birdfeed.networking.TwitterClient;
import com.codepath.apps.birdfeed.utils.ProgressBarHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class FeedActivity extends ActionBarActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsAdapter aTweets;
    private ListView lvTweets;
    private String earliestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBarHandler.create(this);
        setContentView(R.layout.activity_feed);
        client = TwitterApplication.getRestClient();

        initializeMemberVariables();
        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    private void toast() {
        Toast.makeText(this, "woah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onComposeTweet(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("title", "Compose New Tweet");

        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment composeTweet = ComposeTweetFragment.newInstance("Write a new tweet");
        composeTweet.setArguments(bundle);
        composeTweet.show(fm, "fragment_compose_tweet");
    }

    public void refreshTimeline() {
        aTweets.clear();
        populateTimeline();
    }

    private void populateTimeline() {
        ProgressBarHandler.showProgressBar(this);
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                aTweets.addAll(Tweet.fromJSONArray(json));
                earliestId = String.valueOf(aTweets.getItem(tweets.size() - 1).getUid());
                Log.d("debug", "ID: " + earliestId);
                ProgressBarHandler.hideProgressBar(FeedActivity.this);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s);
            }
        });
    }

    private void initializeMemberVariables() {
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        setupTweetScroll();
    }

    // TODO: possible to reduce repeated code here?
    private void setupTweetScroll() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                client.getHomeTimeline(earliestId, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONArray json) {
                        aTweets.addAll(Tweet.fromJSONArray(json));
                        earliestId = String.valueOf(aTweets.getItem(tweets.size() - 1).getUid());
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        Log.d("debug", throwable.toString());
                        Log.d("debug", s);
                    }
                });
            }
        });
    }
}
