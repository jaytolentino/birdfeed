package com.codepath.apps.birdfeed.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.apps.birdfeed.R;
import com.codepath.apps.birdfeed.activities.FeedActivity;
import com.codepath.apps.birdfeed.networking.TwitterApplication;
import com.codepath.apps.birdfeed.networking.TwitterClient;
import com.codepath.apps.birdfeed.utils.ProgressBarHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class ComposeTweetFragment extends DialogFragment {
    private TwitterClient client;

    public static ComposeTweetFragment newInstance(String title) {
        ComposeTweetFragment fragment = new ComposeTweetFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    public ComposeTweetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View currentView = inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        client = TwitterApplication.getRestClient();

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            getDialog().setTitle(title);
        }

        Button sendTweet = (Button) currentView.findViewById(R.id.btnSendTweet);
        sendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView etComposeTweet = (TextView) currentView.findViewById(R.id.etComposeTweet);
                String tweetContent = etComposeTweet.getText().toString();
                final FeedActivity feedActivity = (FeedActivity) getActivity();
                ProgressBarHandler.showProgressBar(getActivity());

                client.postNewTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONArray jsonArray) {
                        feedActivity.refreshTimeline();
                        ((ComposeTweetFragment) getParentFragment()).dismiss();
                        ProgressBarHandler.hideProgressBar(feedActivity);
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        Log.d("debug", throwable.toString());
                        Log.d("debug", s);
                    }
                });

                ((FeedActivity) getActivity()).refreshTimeline();
                getDialog().dismiss();
            }
        });

        return currentView;

    }

    public void dismiss() {
        getDialog().dismiss();
    }

}
