package com.codepath.apps.twitterclient.Fragment;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.apps.twitterclient.TwitterClientApp;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class OtherUserFragment extends TweetsListFragment {
	String userName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getActivity().getIntent().getExtras();
		userName = extras.getString("Username");
		TwitterClientApp.getRestClient().getUserOtherTimeline(userName,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						getAdapter().addAll(Tweet.fromJson(jsonTweets));
					}
				});
	}
}
