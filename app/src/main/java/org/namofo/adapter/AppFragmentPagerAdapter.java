package org.namofo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager滑动适配器(公用类) 
 * @author zhengjiong
 * @version 1.0
 * @created 2014-4-22 下午6:11:07
 */
public class AppFragmentPagerAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment> fragments;
	private List<String> mTitles;

    public AppFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

	public AppFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<String> titles) {
		super(fm);
		this.fragments = fragments;
		this.mTitles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	public void removeFragment(int position){
		fragments.remove(position);
	}
	
	/*@Override
	public CharSequence getPageTitle(int position) {
		if (mTitles != null && mTitles.size() != 0) {
			return mTitles.get(position);
		}
		return "";
	}*/
}
