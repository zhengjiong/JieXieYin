package org.namofo.ui;

import android.os.Bundle;
import android.preference.EditTextPreference;
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
        private ListPreference mSexListPreference;
        private EditTextPreference mAgeEditPreference;
        private EditTextPreference mXueLiEditPreference;
        private EditTextPreference mAiHaoEditPreference;
        private EditTextPreference mTeChangEditPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //設置保存的xml文件和MainActivity.saveUserToSharePreferences保存的xml一致
            getPreferenceManager().setSharedPreferencesName("JieXieYin");
            addPreferencesFromResource(R.xml.basic_info_preference);
        }

        private void initView(){
            mSexListPreference = (ListPreference) findPreference("sex");
            mAgeEditPreference = (EditTextPreference) findPreference("age");
            mXueLiEditPreference = (EditTextPreference) findPreference("xueli");
            mAiHaoEditPreference = (EditTextPreference) findPreference("aihao");
            mTeChangEditPreference = (EditTextPreference) findPreference("techang");

            mSexListPreference.setSummary("".equals(mSexListPreference.getValue()) ? "未填写" : mSexListPreference.getValue());
            mAgeEditPreference.setSummary("".equals(mAgeEditPreference.getText()) ? "未填写" : mAgeEditPreference.getText());
            mXueLiEditPreference.setSummary("".equals(mXueLiEditPreference.getText()) ? "未填写" : mXueLiEditPreference.getText());
            mAiHaoEditPreference.setSummary("".equals(mAiHaoEditPreference.getText()) ? "未填写" : mAiHaoEditPreference.getText());
            mTeChangEditPreference.setSummary("".equals(mTeChangEditPreference.getText()) ? "未填写" : mTeChangEditPreference.getText());
        }

        private void initListener(){
            mSexListPreference.setOnPreferenceChangeListener(new MyPreferenceChangeListener("sex", mSexListPreference.getValue(), mSexListPreference));
            mAgeEditPreference.setOnPreferenceChangeListener(new MyPreferenceChangeListener("age", mAgeEditPreference.getText(), mAgeEditPreference));
            mXueLiEditPreference.setOnPreferenceChangeListener(new MyPreferenceChangeListener("xueli", mXueLiEditPreference.getText(), mXueLiEditPreference));
            mAiHaoEditPreference.setOnPreferenceChangeListener(new MyPreferenceChangeListener("aihao", mAiHaoEditPreference.getText(), mAiHaoEditPreference));
            mTeChangEditPreference.setOnPreferenceChangeListener(new MyPreferenceChangeListener("techang", mTeChangEditPreference.getText(), mTeChangEditPreference));
        }

        class MyPreferenceChangeListener implements Preference.OnPreferenceChangeListener {
            private String mKey;
            private String mDefaultValue;
            private Preference mPreference;

            public MyPreferenceChangeListener(String key, String defaultValue, Preference preference){
                this.mKey = key;
                this.mDefaultValue = defaultValue;
                this.mPreference = preference;
            }

            /**
             * True to update the state of the Preference with the new value.
             * 返回true才會保存選擇的值
             */
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Debug.i(tag, "onPreferenceChange newValue=" + newValue.toString());

                RequestParams params = new RequestParams();
                params.addQueryStringParameter("control", "setUserInfo");
                params.addQueryStringParameter("uid", PreferencesUtils.getString(getActivity(), "uid"));

                switch (preference.getKey()) {
                    case "sex":
                        params.addQueryStringParameter("sex", newValue.toString());
                        break;
                    case "age":
                        params.addQueryStringParameter("age", newValue.toString());
                        break;
                    case "xueli":
                        params.addQueryStringParameter("xueli", newValue.toString());
                        break;
                    case "aihao":
                        params.addQueryStringParameter("aihao", newValue.toString());
                        break;
                    case "techang":
                        params.addQueryStringParameter("techang", newValue.toString());
                        break;
                    default:
                        return false;
                }
                saveData(params);
                return true;
            }

            /**
             * 保存數據
             */
            private void saveData(RequestParams params){
                ProgressDialogUtils.show(getActivity(), "操作中...");

                AppContext.getInstance().getHttpUtils().send(HttpRequest.HttpMethod.GET, Constants.HOST_URL, params, new RequestCallBack<Object>() {


                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        Debug.i(tag, "onSuccess result=" + responseInfo.result.toString());
                        ProgressDialogUtils.hide();
                        //int code = responseInfo.statusCode;
                        String result = responseInfo.result.toString();
                        if (Strings.isNullOrEmpty(result)) {
                            ToastUtils.show(getActivity(), R.string.error_server);
                            return;
                        }
                        try {
                            JSONObject jsonResult = new JSONObject(result);
                            if (jsonResult.has("error")) {
                                ToastUtils.show(getActivity(), jsonResult.getString("info"));
                                //还原数据
                                mPreference.getEditor().putString(mKey, mDefaultValue).commit();
                                Debug.i(tag, "defaultValue=" + mDefaultValue);
                            } else {
                                if ("0".equals(jsonResult.getString("result"))) {
                                    ToastUtils.show(getActivity(), "修改失败");
                                    //还原数据
                                    mPreference.getEditor().putString(mKey, mDefaultValue).commit();
                                    Debug.i(tag, "defaultValue=" + mDefaultValue);
                                } else {
                                    ToastUtils.show(getActivity(), "修改成功");
                                    mPreference.setSummary(mPreference.getSharedPreferences().getString(mKey, mDefaultValue));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.show(getActivity(), R.string.error_json);
                            //还原数据
                            mPreference.getEditor().putString(mKey, mDefaultValue);
                            mPreference.shouldCommit();
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

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            initView();
            initListener();
        }

    }
}
