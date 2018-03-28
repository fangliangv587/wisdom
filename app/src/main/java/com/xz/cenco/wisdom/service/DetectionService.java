package com.xz.cenco.wisdom.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;

import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.util.Util;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by shawn
 * Data: 2/3/2016
 * Blog: effmx.com
 */
public class DetectionService extends AccessibilityService {

    final static String TAG = "DetectionService";

    public static String foregroundPackageName;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0; // 根据需要返回不同的语义值
    }


    /**
     * 重载辅助功能事件回调函数，对窗口状态变化事件进行处理
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            /*
             * 如果 与 DetectionService 相同进程，直接比较 foregroundPackageName 的值即可
             * 如果在不同进程，可以利用 Intent 或 bind service 进行通信
             */
            foregroundPackageName = event.getPackageName().toString();

//            LogUtils.d("onAccessibilityEvent","foregroundPackageName="+foregroundPackageName);

            /*
             * 基于以下还可以做很多事情，比如判断当前界面是否是 Activity，是否系统应用等，
             * 与主题无关就不再展开。
             */
            ComponentName cName = new ComponentName(event.getPackageName().toString(),
                    event.getClassName().toString());
            String className = cName.getClassName();
            LogUtils.i("onAccessibilityEvent","foregroundClassName="+className);
//            LogUtils.d(event.toString());


            getActivity();


        }
    }

    public  Activity getActivity() {
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
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
                LogUtils.d("activity ---"+name);

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
        try {
            Activity foregroundActivity = (Activity) (Class.forName(className).newInstance());
            LogUtils.w("onAccessibilityEvent","activity hashcode"+foregroundActivity.hashCode());
            Field[] fields = Activity.class.getDeclaredFields();

            for (Field f:fields){
                if (f.getName().equals("mTaskDescription")){
                    LogUtils.w(""+f.getName());
                }
            }

            Field field1 = Activity.class.getDeclaredField("mTaskDescription");
            if (!field1.isAccessible()){
                field1.setAccessible(true);
            }
            ActivityManager.TaskDescription td = (ActivityManager.TaskDescription) field1.get(foregroundActivity);
            LogUtils.w("onAccessibilityEvent","mTaskDescription hashcode"+td.hashCode());

            LogUtils.d("************************************************");


            Field f1 = ActivityManager.TaskDescription.class.getDeclaredField("mIcon");
            Field f2 = ActivityManager.TaskDescription.class.getDeclaredField("mLabel");
            Field f3 = ActivityManager.TaskDescription.class.getDeclaredField("mColorPrimary");
            Field f4 = ActivityManager.TaskDescription.class.getDeclaredField("mColorBackground");

            f1.setAccessible(true);
            f2.setAccessible(true);
            f3.setAccessible(true);
            f4.setAccessible(true);

            Bitmap bitmap = (Bitmap) f1.get(td);
            String mLabel = (String) f2.get(td);
            int mColorPrimary = (int) f3.get(td);
            int mColorBackground = (int) f4.get(td);

            LogUtils.w("bitmap="+bitmap+",mLabel="+mLabel+",mColorPrimary="+mColorPrimary+",mColorBackground="+mColorBackground);

        } catch (InstantiationException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","InstantiationException:"+e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","IllegalAccessException:"+e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","ClassNotFoundException:"+e.getMessage());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","NoSuchFieldException:"+e.getMessage());
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected  void onServiceConnected() {
        super.onServiceConnected();
    }
}