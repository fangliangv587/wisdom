package com.xz.cenco.wisdom;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cenco.lib.common.ScreenUtil;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.log.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_MEDIA_PROJECTION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        hideWisdom();
        keepalive();
    }

    private void keepalive() {
        /*
        *  http://blog.csdn.net/liubinwyzbt/article/details/79079134
        * */
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder jb = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            jb.setPeriodic(15 * 60 * 1000, 5 * 60 *1000);
        }else {
            jb .setPeriodic(2*1000);
        }

        JobInfo jobInfo = jb .build();
        int schedule = jobScheduler.schedule(jobInfo);
        if (schedule == JobScheduler.RESULT_SUCCESS){
            LogUtils.i("保活计划成功");
        }else{
            LogUtils.e("保活计划失败");
        }
    }

    private void startCapture() {

        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager)
                getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
        App.mediaProjectionManager = mMediaProjectionManager;
        App.screenDensity = ScreenUtil.getScreenDensity(this);
    }

    private void initView() {


    }

    private void protect() {

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
    public void showClick(View view){
        if (checkPermission()) return;
        Intent intent = new Intent(MainActivity.this, FxService2.class);
        startService(intent);
        finish();
        protect();
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

    private void hideWisdom(){
        Intent intent = new Intent(MainActivity.this, FxService2.class);
        stopService(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showClick(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_MEDIA_PROJECTION && resultCode == Activity.RESULT_OK ){
            App.captureResultCode = resultCode;
            App.captureIntent = data;
        }
    }
}
