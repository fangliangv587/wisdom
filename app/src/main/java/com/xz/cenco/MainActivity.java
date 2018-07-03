package com.xz.cenco;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cenco.lib.common.AssetUtil;
import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.PermissionManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.assits.DayRecordListActivity;
import com.xz.cenco.doctor.DoctorQueryActivity;
import com.xz.cenco.test.TestActivity;
import com.xz.cenco.weed.TimerService;
import com.xz.cenco.weed.WeedListActivity;
import com.xz.cenco.wisdom.BuildConfig;
import com.xz.cenco.wisdom.activity.App;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.WisdomActivity;
import com.xz.cenco.wisdom.bean.Backups;
import com.xz.cenco.wisdom.bean.Setting;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.util.C;
import com.xz.cenco.wisdom.util.SPUtil;
import com.yanzhenjie.alertdialog.AlertDialog;

import java.io.File;
import java.util.List;

import ezy.assist.compat.SettingsCompat;

public class MainActivity extends BaseActivity {

    private TextView trackStateTv;
    private TextView timerStateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wp);
        initView();
        commonPermission();
        actionReady();
        startTimer();
    }

    private void actionReady() {
        AssetUtil.copyFiles(this,"card",C.file.card_path);
    }

    // 此方法用来判断当前应用的辅助功能服务是否开启
    public  boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    private void commonPermission() {
        PermissionManager manager = new PermissionManager(this);
        manager.requestPermission(null, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startTimer();
        trackStateTv.setText(isAccessibilitySettingsOn(this)? "已开启":"未开启");
    }

    private void initView() {
        trackStateTv = findViewById(R.id.track_state);
        timerStateTv = findViewById(R.id.timerStateTv);
    }



    public void recordClick(View view){

        if(!isAccessibilitySettingsOn(this)){
            AlertDialog.newBuilder(this).setCancelable(false).setTitle(com.cenco.lib.common.R.string.title_dialog).setMessage("需要开启辅助权限才能使用此功能，您要开启此功能吗？").setPositiveButton(com.cenco.lib.common.R.string.resume, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivityForResult(intent, C.request.permission_assist);
                }
            }).setNegativeButton(com.cenco.lib.common.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return;
        }

        Intent intent = new Intent(this, DayRecordListActivity.class);
        startActivity(intent);
    }
    public void contentClick(View view){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
            AlertDialog.newBuilder(this).setCancelable(false).setTitle(com.cenco.lib.common.R.string.title_dialog).setMessage("需要开启悬浮框权限才能使用此功能，您要开启此功能吗？").setPositiveButton(com.cenco.lib.common.R.string.resume, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SettingsCompat.manageDrawOverlays(mContext);
                }
            }).setNegativeButton(com.cenco.lib.common.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return;
        }

        Intent intent = new Intent(this, WisdomActivity.class);
        startActivity(intent);
    }


    public void backupClick(View view){

        Backups backups = new Backups();
        WisdomDao wisdomDao = getApp().getDaoSession().getWisdomDao();

        Setting setting = new Setting();
        setting.setAlpha(SPUtil.getAlpha(this));
        setting.setAutocolor(SPUtil.getAutocolor(this));
        setting.setBgcolor(SPUtil.getBgColor(this));
        setting.setColor(SPUtil.getColor(this));
        setting.setHasBgcolor(SPUtil.hasBgColor(this));
        setting.setInterval(SPUtil.getInterval(this));
        setting.setMode(SPUtil.getMode(this));
        setting.setOritation(SPUtil.getOritation(this));
        setting.setPositionX(SPUtil.getPositionX(this));
        setting.setPositionY(SPUtil.getPositionY(this));
        setting.setSize(SPUtil.getSize(this));
        setting.setStartX(SPUtil.getStartX(this));
        setting.setStopX(SPUtil.getStopX(this));
        backups.setSetting(setting);

        List<Wisdom> wisdoms = wisdomDao.queryBuilder().list();
        backups.setWisdoms(wisdoms);
        backups.setDate(DateUtil.getDateString());

        String content = GsonUtil.toJson(backups);
        boolean result = IOUtils.writeFileFromString(C.file.backup_data_path, content);
        LogUtils.i("数据备份:"+result);
        ToastUtil.show(this,"数据备份"+ (result?"成功":"失败"));
    }
    public void restoreClick(View view){
        File file = new File(C.file.backup_data_path);
        if (!file.exists()){
            ToastUtil.show(this,"尚无备份数据");
            return;
        }
        String content = IOUtils.readFile2String(C.file.backup_data_path);
        final Backups backups = GsonUtil.fromJson(content,Backups.class);
        if (backups==null){
            ToastUtil.show(this,"尚无备份数据");
            return;
        }

        AlertDialog.newBuilder(this).setCancelable(false).setTitle(com.cenco.lib.common.R.string.title_dialog).setMessage("您确定要恢复到 " + backups.getDate() + " 的备份吗?").setPositiveButton(com.cenco.lib.common.R.string.resume, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (backups.getWisdoms()!=null){
                    WisdomDao wisdomDao = getApp().getDaoSession().getWisdomDao();
                    wisdomDao.deleteAll();
                    LogUtils.i("删除源数据成功");

                    List<Wisdom> wisdoms =backups.getWisdoms();
                    for (Wisdom wisdom : wisdoms){
                        wisdomDao.insert(wisdom);
                    }
                    Setting setting = backups.getSetting();

                    SPUtil.setBgColor(mContext,setting.getBgcolor());
                    SPUtil.setColor(mContext,setting.getColor());
                    SPUtil.setStopX(mContext,setting.getStopX());
                    SPUtil.setStartX(mContext,setting.getStartX());
                    SPUtil.setInterval(mContext,setting.getInterval());
                    SPUtil.setHasBgColor(mContext,setting.isHasBgcolor());
                    SPUtil.setSize(mContext,setting.getSize());
                    SPUtil.setAlpha(mContext,setting.getAlpha());
                    SPUtil.setPositionY(mContext,setting.getPositionY());
                    SPUtil.setAutocolor(mContext,setting.isAutocolor());
                    SPUtil.setMode(mContext,setting.getMode());
                    SPUtil.setOritation(mContext,setting.isOritation());
                    SPUtil.setPositionX(mContext,setting.getPositionX());

                    ToastUtil.show(mContext,"数据恢复成功");
                }

            }
        }).setNegativeButton(com.cenco.lib.common.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    public void doctorClick(View view) {
        Intent intent = new Intent(this, DoctorQueryActivity.class);
        startActivity(intent);
    }




    private void startTimer() {
        if (BuildConfig.openTimer){
            Intent intent = new Intent(MainActivity.this, TimerService.class);
            startService(intent);
            App.isTimer = true;
            timerStateTv.setText("定时器状态:开");
        }else {
            timerStateTv.setText("定时器状态:关");
        }
    }

    private void stopTimer(){
        if (BuildConfig.openTimer){
            Intent intent = new Intent(MainActivity.this, TimerService.class);
            stopService(intent);
            App.isTimer = false;
            timerStateTv.setText("定时器状态:关");
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == C.request.permission_dialog){
            contentClick(null);
            return;
        }
        if (requestCode == C.request.permission_assist){
            recordClick(null);
            return;
        }

    }

    public void testClick(View view) {
        if (App.isTimer){
            stopTimer();
        }else {
            startTimer();
        }


    }

    public void weedClick(View view) {

        if (App.isTimer){
            stopTimer();
        }

        Intent intent = new Intent(this, WeedListActivity.class);
        startActivity(intent);
    }
}
