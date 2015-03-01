package org.namofo.ui;

import org.namofo.R;

import android.os.Bundle;
import android.view.MenuItem;

/**
 * 登錄
 * @author zhengjiong
 * 2014-6-19 上午7:48:45
 */
public class LoginActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		initView();
	}
	
	@Override
	protected void initView() {
		initActionBar();
		setActionBarTitle("登录");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			LoginActivity.this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void initVariable() {
		
	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void initListener() {
		
	}

}
