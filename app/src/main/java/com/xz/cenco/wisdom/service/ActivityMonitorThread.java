package com.xz.cenco.wisdom.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.cenco.lib.common.log.LogUtils;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Administrator on 2018/3/26.
 */

public class ActivityMonitorThread extends Thread {

    private Context context;
    private boolean alive;

    public ActivityMonitorThread(Context context) {
        this.context = context;
        alive = false;
    }

    @Override
    public void run() {
        super.run();

       if (alive){
           return;
       }
       alive = true;

       while (alive){
           try {
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

           getCurrentTask();
       }



    }

    public void termination (){
        alive = false;
    }


    public void getCurrentTask(){
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> cn = am.getRunningTasks(1);
            ActivityManager.RunningTaskInfo taskInfo=cn.get(0);
            ComponentName name=taskInfo.topActivity;
            String className = name.getClassName();
            LogUtils.d("activity monitor:"+className);
            Activity foregroundActivity = (Activity) (Class.forName(className).newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
            LogUtils.e("InstantiationException:"+e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtils.e("IllegalAccessException:"+e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("ClassNotFoundException:"+e.getMessage());
        }
    }
}
