package cenco.xz.fangliang.wisdom.core;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WisdomService extends Service implements TimerHelper.TimerListener, View.OnSystemUiVisibilityChangeListener {

    TextView mFloatTv;
    TimerHelper timerHelper;


    WindowManager mWindowManager;
    LinearLayout mFloatLayout;


    public static final int NOTICE_ID = 100;
    int totalSecond;

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        LogUtils.i("FxService2 oncreate");
        initView();
        initTimer();
        startActivityMonitor();
    }

    private void startActivityMonitor() {

//        monitorThread = new ActivityMonitorThread(this);
//        monitorThread.start();
    }

    private void initView() {
        createFloatView();
        setFloatContent();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("onStartCommand");
        return START_STICKY;

    }

    private int setFloatContent() {
        String wisdom = getShowWisdom();
        mFloatTv.setText(wisdom);
        mFloatTv.setSelected(true);
        if (TextUtils.isEmpty(wisdom)){
            return 0;
        }else {
            return wisdom.length();
        }
    }

    private void initTimer() {
        timerHelper = new TimerHelper(this);
        int interval = SPUtil.getInterval(this);
        timerHelper.setTotalSecond(interval);
        timerHelper.start();
    }

    public class MyBinder extends Binder {

        public WisdomService getService(){
            return WisdomService.this;
        }
    }
    private MyBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return binder;
    }



    private void createFloatView()
    {
        LayoutParams wmParams = new LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
//        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.type = Util.getWindowType();
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
        mFloatLayout.setOnSystemUiVisibilityChangeListener(this);
        //添加mFloatLayout  
        mWindowManager.addView(mFloatLayout, wmParams);


        //浮动窗口按钮  
        mFloatTv = (TextView) mFloatLayout.findViewById(R.id.float_id);
        resetFloatView();


    }

    public void resetFloatView() {

        if (mFloatTv == null){
            return;
        }

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



        super.onDestroy();
    }


    private String getShowWisdom(){

            return "好好学习，天天向上!";
    }



    @Override
    public void onTimerRunning(int current, int total,boolean isOver) {

        if (!isOver){
            return;
        }
        int textLength = setFloatContent();
        int textTime = textLength * 2;
        int interval = SPUtil.getInterval(this);
        int max = textTime>interval?textTime:interval;
        timerHelper.setTotalSecond(max);
        timerHelper.start();
    }




    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        int a = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        Log.d("xzsystemui","visibility:"+visibility);
    }


}