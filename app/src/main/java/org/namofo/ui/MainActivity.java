package org.namofo.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.namofo.R;
import org.namofo.adapter.SlidingListviewAdapter;
import org.namofo.app.AppContext;
import org.namofo.fragment.CheckInFragment;
import org.namofo.fragment.ForumFragment;
import org.namofo.fragment.HomepageFragment;
import org.namofo.fragment.MengYiRecordFragment;
import org.namofo.fragment.MyCenterFragment;
import org.namofo.fragment.PoJieRecordFragment;
import org.namofo.fragment.TopListFragment;
import org.namofo.util.ToastUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * 应用程序首页
 * @author zhengjiong
 * 2014-6-18 上午6:26:00
 */
public class MainActivity extends BaseActivity implements OnClickListener{
	private static final boolean DEBUG = true;
	private static final String TAG = "MainActivity";
    public int actionBarAlpha;
	
	private DrawerLayout mDrawerLayout;
	
	private ListView mListView;
	
	private View mSlidingMenu;
	
	private View mSlidingUserWrapper;
	
	private int mCurrentPosition;
	
	public ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
	
	private FragmentManager mFragmentManager;
	
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mColumns;
    
    private int[] mIconRes = {
    		R.drawable.ic_home_color_20,
    		R.drawable.ic_hangouts_color_20,
            R.drawable.ic_circles_color_20,
    		R.drawable.ic_circles_color_20,
    		R.drawable.ic_communities_color_20,
    		R.drawable.ic_photos_color_20,
    		R.drawable.ic_circles_color_20};
    
    private SlidingListviewAdapter mSlidingListviewAdapter;

    //private PullToRefreshAttacher mPullToRefreshAttacher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        initView();
		initVariable();
		initData();
		initListener();
		
		//显示首页
		mFragmentManager.beginTransaction().add(
				R.id.content_frame, 
				mFragments.get(0), 
				String.valueOf(0)).commit();
		
		
	}

	protected void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mListView = (ListView) findViewById(R.id.main_listview);
		mSlidingMenu = findViewById(R.id.sliding_menu);
		mSlidingUserWrapper = findViewById(R.id.sliding_user_wrapper);
		
		mColumns = getResources().getStringArray(R.array.columns);
        initActionBar2();
		getSupportActionBar().setTitle(mColumns[0]);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


		/*ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
				MainActivity.this, 
				R.array.post_category, 
				android.R.layout.simple_list_item_1);*/
		
		/*getSupportActionBar().setListNavigationCallbacks(arrayAdapter, new ForumNavigationListener());
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("虚云").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("弟子").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("abcccc").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("abc").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("abc").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("abc").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("abc").setTabListener(this));
		getSupportActionBar().addTab(getSupportActionBar().newTab().setText("abc").setTabListener(this));*/


		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(
				this, 	 							/* host Activity */
				mDrawerLayout, 						/* DrawerLayout object */
				R.drawable.hamenu,					/* nav drawer icon to replace 'Up' caret */
				R.string.openDrawerContentDescRes,  /* "open drawer" description */
				R.string.closeDrawerContentDescRes) /* "open drawer" description */
			{
			
			public void onDrawerOpened(View drawerView) {}
			
			@SuppressLint("NewApi")
			public void onDrawerClosed(View drawerView) {
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
					invalidateOptionsMenu();// creates call to onPrepareOptionsMenu()
				}
			}
		};

        initPullToRefreshActionBar();
	}

    private void initPullToRefreshActionBar() {
        /*Options options = new Options();
        options.headerInAnimation = R.anim.pulldown_fade_in;
        options.headerOutAnimation = R.anim.pulldown_fade_out;
        options.refreshScrollDistance = 0.3f;
        options.headerLayout = R.layout.pulldown_header;
        mPullToRefreshAttacher = new PullToRefreshAttacher(this, options);*/
    }

    //fragment切換
	private void selectItem(int position){
		mCurrentPosition = position;
		
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		for (int i = 0; i < mFragments.size(); i++) {
			if (i == position) {
				continue;
			}
			Fragment fragment = mFragmentManager.findFragmentByTag(String.valueOf(i));
			if (fragment != null) {
				transaction.hide(fragment);
			}
		}
		Fragment fragment = mFragmentManager.findFragmentByTag(String.valueOf(position));
		if (fragment == null) {
			transaction.add(R.id.content_frame, mFragments.get(position), String.valueOf(position));
		}else {
			transaction.show(mFragments.get(position));
		}
		transaction.commit();

        mListView.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mSlidingMenu);//关闭侧滑菜单
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			//如果返回true則代表點擊的是App Icon圖標
			return true;
		}
        switch (item.getItemId()){
            case R.id.check_in:
                ToastUtils.show(MainActivity.this, "签到成功");
                break;
            default:
                break;
        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        switch (mCurrentPosition){
            case 0://文章
                break;
            case 1://排行榜
                break;
            case 2://我要簽到
                getMenuInflater().inflate(R.menu.checkin_menu, menu);
                break;
            case 3:
                break;
        }
		//getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		debug("onPrepareOptionsMenu");
		// If the nav drawer is open, hide action items related to the content view
		boolean isOpen = mDrawerLayout.isDrawerOpen(mSlidingMenu);
		//menu.findItem(R.id.action_websearch).setVisible(!isOpen);

		/*switch (mCurrentPosition) {
		case 1:
			menu.findItem(R.id.action_search).setVisible(false);
			menu.findItem(R.id.action_new_post).setVisible(true);
			break;
		default:
			menu.findItem(R.id.action_search).setVisible(true);
			menu.findItem(R.id.action_new_post).setVisible(false);
			break;
		}*/
		return super.onPrepareOptionsMenu(menu);
	}
	
	/*private class ForumNavigationListener implements OnNavigationListener{

		@Override
		public boolean onNavigationItemSelected(int arg0, long arg1) {
			return true;
		}
		
	}*/
	
	/**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
	 * 当使用ActionBarDrawerToggle时,您必须调用它
     */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();// Sync the toggle state after onRestoreInstanceState has occurred.
	}
	
	/**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
	 * 当使用ActionBarDrawerToggle时,您必须调用它
     */
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
	protected void initVariable() {
		mFragmentManager = getSupportFragmentManager();
		
		mFragments.add(new HomepageFragment());
		mFragments.add(new TopListFragment());
        mFragments.add(new CheckInFragment());
		mFragments.add(new MyCenterFragment());
		mFragments.add(new ForumFragment());
		mFragments.add(new PoJieRecordFragment());
		mFragments.add(new MengYiRecordFragment());
        //mFragments.add(new FadingActionBarTestFragment());

        try {//显示出overflow Button
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {}
		
	}
	
	@Override
	protected void initData() {
		mSlidingListviewAdapter = new SlidingListviewAdapter(this, mColumns, mIconRes);
		mListView.setAdapter(mSlidingListviewAdapter);
	}
	
	@Override
	protected void initListener() {
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mListView.setOnItemClickListener(new SlidingMenuItemListener());
		mSlidingUserWrapper.setOnClickListener(this);
	}
	
	private class SlidingMenuItemListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			/*if (position == 0) {
				getSupportActionBar().setDisplayShowTitleEnabled(false);
				ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
						MainActivity.this, 
						R.array.post_category, 
						android.R.layout.simple_list_item_1);
				
				getSupportActionBar().setListNavigationCallbacks(arrayAdapter, new ForumNavigationListener());
				getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			}else {
				getSupportActionBar().setDisplayShowTitleEnabled(true);
				getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			}*/
            //設置actionbar標題
			getActionBar().setTitle(mColumns[position]);

            if (position != 0) {
                /**
                 * 設置actionbar背景為不透明
                 */
                ColorDrawable actionBarBackgroundDrawable = new ColorDrawable(getResources().getColor(R.color.actionbar_light_translucent));
                getActionBar().setBackgroundDrawable(actionBarBackgroundDrawable);
            } else {
                ColorDrawable actionBarBackgroundDrawable = new ColorDrawable(getResources().getColor(R.color.actionbar_light_translucent));
                actionBarBackgroundDrawable.setAlpha(actionBarAlpha);
                getActionBar().setBackgroundDrawable(actionBarBackgroundDrawable);
            }

			selectItem(position);
		}
		
	}

    @Override
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.sliding_user_wrapper:
			mDrawerLayout.closeDrawer(mSlidingMenu);
			
			//如果沒有登錄就跳到登錄界面
			//startActivity(new Intent(MainActivity.this, LoginActivity.class));

            showLoginDialog();
			break;

		default:
			break;
		}
	}
	
	private void debug(String msg) {
		if (DEBUG) {
			Log.i(TAG, msg);
		}
	}

    private void showLoginDialog(){
        new AlertDialog.Builder(MainActivity.this)
            .setTitle("用户登录")
            .setView(getLayoutInflater().inflate(R.layout.login_layout, null))
            .setNegativeButton("取消", null)
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    RequestParams params = new RequestParams();
                    params.addBodyParameter("userName", "郑炯");
                    params.addBodyParameter("password", "123456789");

                    AppContext.getInstance().getHttpUtils().send(HttpRequest.HttpMethod.POST, "http://app.namofo.org/app.do", params, new RequestCallBack<Object>() {
                        @Override
                        public void onSuccess(ResponseInfo<Object> info) {
                            int code = info.statusCode;
                            String result = info.result.toString();
                            Log.i("zj", "code = " + code);
                        }

                        @Override
                        public void onFailure(HttpException e, String error) {
                            Log.i("zj", "error=" + error);
                        }
                    });
                }
            }).show();
    }

    public HomepageFragment getHomePageFragment(){
        return (HomepageFragment) mFragments.get(0);
    }
}