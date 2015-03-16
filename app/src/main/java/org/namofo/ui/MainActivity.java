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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.namofo.R;
import org.namofo.adapter.SlidingListviewAdapter;
import org.namofo.app.AppConfig;
import org.namofo.app.AppContext;
import org.namofo.bean.User;
import org.namofo.constants.Constants;
import org.namofo.fragment.CheckInFragment;
import org.namofo.fragment.ForumFragment;
import org.namofo.fragment.HomepageFragment;
import org.namofo.fragment.MengYiRecordFragment;
import org.namofo.fragment.MyCenterFragment;
import org.namofo.fragment.PoJieRecordFragment;
import org.namofo.fragment.TopListFragment;
import org.namofo.util.PreferencesUtils;
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

    private AlertDialog mLoginDialog;
	private ListView mListView;
	
	private View mSlidingMenu;
	private View mSlidingUserWrapper;
    private ImageView mImgUserHeader;

    private TextView mTxtLogin;
	
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
        mImgUserHeader = (ImageView) findViewById(R.id.sliding_img_avatar);
        mTxtLogin = (TextView) findViewById(R.id.sliding_txt_login);

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
        String uid = PreferencesUtils.getString(this, "uid", null);
        if (!Strings.isNullOrEmpty(uid)) {//已經登錄過
            mTxtLogin.setText(PreferencesUtils.getString(this, "username", ""));//drawer中設置用戶名
            mSlidingUserWrapper.setEnabled(false);//屏蔽登錄按鈕的點擊事件

            /**
             * 顯示用戶頭像
             */
            String imgUrl = PreferencesUtils.getString(this, "headImg", null);
            if (!Strings.isNullOrEmpty(imgUrl)) {

                AppContext.getmImageLoader().displayImage(
                        imgUrl,
                        mImgUserHeader,
                        AppContext.getImageOptions(R.drawable.ic_avatar));
            }
        }
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

            //如果沒有登錄,就先彈出登錄窗口
            if (!AppConfig.isLogin(MainActivity.this)) {
                showLoginDialog();
                mDrawerLayout.closeDrawer(mSlidingMenu);//关闭侧滑菜单
                return;
            }

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
		case R.id.sliding_user_wrapper://點擊登錄
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

    private void showLoginDialog() {
        final View loginLayout = LayoutInflater.from(this).inflate(R.layout.login_layout, null);
        mLoginDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("用户登录")
                .setView(loginLayout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        EditText txtUsername = (EditText) loginLayout.findViewById(R.id.username);
                        EditText txtPassword = (EditText) loginLayout.findViewById(R.id.password);

                        final String username = txtUsername.getText().toString().trim();
                        String password = txtPassword.getText().toString().trim();

                        if (TextUtils.isEmpty(username)) {
                            ToastUtils.show(MainActivity.this, "请填写用户名");
                            setCloseAble(mLoginDialog, false);
                            return;
                        }
                        if (TextUtils.isEmpty(password)) {
                            ToastUtils.show(MainActivity.this, "请填写密码");
                            setCloseAble(mLoginDialog, false);
                            return;
                        }
                        setCloseAble(mLoginDialog, true);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("control", "userlogin");
                        params.addBodyParameter("userName", username);
                        params.addBodyParameter("password", password);

                        AppContext.getInstance().getHttpUtils().send(HttpRequest.HttpMethod.POST, Constants.HOST_URL, params, new RequestCallBack<Object>() {
                            /**
                             {
                             "uid": "735",
                             "maxSignDays": "1",
                             "continueDays": "1",
                             "userinfo": {
                                 "xuexin": "A",
                                 "tizhong": "",
                                 "shengao": "",
                                 "bingshi": "",
                                 "zhengzhuang": "",
                                 "sex": "",
                                 "age": "",
                                 "xueli": "",
                                 "aihao": "",
                                 "techang": "",
                                 "yy": "",
                                 "qq": "12345645"
                                 }
                             }
                             {"error":"0","info":"用户名密码不正确！"}
                             */
                            @Override
                            public void onSuccess(ResponseInfo<Object> info) {
                                int code = info.statusCode;
                                String result = info.result.toString();
                                if (Strings.isNullOrEmpty(result)) {
                                    ToastUtils.show(MainActivity.this, R.string.error_server);
                                    return;
                                }
                                try {
                                    JSONObject jsonResult = new JSONObject(result);
                                    if (jsonResult.has("error")) {
                                        ToastUtils.show(MainActivity.this, jsonResult.getString("info"));
                                    } else {
                                        User user = User.json2Obj(result);
                                        user.getUserInfo().setUsername(username);
                                        saveUserToSharePreferences(user);

                                        if (!Strings.isNullOrEmpty(user.getHeadImg())) {
                                            //顯示用戶頭像
                                            AppContext.getmImageLoader().displayImage(
                                                    user.getHeadImg(),
                                                    mImgUserHeader,
                                                    AppContext.getImageOptions(R.drawable.ic_avatar));
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ToastUtils.show(MainActivity.this, R.string.error_json);
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String error) {
                                ToastUtils.show(MainActivity.this, R.string.error_server);
                            }
                        });
                    }
                }).create();
        mLoginDialog.setCanceledOnTouchOutside(false);
        mLoginDialog.show();
    }

    private void setCloseAble(AlertDialog dialog, boolean closeAble){
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.setBoolean(dialog, closeAble);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存用戶登錄數據
     * @param user
     */
    private void saveUserToSharePreferences(User user) {
        PreferencesUtils.putString(this, "uid", user.getUid());
        PreferencesUtils.putString(this, "headImg", user.getHeadImg());
        PreferencesUtils.putString(this, "username", user.getUserInfo().getUsername());
        PreferencesUtils.putString(this, "maxSignDays", user.getMaxSignDays());
        PreferencesUtils.putString(this, "continueDays", user.getContinueDays());
        PreferencesUtils.putString(this, "xuexin", user.getUserInfo().getXuexin());
        PreferencesUtils.putString(this, "tizhong", user.getUserInfo().getTizhong());
        PreferencesUtils.putString(this, "shengao", user.getUserInfo().getShengao());
        PreferencesUtils.putString(this, "bingshi", user.getUserInfo().getBingshi());
        PreferencesUtils.putString(this, "zhengzhuang", user.getUserInfo().getZhengzhuang());
        PreferencesUtils.putString(this, "sex", user.getUserInfo().getSex());
        PreferencesUtils.putString(this, "age", user.getUserInfo().getAge());
        PreferencesUtils.putString(this, "xueli", user.getUserInfo().getXueli());
        PreferencesUtils.putString(this, "aihao", user.getUserInfo().getAihao());
        PreferencesUtils.putString(this, "techang", user.getUserInfo().getTechang());
        PreferencesUtils.putString(this, "yy", user.getUserInfo().getYy());
        PreferencesUtils.putString(this, "qq", user.getUserInfo().getQq());
    }

    public HomepageFragment getHomePageFragment(){
        return (HomepageFragment) mFragments.get(0);
    }
}
