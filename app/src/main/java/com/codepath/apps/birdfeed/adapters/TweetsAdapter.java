package com.codepath.apps.birdfeed.adapters;

import android.content.Context;
import android.media.Image;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.birdfeed.R;
import com.codepath.apps.birdfeed.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jay on 10/17/14.
 */
public class TweetsAdapter extends ArrayAdapter<Tweet> {

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        TweetViewHolder holder;
        if (convertView == null) {
            holder = new TweetViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_tweet, parent, false);
            initializeViews(holder, convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (TweetViewHolder) convertView.getTag();
        }
        setViewContent(tweet, holder);
        return convertView;
    }

    private void initializeViews(TweetViewHolder holder, View convertView) {
        holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImg);
        holder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        holder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        holder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
    }

    private void setViewContent(Tweet tweet, TweetViewHolder holder) {
        holder.ivProfileImage.setImageResource(android.R.color.transparent);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), holder.ivProfileImage);

        holder.tvUsername.setText(tweet.getUser().getUsername());
        holder.tvBody.setText(tweet.getBody());

        holder.tvTimestamp.setText(tweet.getRelativeTimetamp());
    }

    private class TweetViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimestamp;
    }
}
