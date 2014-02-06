package com.codepath.apps.twitterclient;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
	String userName;
	

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TimeLineActivity ti = new TimeLineActivity();
		if (convertView == null) { 
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.tweet_item, null);
		}
		Tweet t = getItem(position);
		
		ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(t.getUser().getProfileImageUrl() , ivProfile);
		ivProfile.setTag(t.getUser().getScreenName());
		userName = ivProfile.getTag().toString();
		
		ivProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getContext(), userName, Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub
				userName = v.getTag().toString();
				Intent i = new Intent(v.getContext(), OtherUserProfile.class);
				i.putExtra("Username", userName);
			    v.getContext().startActivity(i);
			}
		});
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		String formattedName = "<b>" + t.getUser().getName() + "</b> <small><font color='#777777'>@" + 
		    t.getUser().getScreenName() + "</font></small>";
		tvName.setText(Html.fromHtml(formattedName));
		
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		tvBody.setText(Html.fromHtml(t.getBody()));
		 
		return convertView;
	}
	
	public String getUserName(){
		return userName;
	}
}
