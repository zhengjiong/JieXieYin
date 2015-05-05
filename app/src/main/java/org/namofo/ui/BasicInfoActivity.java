package org.namofo.ui;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;
import android.view.View;

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
        //setContentView(R.layout.basic_info_layout);
        setContentView(R.layout.basic_info_preference_layout);

        getFragmentManager().beginTransaction()
                .add(R.id.replace_holder, new BasicInfoFragment())
                .commit();

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

    public static class BasicInfoFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesName("JieXieYin");
            addPreferencesFromResource(R.xml.basic_info_preference);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            ListPreference sexPreference = (ListPreference) findPreference("sex");

        }
    }
}
