package com.xz.cenco.wisdom.service;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.App;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.util.SPUtil;
import com.xz.cenco.wisdom.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class WisdomHelper implements TimerHelper.TimerListener {


    private Service service;
    private int total;
    private int tempTotal;
    private TextView mFloatTv;
    private WindowManager mWindowManager;
    private LinearLayout mFloatLayout;
    private List<Wisdom> filterList;

    public WisdomHelper(Service context) {
        this.service = context;
        createFloatView();
        resetFloatView();
    }

    public void start() {
        action();
    }

    public void stop() {

        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }


    private void createFloatView() {
        LayoutParams wmParams = new LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) service.getSystemService(Context.WINDOW_SERVICE);
        //设置window type
//        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.type = Util.getWindowType();
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = SPUtil.getMode(service);
//        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE ;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = SPUtil.getPositionX(service);
        wmParams.y = SPUtil.getPositionY(service);
        wmParams.y -= Util.getStatusBarHeight(service);

        //设置悬浮窗口长宽数据
        wmParams.width = LayoutParams.MATCH_PARENT;
        wmParams.height = Util.getStatusBarHeight(service);
        ;

        LayoutInflater inflater = LayoutInflater.from(service);
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout2, null);
//        mFloatLayout.setBackgroundColor(Color.argb(128,255,0,0));
//        mFloatLayout.setOnSystemUiVisibilityChangeListener(this);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);

        //浮动窗口按钮
        mFloatTv = (TextView) mFloatLayout.findViewById(R.id.float_id);
    }

    public void resetFloatView() {

        if (mFloatTv == null) {
            return;
        }

        mFloatTv.setAlpha(SPUtil.getAlpha(service));
        mFloatTv.setTextSize(SPUtil.getSize(service));
        if (!SPUtil.getAutocolor(service)) {
            mFloatTv.setTextColor(SPUtil.getColor(service));
        }
        if (SPUtil.hasBgColor(service)) {
            int bgColor = SPUtil.getBgColor(service);
            int red = Color.red(bgColor);
            int green = Color.green(bgColor);
            int blue = Color.blue(bgColor);
            LogUtils.i("背景色：red:" + red + ",green:" + green + ",blue:" + blue);
            mFloatTv.setBackgroundColor(bgColor);
        } else {
            mFloatTv.setBackgroundColor(Color.TRANSPARENT);
        }


        int screenWidth = ScreenUtil.getScreenWidth(service);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mFloatTv.getLayoutParams();
        int startX = SPUtil.getStartX(service);
        int stopX = SPUtil.getStopX(service);
        LogUtils.i("startX=" + startX + ",stopX=" + stopX);
        params.rightMargin = screenWidth - stopX;
        params.leftMargin = startX;
        mFloatTv.setLayoutParams(params);
        mFloatTv.setSelected(true);

    }


    private String getShowWisdom() {
        if (filterList == null || filterList.size() == 0) {
            App app = (App) service.getApplication();
            WisdomDao wisdomDao = app.getDaoSession().getWisdomDao();
            List<Wisdom> list = wisdomDao.queryBuilder().list();
            filterList = filter(list);
            LogUtils.d("查询数据库");
        }

        if (filterList == null || filterList.size() == 0) {
            return "好好学习，天天向上!";
        }

        Random random = new Random();
        int index = random.nextInt(filterList.size());
        Wisdom wisdom = filterList.get(index);
        filterList.remove(index);
        return wisdom.getText();

    }

    private List<Wisdom> filter(List<Wisdom> list) {
        if (list == null) {
            return null;
        }
        ArrayList<Wisdom> fliterList = new ArrayList<>();
        Date date = new Date();
        for (Wisdom wisdom : list) {
            String startDateStr = wisdom.getStartDate();
            String stopDateStr = wisdom.getStopDate();
            String startPeriodTimeStr = wisdom.getStartPeriodTime();
            String stopPeriodTimeStr = wisdom.getStopPeriodTime();

            Date startDate = DateUtil.getDate(startDateStr, DateUtil.FORMAT_YMD);
            Date stopDate = DateUtil.getDate(stopDateStr, DateUtil.FORMAT_YMD);
            Date startTime = DateUtil.getDate(startPeriodTimeStr, DateUtil.FORMAT_HM);
            Date stopTime = DateUtil.getDate(stopPeriodTimeStr, DateUtil.FORMAT_HM);

            if (DateUtil.isInPeriodDate(date, startDate, stopDate, DateUtil.FORMAT_YMD) && DateUtil.isInPeriodDate(date, startTime, stopTime, DateUtil.FORMAT_HM)) {
                fliterList.add(wisdom);
            }

        }
        return fliterList;
    }


    private void action() {
        String wisdom = getShowWisdom();
        mFloatTv.setText(wisdom);
        mFloatTv.setSelected(true);

        int textLength = wisdom.length();
        int textTime = textLength * 2;
        int interval = SPUtil.getInterval(service);
        int max = textTime > interval ? textTime : interval;
        LogUtils.d(wisdom + "----显示时间:" + max);

        total = max;
        tempTotal = total;


    }


    @Override
    public void onTimerRunning(int current, int total, boolean isOver) {

        tempTotal--;
        if (tempTotal == 0) {
            action();
        }
    }


}