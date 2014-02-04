package com.codepath.apps.twitterclient.Fragment;

import java.util.ArrayList;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TweetsAdapter;
import com.codepath.apps.twitterclient.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TweetsListFragment extends Fragment {
	TweetsAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
      return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    	adapter = new TweetsAdapter(getActivity(), tweets);
    	ListView lv = (ListView) getActivity().findViewById(R.id.listTweets);
		lv.setAdapter(adapter);
	}
	
	public TweetsAdapter getAdapter(){
		return adapter;
	}
}
