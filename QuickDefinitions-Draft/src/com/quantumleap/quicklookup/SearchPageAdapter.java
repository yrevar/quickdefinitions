package com.quantumleap.quicklookup;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SearchPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	//private Context mContext;
	public SearchPageAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override 
	public Fragment getItem(int position) {

		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
}
