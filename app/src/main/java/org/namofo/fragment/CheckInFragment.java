package org.namofo.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.namofo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import hirondelle.date4j.DateTime;

/**
 * 我要簽到
 * @Author: zhengjiong
 * Date: 2014-08-11
 * Time: 07:47
 */
public class CheckInFragment extends BaseFragment{
    private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    private View mRootView;
    private View mCalendarView;

    private CaldroidFragment mCaldroidFragment;

    private HashMap<String, HashMap<DateTime, Integer>> mDateMap = new HashMap<String, HashMap<DateTime, Integer>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.check_in_layout, null);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVariable();
        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        try {
            initCalendarView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void initCalendarView() throws ParseException {
        mCaldroidFragment = new CaldroidFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

        // Uncomment this to customize startDayOfWeek
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
        CaldroidFragment.MONDAY); // MONDAY
        mCaldroidFragment.setArguments(args);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.calendar_view, mCaldroidFragment);
        transaction.commit();

        /**
         * 設置背景顏色
         */
        HashMap<DateTime, Integer> backgroundDateMap = new HashMap<DateTime, Integer>();

        DateTime dateTime = new DateTime(2014, 8, 10, 0, 0, 0, 0);
        backgroundDateMap.put(dateTime, android.R.color.holo_green_light);

        mCaldroidFragment.setBackgroundResourceForDateTimes(backgroundDateMap);

        /**
         * 設置字體顏色
         */
        HashMap<DateTime, Integer> textColordateMap = new HashMap<DateTime, Integer>();
        textColordateMap.put(dateTime, android.R.color.white);

        mCaldroidFragment.setTextColorForDateTimes(textColordateMap);
    }

    /**
     * 日曆點擊事件
     */
    private CaldroidListener mCaldroidListener = new CaldroidListener() {


        @Override
        public void onSelectDate(Date date, View view) {
            mCaldroidFragment.setSelectedDates(date, date);
            mCaldroidFragment.refreshView();
        }
    };

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mCaldroidFragment.setCaldroidListener(mCaldroidListener);
    }
}
