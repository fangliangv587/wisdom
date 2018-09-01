package cenco.xz.fangliang.wisdom.core;

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import com.cenco.lib.common.FileUtils;
import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;

import cenco.xz.fangliang.wisdom.weed.TimerService;

/**
 * Created by Administrator on 2018/2/23.
 */

public class SettingActivity extends Activity implements  SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    //定义浮动窗口布局
//    LinearLayout mFloatLayout;
//    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
//    WindowManager mWindowManager;

//    TextView mFloatTv;
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

    static  final int request_text_color  = 0x0001;
    static  final int request_bg_color  = 0x0002;

    TimerService wisdomService;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.MyBinder binder = (TimerService.MyBinder) service;
            wisdomService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        initView();
        bindService();
    }

    private void initView() {

//        setBackground();

        textColorView = findViewById(R.id.textColorView);
        textColorView.setBackgroundColor(SPUtil.getColor(this));
        bgColorView = findViewById(R.id.bgColorView);
        bgColorView.setBackgroundColor(SPUtil.getBgColor(this));

        alphaseekbar = (SeekBar) findViewById(R.id.seekbar);
        alphaseekbar.setOnSeekBarChangeListener(this);
        int progress = (int) (SPUtil.getAlpha(this) * 100);
        alphaseekbar.setProgress(progress);

        sizeseekbar = (SeekBar) findViewById(R.id.sizeseekbar);
        sizeseekbar.setOnSeekBarChangeListener(this);
        sizeseekbar.setProgress(SPUtil.getSize(this));

        startSeekbar =(SeekBar)  findViewById(R.id.startSeekbar);
        startSeekbar.setOnSeekBarChangeListener(this);
        startSeekbar.setMax(ScreenUtil.getScreenWidth(this));
        int startX = SPUtil.getStartX(this);
        LogUtils.i("startX = "+ startX);
        startSeekbar.setProgress(startX);

        stopSeekbar =(SeekBar)  findViewById(R.id.stopSeekbar);
        stopSeekbar.setOnSeekBarChangeListener(this);
        stopSeekbar.setMax(ScreenUtil.getScreenWidth(this));
        int stopX = SPUtil.getStopX(this);
        LogUtils.i("stopX = "+ stopX);
        stopSeekbar.setProgress(stopX);

        bgCheck = (CheckBox) findViewById(R.id.bgCheck);
        bgCheck.setOnCheckedChangeListener(this);
        bgCheck.setChecked(SPUtil.hasBgColor(this));
        bgColorView.setEnabled(SPUtil.hasBgColor(this));

        textDirectionCheck =(CheckBox)  findViewById(R.id.textDirection);
        textDirectionCheck.setOnCheckedChangeListener(this);
        textDirectionCheck.setChecked(SPUtil.getOritation(this));

        intervalTime =(EditText) findViewById(R.id.intervalTime);
        intervalTime.setText(""+SPUtil.getInterval(this));

    }

    public void openWisdomFileClick(View v) {
        boolean fileExists = FileUtils.isFileExists(C.file.wisdom_path);
        if (!fileExists){
            FileUtils.createOrExistsFile(C.file.wisdom_path);
        }

        openFile(this,new File(C.file.wisdom_path));
    }

    public  void openFile(Context context,File file) {
        //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMimeType(file.getAbsolutePath());
//        //设置intent的data和Type属性。
//        Uri uri = FileProvider7.getUriForFile(context, file);
//        intent.setDataAndType(uri, type);

        FileProvider7.setIntentDataAndType(context,intent,type,file,true);
        //跳转
        context.startActivity(intent);

    }

    private String getMimeType(String filePath) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
    }
    public void colorClick(View v) {

        int request = 0;
        int color =0;
        if (v == textColorView) {
            request = request_text_color;
            color = SPUtil.getColor(this);
        } else {
            request = request_bg_color;
            color = SPUtil.getBgColor(this);
        }

        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(C.extra.color,color);
        startActivityForResult(intent,request);

    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        unbindService(conn);

        String s = intervalTime.getText().toString().trim();
        if (!TextUtils.isEmpty(s)){
            int i = Integer.parseInt(s);
            SPUtil.setInterval(this,i);
        }
        super.onDestroy();

    }

    private void bindService() {
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);


    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (!fromUser){
            return;
        }
        if (seekBar == alphaseekbar) {
            float alpha = progress * 1.0f / 100;
            SPUtil.setAlpha(this, alpha);
        } else if (seekBar == sizeseekbar) {
            SPUtil.setSize(this, progress);
        }else if (seekBar == startSeekbar){
            SPUtil.setStartX(this,progress);
        }else if (seekBar == stopSeekbar){
            SPUtil.setStopX(this,progress);
        }

        resetFloatWindow();
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
        }
        if (buttonView == textDirectionCheck){

        }

        resetFloatWindow();
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode == request_text_color){
            int color = data.getIntExtra(C.extra.color, 0);
            SPUtil.setColor(this,color);
            textColorView.setBackgroundColor(color);
        }

        if (requestCode == request_bg_color){
            int color = data.getIntExtra(C.extra.color, 0);
            SPUtil.setBgColor(this,color);
            bgColorView.setBackgroundColor(color);
        }
        resetFloatWindow();
    }


    public void resetFloatWindow(){
        if (wisdomService!=null){
            wisdomService.updateWisdom();
        }
    }
}
