package com.xz.cenco.wisdom.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.cenco.lib.common.log.LogUtils;

import java.lang.reflect.Field;

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

            LogUtils.d("onAccessibilityEvent","foregroundPackageName="+foregroundPackageName);

            /*
             * 基于以下还可以做很多事情，比如判断当前界面是否是 Activity，是否系统应用等，
             * 与主题无关就不再展开。
             */
            ComponentName cName = new ComponentName(event.getPackageName().toString(),
                    event.getClassName().toString());
            String className = cName.getClassName();
            LogUtils.i("onAccessibilityEvent","foregroundClassName="+className);

            getStatusBarColor(className);

        }
    }

    public void getStatusBarColor(String className){
        try {
            Activity foregroundActivity = (Activity) (Class.forName(className).newInstance());
            LogUtils.w("onAccessibilityEvent","activity hashcode"+foregroundActivity.hashCode());
            Field[] fields = Activity.class.getDeclaredFields();
            Field field = Activity.class.getDeclaredField("mTitleColor");
            if (!field.isAccessible()){
                field.setAccessible(true);
            }
            for (Field f:fields){
                LogUtils.d(""+f.getName());
                if (f.getName().equals("mTaskDescription")){
                    LogUtils.w(""+f.getName());
                }
            }
            int o = (int) field.get(foregroundActivity);
//            Field field1 = Activity.class.getDeclaredField("mTaskDescription");
            int b =o+1;
//            LogUtils.w("onAccessibilityEvent","mTaskDescription hashcode"+field.hashCode());
//            field.get(user)
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