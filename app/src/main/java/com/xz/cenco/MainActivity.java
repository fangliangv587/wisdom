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
import com.xz.cenco.doctor.DoctorQueryActivity;
import com.xz.cenco.wisdom.activity.SettingActivity;
import com.xz.cenco.wisdom.activity.TypeActivity;
import com.xz.cenco.wisdom.service.MyJobService;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.service.WisdomService;
import com.xz.cenco.wisdom.util.Util;

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

    public void getActivityThread(){
//        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE) ;
//        ActivityManager.getService();
//        Context c = null;
//        c.getApplicationContext();
//        ActivityStack s;
//        IApplicationThread ===> ActivityRecord .app.thread;
//        TaskRecord t;
    }

    public  Activity getActivity() {
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");

            Method[] methods = activityThreadClass.getMethods();
            Method[] declaredMethods = activityThreadClass.getDeclaredMethods();

            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field[] fields = activityRecordClass.getDeclaredFields();
                LogUtils.d("---"+activityRecordClass.getName());
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                Field activityField = activityRecordClass.getDeclaredField("activity");
                Field windowField = activityRecordClass.getDeclaredField("window");
                LogUtils.d("activity ---"+activityField.getName());

                activityField.setAccessible(true);
                windowField.setAccessible(true);
                Object o = activityField.get(activityRecord);
                Window w = (Window) windowField.get(activityRecord);

                String name = o.getClass().getName();
                String name1 = o.getClass().getPackage().getName();
                LogUtils.d("activity ---"+activityField.getName());

                int statusBarColor = w.getStatusBarColor();
                String sss = Util.getColor(statusBarColor);
                LogUtils.i("getWindow statusBarColor color  ="+sss);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return null;
    }


    public void getStatusBarColor(String className){


        int statusBarColor = this.getWindow().getStatusBarColor();
        String sss = Util.getColor(statusBarColor);
        LogUtils.i("getWindow statusBarColor color  ="+sss);

        try {
            LogUtils.d(className);




            AppCompatActivity instance = (AppCompatActivity) Class.forName(className).newInstance();
            Window window = instance.getWindow();
            LogUtils.w("window:"+window);
//            int statusBarColor1 = window.getStatusBarColor();
//            String sss1 = Util.getColor(statusBarColor1);
//            LogUtils.w("getWindow statusBarColor color  ="+sss1);


//            Object value = ReflectionUtils.getFieldValue(this, "mTaskDescription");
//            LogUtils.i("ReflectionUtils getFieldValue ="+value);
//            int mColorBackground = (int) ReflectionUtils.getFieldValue(value, "mColorBackground");
//            int colorPrimary = (int) ReflectionUtils.getFieldValue(value, "mColorPrimary");
//            LogUtils.i("ReflectionUtils mColorBackground ="+mColorBackground);
//            LogUtils.i("ReflectionUtils colorPrimary ="+colorPrimary);
//            String color1 = Util.getColor(mColorBackground);
//            String color2 = Util.getColor(colorPrimary);
//            LogUtils.i("ReflectionUtils mColorBackground color  ="+color1);
//            LogUtils.i("ReflectionUtils colorPrimary color  ="+color2);


        }catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","NoSuchFieldException:"+e.getMessage());
        }
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
            // 执行辅助功能服务相关操作
        }
    }


    public void test(){
        List<ActivityManager.RunningAppProcessInfo> runningAppsInfo = new ArrayList<>();
        PackageManager pm = getPackageManager();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        am.getAppTasks();
        List<ActivityManager.RunningServiceInfo> runningServices = am
                .getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo service : runningServices) {

            String pkgName = service.process.split(":")[0];

            Activity activity=null;
            activity.setTaskDescription(null);


        }
    }


    private void setupTransparentSystemBarsForLmp() {
        // TODO(sansid): use the APIs directly when compiling against L sdk.
        // Currently we use reflection to access the flags and the API to set the transparency
        // on the System bars.
            String TAG = "systemBar";
            try {
                getWindow().getAttributes().systemUiVisibility |=
                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                Field drawsSysBackgroundsField = WindowManager.LayoutParams.class.getField(
                        "FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
                getWindow().addFlags(drawsSysBackgroundsField.getInt(null));

                Method setStatusBarColorMethod =
                        Window.class.getDeclaredMethod("setStatusBarColor", int.class);
                Method setNavigationBarColorMethod =
                        Window.class.getDeclaredMethod("setNavigationBarColor", int.class);
                setStatusBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
                setNavigationBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
            } catch (NoSuchFieldException e) {
                Log.w(TAG, "NoSuchFieldException while setting up transparent bars");
            } catch (NoSuchMethodException ex) {
                Log.w(TAG, "NoSuchMethodException while setting up transparent bars");
            } catch (IllegalAccessException e) {
                Log.w(TAG, "IllegalAccessException while setting up transparent bars");
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "IllegalArgumentException while setting up transparent bars");
            } catch (InvocationTargetException e) {
                Log.w(TAG, "InvocationTargetException while setting up transparent bars");
            } finally {}

    }

    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
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




    public void contentClick(View view){
        getActivity();
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
        startWisdom();
        finish();
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
