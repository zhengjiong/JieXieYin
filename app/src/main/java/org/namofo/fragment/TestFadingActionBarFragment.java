package org.namofo.fragment;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.namofo.R;
import org.namofo.ui.ListeningScrollView;
import org.namofo.ui.MainActivity;

/**
 * @Author: zhengjiong
 * Date: 2014-09-21
 * Time: 19:55
 */
public class TestFadingActionBarFragment extends Fragment{
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.test, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListeningScrollView scrollView = (ListeningScrollView) mRootView.findViewById(R.id.scrollview);
        ActionBar actionBar = ((MainActivity)getActivity()).getActionBar();

        RelativeLayout headview = (RelativeLayout) mRootView.findViewById(R.id.headview);

        ColorDrawable backgroundDrawable = new ColorDrawable(0xbe000000);
        actionBar.setBackgroundDrawable(backgroundDrawable);

        scrollView.setActionBarBackgroundDrawable(backgroundDrawable,
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) ? callback : null);
        scrollView.setActionBar(((MainActivity)getActivity()).getActionBar());
        Log.i("zj", "actionBar.getHeight()=" + actionBar.getHeight());
        scrollView.setTransparentHeaderView(headview);
        scrollView.setHeaderviewImg((android.widget.ImageView) mRootView.findViewById(R.id.headview_img));
    }


    private Drawable.Callback callback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            ((MainActivity)getActivity()).getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };
}
