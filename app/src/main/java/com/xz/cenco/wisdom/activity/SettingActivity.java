package com.xz.cenco.wisdom.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xcolorpicker.android.OnColorSelectListener;
import com.xcolorpicker.android.XColorPicker;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.util.SPUtil;
import com.xz.cenco.wisdom.util.Util;
import com.xz.cenco.wisdom.util.WallpaperDrawable;

/**
 * Created by Administrator on 2018/2/23.
 */

public class SettingActivity extends Activity implements View.OnTouchListener, OnColorSelectListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    TextView mFloatTv;
    int downY = 0;
    Dialog dialog;
    View textColorView;
    View bgColorView;
    SeekBar alphaseekbar;
    SeekBar sizeseekbar;
    SeekBar startSeekbar;
    SeekBar stopSeekbar;
    CheckBox bgCheck;
    CheckBox textDirectionCheck;
    EditText intervalTime;

    static final int type_text = 0;
    static final int type_bg = 1;
    int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        createFloatView();
        initView();
    }

    private void initView() {

//        setBackground();

        textColorView = findViewById(R.id.textColorView);
        textColorView.setBackgroundColor(SPUtil.getColor(this));
        bgColorView = findViewById(R.id.bgColorView);
        bgColorView.setBackgroundColor(SPUtil.getBgColor(this));

        alphaseekbar = findViewById(R.id.seekbar);
        alphaseekbar.setOnSeekBarChangeListener(this);
        int progress = (int) (SPUtil.getAlpha(this) * 100);
        alphaseekbar.setProgress(progress);

        sizeseekbar = findViewById(R.id.sizeseekbar);
        sizeseekbar.setOnSeekBarChangeListener(this);
        sizeseekbar.setProgress(SPUtil.getSize(this));

        startSeekbar = findViewById(R.id.startSeekbar);
        startSeekbar.setOnSeekBarChangeListener(this);
        startSeekbar.setMax(ScreenUtil.getScreenWidth(this));
        int startX = SPUtil.getStartX(this);
        LogUtils.i("startX = "+ startX);
        startSeekbar.setProgress(startX);

        stopSeekbar = findViewById(R.id.stopSeekbar);
        stopSeekbar.setOnSeekBarChangeListener(this);
        stopSeekbar.setMax(ScreenUtil.getScreenWidth(this));
        int stopX = SPUtil.getStopX(this);
        LogUtils.i("stopX = "+ stopX);
        stopSeekbar.setProgress(stopX);

        bgCheck = findViewById(R.id.bgCheck);
        bgCheck.setOnCheckedChangeListener(this);
        bgCheck.setChecked(SPUtil.hasBgColor(this));
        bgColorView.setEnabled(SPUtil.hasBgColor(this));

        textDirectionCheck = findViewById(R.id.textDirection);
        textDirectionCheck.setOnCheckedChangeListener(this);
        textDirectionCheck.setChecked(SPUtil.getOritation(this));

        intervalTime = findViewById(R.id.intervalTime);
        intervalTime.setText(""+SPUtil.getInterval(this));

    }

    public void colorClick(View v) {

        if (v == textColorView) {
            type = type_text;
        } else {
            type = type_bg;
        }

        XColorPicker picker = new XColorPicker(this);
        picker.setOnColorSelectListener(this);

        dialog = new Dialog(this);
        dialog.setContentView(picker);
        dialog.show();
    }


    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = SPUtil.getMode(this);
//        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = SPUtil.getPositionX(this);
        wmParams.y = SPUtil.getPositionY(this);

        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = Util.getStatusBarHeight(this);

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout2, null);
        mFloatLayout.setOnTouchListener(this);
        mWindowManager.addView(mFloatLayout, wmParams);
        mFloatTv = mFloatLayout.findViewById(R.id.float_id);

        resetFloatView();
    }

    private void resetFloatView() {
        mFloatTv.setAlpha(SPUtil.getAlpha(this));
        mFloatTv.setTextSize(SPUtil.getSize(this));
        mFloatTv.setTextColor(SPUtil.getColor(this));
        if (SPUtil.hasBgColor(this)) {
            mFloatTv.setBackgroundColor(SPUtil.getBgColor(this));
        } else {
            mFloatTv.setBackgroundColor(Color.TRANSPARENT);
        }

        int screenWidth = ScreenUtil.getScreenWidth(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mFloatTv.getLayoutParams();
        int startX = SPUtil.getStartX(this);
        int stopX = SPUtil.getStopX(this);
//        LogUtil.i("startX="+startX+",stopX="+stopX);
        params.rightMargin = screenWidth - stopX;
        params.leftMargin = startX;
        mFloatTv.setLayoutParams(params);
    }

    @Override
    //此处必须返回false，否则OnClickListener获取不到监听
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                int distance = y - downY;
                wmParams.y += distance;
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                SPUtil.setPositionY(this, wmParams.y);
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

//        return isClick;
        return false;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        String s = intervalTime.getText().toString().trim();
        if (!TextUtils.isEmpty(s)){
            int i = Integer.parseInt(s);
            SPUtil.setInterval(this,i);
        }
        super.onDestroy();
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }

    @Override
    public void onColorSelected(int newColor, int oldColor) {
        dialog.dismiss();
        if (type == type_text) {
            textColorView.setBackgroundColor(newColor);
            SPUtil.setColor(this, newColor);
            resetFloatView();
        } else if (type == type_bg) {
            bgColorView.setBackgroundColor(newColor);
            SPUtil.setBgColor(this, newColor);
            resetFloatView();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (!fromUser){
            return;
        }
        if (seekBar == alphaseekbar) {
            float alpha = progress * 1.0f / 100;
            SPUtil.setAlpha(this, alpha);
            resetFloatView();
        } else if (seekBar == sizeseekbar) {
            SPUtil.setSize(this, progress);
            resetFloatView();
        }else if (seekBar == startSeekbar){
            SPUtil.setStartX(this,progress);
            resetFloatView();
        }else if (seekBar == stopSeekbar){
            SPUtil.setStopX(this,progress);
            resetFloatView();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == bgCheck) {
            SPUtil.setHasBgColor(this, isChecked);
            bgColorView.setEnabled(isChecked);
            resetFloatView();
        }
        if (buttonView == textDirectionCheck){

        }
    }


    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private void setBackground() {
        View view = findViewById(R.id.mainView);
        WallpaperManager wm = WallpaperManager.getInstance(this);
        Drawable d = wm.getDrawable();
        if (d != null) {
            Bitmap bitmap = drawableToBitmap(d);
            WallpaperDrawable wd = new WallpaperDrawable();
            wd.setBitmap(bitmap);
            view.setBackground(d);
        } else {
            view.setBackgroundColor(Color.WHITE);
        }
    }



}
