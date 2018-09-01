package cenco.xz.fangliang.wisdom.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.TimerHelper;
import com.xz.cenco.wisdom.R;

import cenco.xz.fangliang.wisdom.App;
import cenco.xz.fangliang.wisdom.widget.MarqueeText;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class WisdomHelper implements MarqueeText.MarqueeListener {


    private Context service;
    private MarqueeText mFloatTv;
    private WindowManager mWindowManager;
    private LinearLayout mFloatLayout;
    private List<String> list = new ArrayList<>();
    private int index;
    private Object lock = new Object();
    private int timerInterval = 3600 * 1000;


    public WisdomHelper(Context context) {
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
        mFloatTv = (MarqueeText) mFloatLayout.findViewById(R.id.float_id);
        mFloatTv.setListener(this);
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
//            LogUtils.i("背景色：red:" + red + ",green:" + green + ",blue:" + blue);
            mFloatTv.setBackgroundColor(bgColor);
        } else {
            mFloatTv.setBackgroundColor(Color.TRANSPARENT);
        }


        int screenWidth = ScreenUtil.getScreenWidth(service);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mFloatTv.getLayoutParams();
        int startX = SPUtil.getStartX(service);
        int stopX = SPUtil.getStopX(service);
//        LogUtils.i("startX=" + startX + ",stopX=" + stopX);
        params.rightMargin = screenWidth - stopX;
        params.leftMargin = startX;
        mFloatTv.setLayoutParams(params);
        mFloatTv.setSelected(true);

    }


    private String getShowWisdom() {return "世界那么大,我想去看看！";}

    private CountDownTimer timer;

    private void action() {


        mFloatTv.startScroll();
        resetData();

        timer = new CountDownTimer(timerInterval,timerInterval){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                timer.start();
                resetData();
            }
        };

        timer.start();

    }

    public void resetData(){
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<String> strings = IOUtils.readFile2List(C.file.wisdom_path);
                if (strings==null){
                    return;
                }
                synchronized (lock){
                    list.clear();
                    list.addAll(strings);
                }

            }
        });

    }


    @Override
    public void onMarqueeNext() {
        synchronized (lock){
            if (list==null || list.size()==0){
                mFloatTv.setText("世界那么大,我想去看看！");
                return;
            }
            if (index>=list.size()){
                index=0;
            }
            String str = list.get(index);
            mFloatTv.setText(str);
            index++;
        }


    }



}