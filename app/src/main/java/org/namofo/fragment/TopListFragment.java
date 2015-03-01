package org.namofo.fragment;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.namofo.R;
import org.namofo.adapter.TopListAdapter;
import org.namofo.ui.MainActivity;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * 排行榜
 * @author zhengjiong
 * 2014-6-20 上午6:26:48
 */
public class TopListFragment extends BaseFragment{
	private static final boolean DEBUG = true;
	private static final String TAG = "TopListFragment";

	private View mRootView;
    private ListView mListView;
    private PullToRefreshLayout mPullToRefreshLayout;

    private MainActivity mainActivity;
    private ColorDrawable mActionBarBackgroundDrawalbe;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mRootView = inflater.inflate(R.layout.top_list_layout, null);
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
		// TODO Auto-generated method stub
        mListView = (ListView) mRootView.findViewById(R.id.listview);

        mPullToRefreshLayout = (PullToRefreshLayout) mRootView.findViewById(R.id.ptr_layout);

        ActionBarPullToRefresh.from(mActivity)
                .options(Options.create()
                        .scrollDistance(0.5f)
                        .build()
                )
                // Mark All Children as pullable
                .allChildrenArePullable()
                // Set a OnRefreshListener
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullToRefreshLayout.setRefreshComplete();
                            }
                        }, 2000);
                    }
                })
                // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);
    }

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
        mainActivity = (MainActivity) mActivity;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
        mListView.setAdapter(new TopListAdapter(mActivity));
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
        /*mActionBarBackgroundDrawalbe = new ColorDrawable(getResources().getColor(R.color.actionbar_light_translucent));
        mainActivity.mActionBar.setBackgroundDrawable(mActionBarBackgroundDrawalbe);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawalbe.setCallback(callback);
        }

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int newScrollY = getListViewScrollY();

                int headerHeight = (int) mActivity.getResources().getDimension(R.dimen.abc_action_bar_default_height) * 3;

                float ratio = 1 - ((float) Math.min(Math.max(newScrollY, 0), headerHeight) / headerHeight);

                int newAlpha = (int) (ratio * 255) ;

                mActionBarBackgroundDrawalbe.setAlpha(newAlpha);
                mainActivity.mActionBar.setBackgroundDrawable(mActionBarBackgroundDrawalbe);
                mainActivity.getHomePageFragment().mImgHeadview.getDrawable().setAlpha(newAlpha);
                mainActivity.getHomePageFragment().mHeaderView.getBackground().setAlpha(newAlpha);
            }
        });*/
	}

    /**
     * 在ListView中，使用getChildAt(index)的取值，只能是当前可见区域（列表可滚动）的子项！

     * 即取值范围在 >= ListView.getFirstVisiblePosition() &&  <= ListView.getLastVisiblePosition();
     * 1）所以如果想获取前部的将会出现返回Null值空指针问题；
     * 2）getChildCount跟getCount获取的值将会不一样（数量多时）；
     * 3）如果使用了getChildAt(index).findViewById(...)设置值的话，滚动列表时值就会改变了。
     * 需要使用getFirstVisiblePosition()获得第一个可见的位置，再用当前的position-它,再用getChildAt取值！即getChildAt(position - ListView。getFirstVisiblePosition()).findViewById(...)去设置值
     */
    protected int getListViewScrollY() {
        View topChild = mListView.getChildAt(0);
        return topChild == null ? 0 : mListView.getFirstVisiblePosition() * topChild.getHeight() -
                topChild.getTop();
    }

    private Drawable.Callback callback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            mainActivity.mActionBar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };

	private void debug(String msg) {
		// TODO Auto-generated method stub
		if (DEBUG) {
			Log.i(TAG, msg);
		}
	}
}
