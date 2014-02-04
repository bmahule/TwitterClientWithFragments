package com.codepath.apps.twitterclient;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TimeLineActivity extends ActionBarActivity {

	public static final int REQ_OPTIONS_OK = 1;
	
	private ListView lvTweets;  
	private List<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		Log.d("DEBUG", "creating timeline activity"); 
		lvTweets = (ListView) findViewById(R.id.listTweets); 
		refresh();

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
	            customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
	        }
	        });
		setupTabs();    
	}
	
	private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
            .newTab()
            .setText("Home")
            .setIcon(R.drawable.ic_home)
            .setTag("HomeTimelineFragment")
            .setTabListener(new FragmentTabListener<HomeFragment>(R.id.homeFragment, this,"Home", HomeFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);
        
        Tab tab2 = actionBar
            .newTab()
            .setText("Mentions")
            .setIcon(R.drawable.ic_mentions)
            .setTag("MentionsTimelineFragment")
            .setTabListener(new MentionsTabListener<MyTweetsFragment>(R.id.placeHolder, this, "My Tweets", MyTweetsFragment.class));
        actionBar.addTab(tab2);
        actionBar.show();
        
    }


	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    }
    
	private void refresh() {
		TwitterClientApp.getRestClient().getHomeTimeline(
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

	public void onClickRefresh(MenuItem mi) {
	    refresh();	
	}
	
	public void onClickCompose(MenuItem mi) { 
	    Intent i = new Intent(getApplicationContext(), PostTweetActivity.class);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}
	
	public void onClickUserinfo(MenuItem mi) { 
	    Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}
	
	public void getMyTweets() { 
		TwitterClientApp.getRestClient().getMentionsTimeline(
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK && requestCode == REQ_OPTIONS_OK) {
		     refresh();
	    }
	}	
	
}