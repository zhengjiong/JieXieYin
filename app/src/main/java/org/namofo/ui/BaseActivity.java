package org.namofo.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 *
 * @author zhengjiong
 * 2014-6-18 上午6:05:10
 */
public abstract class BaseActivity extends ActionBarActivity {
	public ActionBar mActionBar;
    protected ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected abstract void initView();//初始化控件
	protected abstract void initVariable();//初始化變量
	protected abstract void initData();//初始化數據
	protected abstract void initListener();//初始化listener

    protected void initActionBarHideLogo(){
        mActionBar = getSupportActionBar();
        /**
         * 给左上角图标的左边加上一个的图标
         */
        mActionBar.setDisplayHomeAsUpEnabled(true);

        /**
         * 使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标
         */
        mActionBar.setDisplayShowHomeEnabled(false);
        /**
         * 这个小于4.0版本的默认值为true的。但是在4.0及其以上是false，该方法的作用：
         * 决定左上角的图标是否可以点击。没有向左的小图标。 true 图标可以点击  false 不可以点击。
         */
        mActionBar.setHomeButtonEnabled(true);
    }


    protected void initActionBar() {
		mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标
        mActionBar.setHomeButtonEnabled(true);
	}

    protected void initActionBarShowLogo(){
        /*
         * 给左上角图标的左边加上一个返回的图标
         */
        mActionBar.setDisplayHomeAsUpEnabled(true);

        /**
         * 使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标
         */
        mActionBar.setDisplayShowHomeEnabled(true);
        /**
         * 这个小于4.0版本的默认值为true的。但是在4.0及其以上是false，该方法的作用：
         * 决定左上角的图标是否可以点击。没有向左的小图标。 true 图标可以点击  false 不可以点击。
         */
        mActionBar.setHomeButtonEnabled(true);
    }

    /**
     * @param title
     */
    protected void setActionBarTitle(String title){
        mActionBar.setTitle(title);
	}

    protected void showProgress(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
