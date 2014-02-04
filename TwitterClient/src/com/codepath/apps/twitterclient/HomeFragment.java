package com.codepath.apps.twitterclient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
		Log.d("DEBUG", "In Home Fragment.java"); 
      View view = inflater.inflate(R.layout.fragment_home, container, false);
      return view;
    }
}
