package com.xz.cenco.wisdom;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.TimerHelper;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.util.SPUtil;

import java.util.List;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class FxService2 extends Service implements TimerHelper.TimerListener {

    private static final String TAG = "FxService";
    TextView mFloatTv;
    TimerHelper timerHelper;
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i(TAG, "oncreat");
        createFloatView();
        setFloatContent();
        initTimer();
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
        WindowManager mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
        wmParams.type = LayoutParams.TYPE_PHONE;
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

        //设置悬浮窗口长宽数据
        wmParams.width = LayoutParams.MATCH_PARENT;
        wmParams.height = LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());  
        //获取浮动窗口视图所在布局  
        LinearLayout mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout2, null);

        //添加mFloatLayout  
        mWindowManager.addView(mFloatLayout, wmParams);  
        //浮动窗口按钮  
        mFloatTv = mFloatLayout.findViewById(R.id.float_id);
        resetFloatView();

    }

    private void resetFloatView() {
        mFloatTv.setAlpha(SPUtil.getAlpha(this));
        mFloatTv.setTextSize(SPUtil.getSize(this));
        mFloatTv.setTextColor(SPUtil.getColor(this));
        if (SPUtil.hasBgColor(this)){
            mFloatTv.setBackgroundColor(SPUtil.getBgColor(this));
        }else {
            mFloatTv.setBackgroundColor(Color.TRANSPARENT);
        }

    }
      
    @Override  
    public void onDestroy()   
    {  
        // TODO Auto-generated method stub  
        super.onDestroy();  

    }


    private String getShowWisdom(){
        App app = (App) getApplication();
        WisdomDao wisdomDao = app.getDaoSession().getWisdomDao();
        List<Wisdom> list = wisdomDao.queryBuilder().list();
        if (list==null || list.size() ==0){
            return "";
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