package org.namofo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.namofo.R;
import org.namofo.adapter.AppFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @Author: zhengjiong
 * Date: 2014-09-21
 * Time: 18:43
 */
public class FadingActionBarTestFragment extends Fragment{

    private View mRootView;
    private ViewPager mViewPager;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_my, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager) mRootView.findViewById(R.id.pager);

        fragments.add(new TestFadingActionBarFragment());
        fragments.add(new TestFadingActionBarFragment());
        fragments.add(new TestFadingActionBarFragment());

        mViewPager.setAdapter(new AppFragmentPagerAdapter(getChildFragmentManager(), fragments));
        mViewPager.setOffscreenPageLimit(3);
    }


}
