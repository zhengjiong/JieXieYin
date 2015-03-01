package org.namofo.ui;

import android.os.Bundle;
import android.view.MenuItem;

import org.namofo.R;

/**
 * 用戶基本資料
 * @Author: zhengjiong
 * Date: 2014-09-09
 * Time: 07:53
 */
public class BasicInfoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info_layout);

        initVariable();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        initActionBar();
        setActionBarTitle("基本资料");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.scale_enter_anim, R.anim.translate_exit_anim);
    }
}
