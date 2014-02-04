package com.codepath.apps.twitterclient;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.twitterclient.Fragment.HomeTimelineFragment;
import com.codepath.apps.twitterclient.Fragment.MentionsFragment;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends FragmentActivity implements TabListener {

	public static final int REQ_OPTIONS_OK = 1;
	
	private ListView lvTweets;  
	private List<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		Log.d("DEBUG", "creating timeline activity"); 
		lvTweets = (ListView) findViewById(R.id.listTweets); 
		setupTabs();    
	}
	
	
	private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
            .newTab()
            .setText("Home")
            .setIcon(R.drawable.ic_home)
            .setTag("HomeTimelineFragment")
            .setTabListener(this);
        
        Tab tab2 = actionBar
            .newTab()
            .setText("Mentions")
            .setIcon(R.drawable.ic_mentions)
            .setTag("MentionsTimelineFragment")
            .setTabListener(this);
        
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.selectTab(tab1);
        actionBar.show();    
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

	public void onClickCompose(MenuItem mi) { 
	    Intent i = new Intent(getApplicationContext(), PostTweetActivity.class);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}
	
	public void onClickUserinfo(MenuItem mi) { 
	    Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK && requestCode == REQ_OPTIONS_OK) {
		    // refresh();
	    }
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag() == "HomeTimelineFragment"){
			// Set fragment to home time lime
			fts.replace(R.id.frameContainer, new HomeTimelineFragment());
		} else {
			// Set fragment to mentions time line
			fts.replace(R.id.frameContainer, new MentionsFragment());
		}
		fts.commit();
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}	
	
}