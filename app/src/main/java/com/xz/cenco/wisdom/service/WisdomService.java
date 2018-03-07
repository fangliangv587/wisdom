package com.xz.cenco.wisdom.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.ScreenListener;
import com.xz.cenco.wisdom.activity.App;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.util.SPUtil;
import com.xz.cenco.wisdom.util.Util;

import java.util.List;
import java.util.Random;


public class WisdomService extends Service implements TimerHelper.TimerListener {

    TextView mFloatTv;
    TimerHelper timerHelper;
    ScreenListener screenListener;

    WindowManager mWindowManager;
    LinearLayout mFloatLayout;

    public static final int NOTICE_ID = 100;

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        LogUtils.i("FxService2 oncreate");
        initView();
        initTimer();
        initScreenListner();
        startForeground();
    }

    private void startForeground() {

    }

    private void initView() {
        createFloatView();
        setFloatContent();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("onStartCommand");
        return START_STICKY;

    }

    private void initScreenListner() {
        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                LogUtils.i( "解锁");
                mFloatTv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScreenOn() {
                KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
                LogUtils.i( "开屏  "+ flag);
                if (flag){
                    mFloatTv.setVisibility(View.INVISIBLE);
                }else {
                    mFloatTv.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScreenOff() {
                LogUtils.i(  "锁屏");
                mFloatTv.setVisibility(View.INVISIBLE);
            }
        });
    }


    private void setFloatContent() {
        String wisdom = getShowWisdom();
        mFloatTv.setText(wisdom);
        mFloatTv.setSelected(true);
    }

    private void initTimer() {
        timerHelper = new TimerHelper(this);
        int interval = SPUtil.getInterval(this);
        timerHelper.setTotalSecond(interval);
        timerHelper.start();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
    }



    private void createFloatView()
    {
        LayoutParams wmParams = new LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
//        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.type = LayoutParams.TYPE_SYSTEM_OVERLAY;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = SPUtil.getMode(this);
//        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE ;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = SPUtil.getPositionX(this);
        wmParams.y = SPUtil.getPositionY(this);
        wmParams.y -= Util.getStatusBarHeight(this);

        //设置悬浮窗口长宽数据
        wmParams.width = LayoutParams.MATCH_PARENT;
        wmParams.height = Util.getStatusBarHeight(this);;

        LayoutInflater inflater = LayoutInflater.from(getApplication());  
        //获取浮动窗口视图所在布局  
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout2, null);
//        mFloatLayout.setBackgroundColor(Color.argb(128,255,0,0));
        //添加mFloatLayout  
        mWindowManager.addView(mFloatLayout, wmParams);  
        //浮动窗口按钮  
        mFloatTv = mFloatLayout.findViewById(R.id.float_id);
        resetFloatView();


    }

    private void resetFloatView() {
        mFloatTv.setAlpha(SPUtil.getAlpha(this));
        mFloatTv.setTextSize(SPUtil.getSize(this));
        if (!SPUtil.getAutocolor(this)){
            mFloatTv.setTextColor(SPUtil.getColor(this));
        }
        if (SPUtil.hasBgColor(this)){
            int bgColor = SPUtil.getBgColor(this);
            int red = Color.red(bgColor);
            int green = Color.green(bgColor);
            int blue = Color.blue(bgColor);
            LogUtils.i("背景色：red:"+red+",green:"+green+",blue:"+blue);
            mFloatTv.setBackgroundColor(bgColor);
        }else {
            mFloatTv.setBackgroundColor(Color.TRANSPARENT);
        }


        int screenWidth = ScreenUtil.getScreenWidth(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mFloatTv.getLayoutParams();
        int startX = SPUtil.getStartX(this);
        int stopX = SPUtil.getStopX(this);
        LogUtils.i("startX="+startX+",stopX="+stopX);
        params.rightMargin = screenWidth - stopX;
        params.leftMargin = startX;
        mFloatTv.setLayoutParams(params);

    }
      
    @Override  
    public void onDestroy(){
        // TODO Auto-generated method stub


        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }

        if (screenListener!=null){
            screenListener.stop();
        }
        super.onDestroy();
    }


    private String getShowWisdom(){
        App app = (App) getApplication();
        WisdomDao wisdomDao = app.getDaoSession().getWisdomDao();
        List<Wisdom> list = wisdomDao.queryBuilder().list();
        if (list==null || list.size() ==0){
            return "还没有添加名言警句!";
        }
        Random random = new Random();
        int index = random.nextInt(list.size());
        Wisdom wisdom = list.get(index);
        return wisdom.getText();

    }


    @Override
    public void onTimerRunning(int current, int total) {


    }

    @Override
    public void onTimerOver() {
        setFloatContent();
        int interval = SPUtil.getInterval(this);
        timerHelper.setTotalSecond(interval);
        timerHelper.start();
    }







}