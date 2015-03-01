package org.namofo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.namofo.app.AppContext;

/**
 * 
 * @author zhengjiong
 * 2014-6-19 上午6:41:01
 */
public abstract class BaseFragment extends Fragment{
	protected AppContext mAppContext;
    protected Activity mActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mAppContext = AppContext.getInstance();
        mActivity = getActivity();
	}
	
	
	protected abstract void initView();
	protected abstract void initVariable();//初始化變量
	protected abstract void initData();//初始化數據
	protected abstract void initListener();
	
}
