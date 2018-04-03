package com.xz.cenco;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cenco.lib.common.PermissionManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.assits.AssitActivity;
import com.xz.cenco.doctor.DoctorQueryActivity;
import com.xz.cenco.wisdom.activity.SettingActivity;
import com.xz.cenco.wisdom.activity.TypeActivity;
import com.xz.cenco.wisdom.service.MyJobService;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.service.WisdomService;
import com.xz.cenco.wisdom.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    final static String TAG = "AccessibilityUtil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        permission();
        assist();

    }



    // 此方法用来判断当前应用的辅助功能服务是否开启
    public static boolean isAccessibilitySettingsOn(Context context) {
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

    private void assist() {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(this)) {
            // 引导至辅助功能设置页面
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } else {
            LogUtils.i("已经打开了");
            // 执行辅助功能服务相关操作
        }
    }



    private void permission() {
        PermissionManager manager = new PermissionManager(this);
        manager.requestPermission(null, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopWisdom();
    }




    private void keepalive() {
        scheduler();
        startKeepLiveService(this,2000);
    }

    public void startKeepLiveService(Context context, int timeMillis) {
        //获取AlarmManager系统服务
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //包装Intent
        Intent intent = new Intent(context,WisdomService.class);

        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //添加到AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),timeMillis,pendingIntent);
    }

    private void scheduler() {
    /*
    *  http://blog.csdn.net/liubinwyzbt/article/details/79079134
    * */
        int result = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1,
                    new ComponentName(getPackageName(), MyJobService.class.getName()));
            builder.setMinimumLatency(2 * 1000);
            JobInfo build = builder.build();
            int schedule = mJobScheduler.schedule(build);
            result = schedule;
        }else {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo.Builder jb = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()));
            jb .setPeriodic(2*1000);
            JobInfo jobInfo = jb .build();
            int schedule = jobScheduler.schedule(jobInfo);
            result = schedule;
        }

        if (result == JobScheduler.RESULT_SUCCESS){
            LogUtils.i("keep alive success");
        }else{
            LogUtils.e("keep alive fail");
        }
    }



    private void initView() {


    }




    public void recordClick(View view){
        Intent intent = new Intent(this, AssitActivity.class);
        startActivity(intent);
    }
    public void contentClick(View view){

        Intent intent = new Intent(this, TypeActivity.class);
        startActivity(intent);
    }
    public void settingClick(View view){
        if (checkPermission()) return;
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }



    private boolean checkPermission() {
        if(!Settings.canDrawOverlays(this)){
            //没有悬浮窗权限,跳转申请
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(intent);
            ToastUtil.show(this,"请赋予悬浮窗权限");
            return true;
        }
        return false;
    }

    private void stopWisdom(){
        Intent intent = new Intent(MainActivity.this, WisdomService.class);
        stopService(intent);
    }

    private void startWisdom() {
        Intent intent = new Intent(MainActivity.this, WisdomService.class);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Settings.canDrawOverlays(this)){
            startWisdom();
        }else {
            ToastUtil.show(this,"未开启悬浮窗权限");
        }
    }


    public void doctorClick(View view) {
        Intent intent = new Intent(this, DoctorQueryActivity.class);
        startActivity(intent);
    }
}
