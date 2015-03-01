package org.namofo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import org.namofo.R;
import org.namofo.ui.MainActivity;

import java.util.List;

/**
 * 
 * @author zhengjiong
 * @version 1.0
 * @created 2014-7-4 下午5:57:08
 */
public class JieFragmentPagerAdapter extends PagerAdapter implements OnPageChangeListener{
	private FragmentManager mFragmentManager;
	private List<Fragment> mFragments;
	private String[] mTitles;
	private ViewPager mViewPager;
	private int currentPageIndex = 0; // 当前page索引（切换之前）

	public JieFragmentPagerAdapter(FragmentManager fm, ViewPager viewPager, List<Fragment> fragments) {
		mFragmentManager = fm;
		mFragments = fragments;
		mViewPager = viewPager;
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}

    public interface PageSelectedCallBack{
        public void onPageSelected();
    }

	/**
	 * 用於頂部有PagerSlidingTabStrip的情況
	 * @param fm
	 * @param viewPager
	 * @param fragments
	 * @param context
	 * @param title
	 */
	public JieFragmentPagerAdapter(FragmentManager fm, ViewPager viewPager, List<Fragment> fragments, Context context, String[] title) {
		mFragmentManager = fm;
		mFragments = fragments;
		mTitles = title;
		mViewPager = viewPager;
		mViewPager.setAdapter(this);
        //mViewPager.setOffscreenPageLimit(fragments.size());
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = mFragments.get(position);
		if (!fragment.isAdded()) {//如果fragment還沒有added
			FragmentTransaction transaction = mFragmentManager.beginTransaction();
			transaction.add(fragment, fragment.getClass().getSimpleName());
			transaction.commit();
			
			/**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
			mFragmentManager.executePendingTransactions();
		}
		
		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView());
		}

		return fragment.getView();
	}
	
	
	@Override
	public int getCount() {
		return mFragments.size();
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mFragments.get(position).getView());
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		return v == o;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int i) {
        mFragments.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
        if(mFragments.get(i).isAdded()){
          mFragments.get(i).onResume(); // 调用切换后Fargment的onResume()
        }
        currentPageIndex = i;
        /*Log.i("zj", "onPageSelected i=" + i);
        if (mPageSelectedCallBack != null) {
            mPageSelectedCallBack.onPageSelected();
        }*/
    }

}
