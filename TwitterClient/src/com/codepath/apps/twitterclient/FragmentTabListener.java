package com.codepath.apps.twitterclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.widget.Toast;

public class FragmentTabListener<T extends Fragment> implements TabListener {
	private Fragment mFragment;
	private final FragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final int mfragmentContainerId;

	public FragmentTabListener(FragmentActivity activity, String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = android.R.id.content;
	}

	public FragmentTabListener(int fragmentContainerId, FragmentActivity activity, String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = fragmentContainerId;
		
		// Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        mFragment = mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
        if (mFragment != null && !mFragment.isDetached()) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            ft.detach(mFragment);
            ft.commit();
        }
  
	}

	/* The following are each of the ActionBar.TabListener callbacks */

	public void onTabSelected(Tab tab, FragmentTransaction sft) {
		// Check if the fragment is already initialized
		if (mFragment == null) {
			// If not, instantiate and add it to the activity
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
			sft.add(mfragmentContainerId, mFragment, mTag);
		} else {
			// If it exists, simply attach it in order to show it
			sft.attach(mFragment);
		}
		
		
		//if(tab.getTag().equals("Home")){
			Toast.makeText(this.mActivity, mActivity.toString(), Toast.LENGTH_SHORT).show();
		//}
		
	}

	public void onTabUnselected(Tab tab, FragmentTransaction sft) {
		if (mFragment != null) {
			// Detach the fragment, because another one is being attached
			sft.detach(mFragment);
			Toast.makeText(this.mActivity, "HOME PAGE Unselected", Toast.LENGTH_SHORT).show();
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction sft) {
		// User selected the already selected tab. Usually do nothing.
	}
}