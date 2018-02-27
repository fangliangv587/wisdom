package com.xz.cenco.wisdom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
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

import com.cenco.lib.common.BitmapUtil;
import com.cenco.lib.common.LogUtil;
import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.TimerHelper;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.util.SPUtil;
import com.xz.cenco.wisdom.util.Util;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;


public class FxService2 extends Service implements TimerHelper.TimerListener {

    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private static final String TAG = "FxService";
    TextView mFloatTv;
    TimerHelper timerHelper;

    private  boolean test = false;

    private MediaProjection mMediaProjection;
    private ImageReader mImageReader = null;
    private VirtualDisplay mVirtualDisplay = null;

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i(TAG, "oncreat");
        createFloatView();
        setFloatContent();
        initTimer();
        initCapture();
    }

    private void initCapture() {
        int screenHeight = ScreenUtil.getScreenHeight(this);
        int screenWidth = ScreenUtil.getScreenWidth(this);
        mImageReader = ImageReader.newInstance(screenWidth, screenHeight, 0x1, 2); //ImageFormat.RGB_565
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
        wmParams.height = LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());  
        //获取浮动窗口视图所在布局  
        LinearLayout mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout2, null);
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
//        mFloatTv.setTextColor(SPUtil.getColor(this));
        if (SPUtil.hasBgColor(this)){
            int bgColor = SPUtil.getBgColor(this);
            int red = Color.red(bgColor);
            int green = Color.green(bgColor);
            int blue = Color.blue(bgColor);
            LogUtil.i("背景色：red:"+red+",green:"+green+",blue:"+blue);
            mFloatTv.setBackgroundColor(bgColor);
        }else {
            mFloatTv.setBackgroundColor(Color.TRANSPARENT);
        }

    }
      
    @Override  
    public void onDestroy(){
        // TODO Auto-generated method stub
        if (mVirtualDisplay != null){
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
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

        if (current != 0 && current%3==0 ){
            capture();
        }
    }

    @Override
    public void onTimerOver() {
        setFloatContent();
        int interval = SPUtil.getInterval(this);
        timerHelper.setTotalSecond(interval);
        timerHelper.start();
    }


    private void capture(){
        LogUtil.i("capture");
        if (mMediaProjection ==null){
            mMediaProjection = App.mediaProjectionManager.getMediaProjection(App.captureResultCode, App.captureIntent);
        }
        int screenHeight = ScreenUtil.getScreenHeight(this);
        int screenWidth = ScreenUtil.getScreenWidth(this);
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                screenWidth, screenHeight, App.screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Image image = mImageReader.acquireLatestImage();
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width+rowPadding/pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        image.close();

        int statusBarHeight = Util.getStatusBarHeight(this);
        LogUtil.i("状态栏高度:"+statusBarHeight);
        int positionX = SPUtil.getPositionX(this);
        int positionY = SPUtil.getPositionY(this)  +10;
        int color = bitmap.getPixel(positionX, positionY);
        bitmap.recycle();
        int inverseColor = Util.getInverseColor(color);
        mFloatTv.setTextColor(inverseColor);


    }


}