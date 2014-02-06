package com.codepath.apps.twitterclient;

import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6E6C6C")));
		loadProfileInfo();		
	}
	
	private void loadProfileInfo() {
		TwitterClientApp.getRestClient().getUserInfo(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject myInfo) {
						User user = User.fromJson(myInfo);
						getActionBar().setTitle("@" + user.getScreenName());
						popolateProfileHeader(user);
					}
				});
	}
	
	private void popolateProfileHeader(User u){
		ImageView imgProfile = (ImageView) findViewById(R.id.ivProfileImage);
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView followers = (TextView) findViewById(R.id.followers);
		TextView following = (TextView) findViewById(R.id.following);
		TextView tagline = (TextView) findViewById(R.id.tvTagline);
		
		tvName.setText(u.getName());
		tagline.setText(u.getTagline());
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), imgProfile);
		following.setText(u.getFriendsCount() + "following");
		followers.setText(u.getFollowersCount() + "followers");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
