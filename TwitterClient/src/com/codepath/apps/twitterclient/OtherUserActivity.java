package com.codepath.apps.twitterclient;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.activeandroid.ActiveAndroid;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OtherUserActivity extends Activity {
	
	private ImageView imgProfile;
	private TextView tvProfile;
	private TextView followers;
	private TextView following;
	private volatile boolean userInfoFound = false;
	private List<Tweet> tweets;
	private ListView lvTweets;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		imgProfile = (ImageView) findViewById(R.id.myProfileImg);
		tvProfile = (TextView) findViewById(R.id.myProfile);		
		followers = (TextView) findViewById(R.id.followers);
		following = (TextView) findViewById(R.id.following);
		lvTweets = (ListView) findViewById(R.id.myTweets); 
		
		Bundle extras = getIntent().getExtras();
		String username = extras.getString("Username");
		
		if (!userInfoFound) {
			TwitterClientApp.getRestClient().getOtherUserInfo(username,
				    new JsonHttpResponseHandler() {
				    	
						@Override  
				        public void onSuccess(JSONObject myInfo) {
							userInfoFound = true; 
				        	Log.d("DEBUG object", myInfo.toString());
				        	User user = User.fromJson(myInfo);
				        	tvProfile.setText(" @" + user.getScreenName());
				        	ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imgProfile);
				        	following.setText(user.getFriendsCount()+"following");
				        	followers.setText(user.getFollowersCount()+"followers");
				        	
				        	TwitterClientApp.getRestClient().getUserTimeline(user.getScreenName(),
				    			    new JsonHttpResponseHandler() {
				    			        @Override
				    					public void onFailure(Throwable arg0, JSONArray arg1) { 
				    			        	Log.d("DEBUG", "JSONArray: " + arg0 + " " + arg1);  
				    					}

				    					@Override
				    					public void onFailure(Throwable arg0, JSONObject arg1) {
				    						Log.d("DEBUG", "JSONObject: " + arg0 + " " + arg1);    
				    					} 
				    	 
				    					@Override  
				    			        public void onSuccess(JSONArray jsonTweets) {
				    			        	tweets = Tweet.fromJson(jsonTweets);
				    			        	TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				    			    		lvTweets.setAdapter(adapter);
				    			    		ActiveAndroid.beginTransaction();
				    			    		try {
				    			    			for (Tweet t : tweets) {
				    			    		    	t.save1();
				    			    		    }
				    			    		    ActiveAndroid.setTransactionSuccessful();
				    			    		} finally {
				    			    			ActiveAndroid.endTransaction();
				    			    		}
				    			        }  
				    			    });
				        }  
										
				    });
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}

}
