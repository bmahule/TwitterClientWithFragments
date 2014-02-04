package com.codepath.apps.twitterclient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class MyTweetsFragment extends Fragment {
	
	
    FragmentActivity listener;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      View view = inflater.inflate(R.layout.fragment_my_tweets, container, false);
      return view;
    }

	public void populateTweets() {
		// TODO Auto-generated method stub
		Toast.makeText(this.getActivity(), "Mentions Selected", Toast.LENGTH_SHORT).show();
	}
}