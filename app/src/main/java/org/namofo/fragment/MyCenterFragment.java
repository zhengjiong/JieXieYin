package org.namofo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Strings;

import org.namofo.R;
import org.namofo.app.AppContext;
import org.namofo.ui.BasicInfoActivity;
import org.namofo.ui.CommunicationInfoActivity;
import org.namofo.ui.HealthInfoActivity;
import org.namofo.util.PreferencesUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 * @author zhengjiong
 * 2014-6-19 上午8:03:14
 */
public class MyCenterFragment extends BaseFragment{
	private static final boolean DEBUG = true;
	private static final String TAG = "MyCenter";
	
	private View mRootView;

    private ImageView mImgBasicInfo;
    private ImageView mImgCommunicationInfo;
    private ImageView mImgHealthInfo;

    private TextView mTxtUsername;
    private TextView mTxtCheckinNum;
    private TextView mTxtCheckinDayNum;
    private TextView mTxtCheckinMaxDayNum;
    private CircleImageView mImgAvatar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mRootView = inflater.inflate(R.layout.my_center_layout, null);
		return mRootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		debug("onViewCreated");

        setHasOptionsMenu(true);

		initView();
		initVariable();
        initListener();
        setView();
    }

    private void setView() {
        String checkinDayNum = PreferencesUtils.getString(mActivity, "continueDays", "0");
        String maxCheckinDayNum = PreferencesUtils.getString(mActivity, "maxSignDays", "0");
        String username = PreferencesUtils.getString(mActivity, "username", "");
        String imgUrl = PreferencesUtils.getString(mActivity, "headImg", "");

        mTxtUsername.setText(username);
        mTxtCheckinNum.setText(checkinDayNum);
        mTxtCheckinDayNum.setText(getString(R.string.checkin_day_num, checkinDayNum));
        mTxtCheckinMaxDayNum.setText(getString(R.string.max_checkin_day_num, maxCheckinDayNum));

        if (!Strings.isNullOrEmpty(imgUrl)) {

            AppContext.getmImageLoader().displayImage(
                    imgUrl,
                    mImgAvatar,
                    AppContext.getImageOptions(R.drawable.title_profile));
        }

    }

    @Override
	protected void initView() {
        // TODO Auto-generated method stub
        mImgBasicInfo = (ImageView) mRootView.findViewById(R.id.img_basic_info);
        mImgCommunicationInfo = (ImageView) mRootView.findViewById(R.id.img_communication_info);
        mImgHealthInfo = (ImageView) mRootView.findViewById(R.id.img_health_info);
        mImgAvatar = (CircleImageView) mRootView.findViewById(R.id.txt_user_avatar);
        mTxtUsername = (TextView) mRootView.findViewById(R.id.txt_user_name);
        mTxtCheckinNum = (TextView) mRootView.findViewById(R.id.txt_user_checkin_num);
        mTxtCheckinDayNum = (TextView) mRootView.findViewById(R.id.txt_checkin_day_num);
        mTxtCheckinMaxDayNum = (TextView) mRootView.findViewById(R.id.txt_max_checkin_day_num);
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
        mImgBasicInfo.setOnClickListener(onClickListener);
        mImgCommunicationInfo.setOnClickListener(onClickListener);
        mImgHealthInfo.setOnClickListener(onClickListener);
	}

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.img_basic_info:
                    intent.setClass(mActivity, BasicInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.img_communication_info:
                    intent.setClass(mActivity, CommunicationInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.img_health_info:
                    intent.setClass(mActivity, HealthInfoActivity.class);
                    startActivity(intent);
                    break;
            }
            mActivity.overridePendingTransition(R.anim.translate_enter_anim, R.anim.scale_exit_anim);
        }
    };

	private void debug(String msg) {
		// TODO Auto-generated method stub
		if (DEBUG) {
			Log.i(TAG, msg);
		}
	}

}
