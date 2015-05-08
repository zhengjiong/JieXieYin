package org.namofo.ui;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;
import android.view.View;

import com.google.common.base.Strings;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.namofo.R;
import org.namofo.app.AppContext;
import org.namofo.constants.Constants;
import org.namofo.util.Debug;
import org.namofo.util.PreferencesUtils;
import org.namofo.util.ProgressDialogUtils;
import org.namofo.util.ToastUtils;

/**
 * 用戶基本資料
 * @Author: zhengjiong
 * Date: 2014-09-09
 * Time: 07:53
 */
public class BasicInfoActivity extends BaseActivity{
    private static String tag = "BasicInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.basic_info_layout);
        setContentView(R.layout.basic_info_preference_layout);

        initVariable();
        initView();
        initData();
        initListener();

        getFragmentManager().beginTransaction()
                .replace(R.id.replace_holder, new BasicInfoFragment())
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
            //設置保存的xml文件和MainActivity.saveUserToSharePreferences保存的xml一致
            getPreferenceManager().setSharedPreferencesName("JieXieYin");
            addPreferencesFromResource(R.xml.basic_info_preference);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            ListPreference sexPreference = (ListPreference) findPreference("sex");

            sexPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Debug.i(tag, "onPreferenceChange newValue=" + newValue.toString());

                    RequestParams params = new RequestParams();
                    params.addBodyParameter("control", "setUserInfo");
                    params.addBodyParameter("uid", PreferencesUtils.getString(getActivity(), "uid"));
                    params.addBodyParameter("sex", newValue.toString());
                    saveData(params);

                    /*
                     * True to update the state of the Preference with the new value.
                     * 返回true才會保存選擇的值
                     */
                    return true;

                }
            });
            sexPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Debug.i(tag, "onPreferenceChange key=" + preference.getKey());
                    Debug.i(tag, "onPreferenceChange title=" + preference.getTitle());
                    return true;
                }
            });
        }

        private void saveData(RequestParams params){
            ProgressDialogUtils.show(getActivity(), "操作中...");

            AppContext.getInstance().getHttpUtils().send(HttpRequest.HttpMethod.POST, Constants.HOST_URL, params, new RequestCallBack<Object>() {

                /*
                 * {"uid":"735","result":"1"}
                 */
                @Override
                public void onSuccess(ResponseInfo<Object> responseInfo) {
                    ProgressDialogUtils.hide();
                    //int code = responseInfo.statusCode;
                    String result = responseInfo.result.toString();
                    if (Strings.isNullOrEmpty(result)) {
                        ToastUtils.show(getActivity(), R.string.error_server);
                        return;
                    }
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        if ("0".equals(jsonResult.getString("result"))) {
                            ToastUtils.show(getActivity(), jsonResult.getString("info"));
                        } else {
                            ToastUtils.show(getActivity(), "修改成功");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.show(getActivity(), R.string.error_json);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    ToastUtils.show(getActivity(), R.string.error_server);
                    ProgressDialogUtils.hide();
                }
            });
        }
    }
}
