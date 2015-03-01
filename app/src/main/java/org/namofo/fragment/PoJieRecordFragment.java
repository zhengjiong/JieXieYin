package org.namofo.fragment;

import org.namofo.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 破戒記錄
 * @author zhengjiong
 * 2014-6-20 上午6:29:53
 */
public class PoJieRecordFragment extends BaseFragment{
	private static final boolean DEBUG = true;
	private static final String TAG = "PoJieFragment";
	
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mRootView = inflater.inflate(R.layout.po_jie_layout, null);
		return mRootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		debug("onViewCreated");
		
		initView();
		initVariable();
	}
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}

	private void debug(String msg) {
		// TODO Auto-generated method stub
		if (DEBUG) {
			Log.i(TAG, msg);
		}
	}
}
