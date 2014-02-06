package com.codepath.apps.twitterclient;

import org.json.JSONObject;

import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class OtherUserProfile extends FragmentActivity {
	String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_user_profile);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6E6C6C")));
		Bundle extras = getIntent().getExtras();
		userName = extras.getString("Username");
		loadProfileInfo();
	}
	
	private void loadProfileInfo() {
		TwitterClientApp.getRestClient().getOtherUserInfo(userName,
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
		ImageView imgProfile = (ImageView) findViewById(R.id.ivProfileImage1);
		TextView tvName = (TextView) findViewById(R.id.tvName1);
		TextView followers = (TextView) findViewById(R.id.followers1);
		TextView following = (TextView) findViewById(R.id.following1);
		TextView tagline = (TextView) findViewById(R.id.tvTagline1);
		
		tvName.setText(u.getName());
		tagline.setText(u.getTagline());
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), imgProfile);
		following.setText(u.getFriendsCount() + "following");
		followers.setText(u.getFollowersCount() + "followers");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other_user_profile, menu);
		return true;
	}

}
