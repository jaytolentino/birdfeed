package com.codepath.apps.birdfeed.adapters;

import android.content.Context;
import android.media.Image;
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

            holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImg);
            holder.ivProfileImage.setImageResource(android.R.color.transparent);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), holder.ivProfileImage);

            holder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            holder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);

            convertView.setTag(holder);
        }
        else {
            holder = (TweetViewHolder) convertView.getTag();
        }

        holder.tvUsername.setText(tweet.getUser().getUsername());
        holder.tvBody.setText(tweet.getBody());
        return convertView;
    }

    private class TweetViewHolder {
        ImageView ivProfileImage;
        TextView tvUsername;
        TextView tvBody;
    }
}
