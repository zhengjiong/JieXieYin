package org.namofo.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;

import org.namofo.R;
import org.namofo.adapter.JieFragmentPagerAdapter;
import org.namofo.app.AppContext;
import org.namofo.bean.ArticleCategory;
import org.namofo.sqlite.service.ArticleCategoryDBService;
import org.namofo.ui.MainActivity;
import org.namofo.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 首頁 (显示戒色文章)
 *
 * @author zhengjiong
 * @date 2014-6-19 上午8:21:18
 */
public class HomepageFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final boolean DEBUG = true;
    private static final String TAG = "HomepageFragment";

    protected ArrayList<View> mTabChildViews = new ArrayList<View>();
    public ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    private View mRootView;
    protected ImageView mImgHeadview;
    protected RelativeLayout mHeaderView;
    protected PagerSlidingTabStrip mTabs;

    private ViewPager mViewPager;

    private ArticleCategoryDBService mArticleCategoryDBService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.homepage_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        debug("onViewCreated");

        initVariable();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        mHeaderView = (RelativeLayout) mRootView.findViewById(R.id.headerview);
        mImgHeadview = (ImageView) mRootView.findViewById(R.id.img_header);

        mTabs = (PagerSlidingTabStrip) mRootView.findViewById(R.id.tabs);

        //設置頂部Tab的樣式
        mTabs.setIndicatorColorResource(R.color.tab_indicator_light);
        mTabs.setTextColorResource(R.color.tab_txt);
        mTabs.setTextSize(DisplayUtil.sp2px(mActivity, 15));
        mTabs.setBackgroundColor(getResources().getColor(R.color.actionbar_light_translucent));
        mTabs.setIndicatorHeight(DisplayUtil.dip2px(getActivity(), 6));
        mTabs.setUnderlineHeight(0);
        mTabs.setDividerColorResource(android.R.color.transparent);
        mTabs.setTabBackground(R.drawable.background_tab);
    }

    @Override
    protected void initVariable() {
        mArticleCategoryDBService = AppContext.getArticleCategoryDBService(mActivity);
    }

    @Override
    protected void initData() {
        initTabData();
    }

    @Override
    protected void initListener() {

    }

    private void initTabData() {
        // TODO Auto-generated method stub
        List<ArticleCategory> categories = mArticleCategoryDBService.loadAll();
        String[] titles = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            ArticleCategory category = categories.get(i);
            mFragments.add(ArticleFragment.newInstance(category));
            titles[i] = category.getName();
        }
        JieFragmentPagerAdapter adapter = new JieFragmentPagerAdapter(getChildFragmentManager(), mViewPager, mFragments, getActivity(), titles);
        mTabs.setViewPager(mViewPager);
        mTabs.setOnPageChangeListener(this);
    }

    private void debug(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    private int currentPageIndex = 0; // 当前page索引（切换之前）
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


        Drawable mActionBarBackgroundDrawalbe = new ColorDrawable(getResources().getColor(R.color.actionbar_light_translucent));
        MainActivity mainActivity = (MainActivity) mActivity;

        mainActivity.actionBarAlpha = 255;
        mActionBarBackgroundDrawalbe.setAlpha(mainActivity.actionBarAlpha);
        mainActivity.mActionBar.setBackgroundDrawable(mActionBarBackgroundDrawalbe);
        mainActivity.getHomePageFragment().mImgHeadview.getDrawable().setAlpha(mainActivity.actionBarAlpha);
        mainActivity.getHomePageFragment().mHeaderView.getBackground().setAlpha(mainActivity.actionBarAlpha);

        PagerSlidingTabStrip tab = mainActivity.getHomePageFragment().mTabs;
        tab.getBackground().setAlpha(mainActivity.actionBarAlpha);
        tab.setTextColor(Color.argb(mainActivity.actionBarAlpha, 255, 255, 255));
        tab.setIndicatorColor(Color.argb(mainActivity.actionBarAlpha, 3, 169, 244));

        /**
         * tab背景顏色和點擊顏色
         */
        for (int j = 0; j < mTabChildViews.size(); j++) {
            View v = mainActivity.getHomePageFragment().mTabChildViews.get(j);
            Drawable tabBackgroundDrawable = v.getBackground();
            tabBackgroundDrawable.setAlpha(mainActivity.actionBarAlpha);
            v.setBackgroundDrawable(tabBackgroundDrawable);
        }

        /**
         * 設置tab顯示
         */
        mTabs.setVisibility(View.VISIBLE);

        /**
         * android4.4以下版本有bug,必須重新設置title,不然会直接让背景消失
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            mainActivity.getActionBar().setTitle(mainActivity.getActionBar().getTitle());
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {}
}
