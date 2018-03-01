package com.xz.cenco.wisdom;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.cenco.lib.common.LogUtil;

import java.util.List;

public class MyJobService extends JobService {

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (!isServiceRunning(MyJobService.this,FxService2.class.getName())){
                LogUtil.w("启动服务");
                Intent intent = new Intent(MyJobService.this, FxService2.class);
                startService(intent);
            }
            JobParameters param = (JobParameters) msg.obj;
            jobFinished(param, true);
            return true;
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("MyJobService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("MyJobService onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        LogUtil.i("onStartJob");
        Message m = Message.obtain();
        m.obj = params;
        handler.sendMessage(m);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogUtil.w("onStopJob");
        handler.removeCallbacksAndMessages(null);
        return false;
    }

    /**
     * 方法描述：判断某一Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service的全路径： 包名 + service的类名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}