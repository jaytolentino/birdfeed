package com.codepath.apps.birdfeed.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.birdfeed.R;
import com.codepath.apps.birdfeed.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

public class TweetDetailActivity extends Activity {
    private Tweet tweet;
    private ImageView ivDetailProfilePic;
    private ImageView ivDetailBackground;
    private TextView tvDetailName;
    private TextView tvDetailUsername;
    private TextView tvDetailTweet;
    private TextView tvDetailTimestamp;
    private TextView tvCountRetweets;
    private TextView tvCountFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        initailizeMemberVariables();
        setImageViews();
        setTextViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet_detail, menu);
        return true;
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void initailizeMemberVariables() {
        int tweetPosition = getIntent().getIntExtra("tweetPosition", 0);
        createTweetAtPosition(tweetPosition);

        ivDetailProfilePic = (ImageView) findViewById(R.id.ivDetailProfilePic);
        ivDetailBackground = (ImageView) findViewById(R.id.ivDetailBackground);

        tvDetailName = (TextView) findViewById(R.id.tvDetailName);
        tvDetailUsername = (TextView) findViewById(R.id.tvDetailUsername);
        tvDetailTweet = (TextView) findViewById(R.id.tvDetailTweet);
        tvDetailTimestamp = (TextView) findViewById(R.id.tvDetailTimestamp);
        tvCountRetweets = (TextView) findViewById(R.id.tvCountRetweets);
        tvCountFavorites = (TextView) findViewById(R.id.tvCountFavorites);
    }

    private void setImageViews() {
        ImageLoader loader = ImageLoader.getInstance();
        ivDetailBackground.setImageResource(android.R.color.transparent);
        loader.displayImage(tweet.getUser().getCoverImageUrl(), ivDetailBackground);

        ivDetailProfilePic.setImageResource(android.R.color.transparent);
        loader.displayImage(tweet.getUser().getProfileImageUrl(), ivDetailProfilePic);
    }

    private void setTextViews() {
        tvDetailName.setText(tweet.getUser().getName());
        tvDetailUsername.setText(tweet.getUser().getUsername());
        tvDetailTweet.setText(tweet.getBody());
        tvDetailTimestamp.setText(tweet.getRelativeTimetamp());
        tvCountRetweets.setText(tweet.getRetweetCount());
        tvCountFavorites.setText(tweet.getFavoriteCount());

    }

    private void createTweetAtPosition(int tweetPosition) {
        if (FeedActivity.hasTweets()) {
            tweet = FeedActivity.getTweet(tweetPosition);
        } else {
            throw new NullPointerException("Tweets deleted after process killed.");
        }
    }
}
