package com.xz.cenco.wisdom.service;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;


import com.cenco.lib.common.log.LogUtils;

import java.util.List;

public class MyJobService extends JobService {

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (!isServiceRunning(MyJobService.this, WisdomService.class.getName())) {
                LogUtils.w("启动服务");
                Intent intent = new Intent(MyJobService.this, WisdomService.class);
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
        LogUtils.i("MyJobService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("MyJobService onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        LogUtils.i("onStartJob");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LogUtils.d("JobSchedulerService", "7.0 handleMessage task running");

            if (!isServiceRunning(MyJobService.this, WisdomService.class.getName())) {
                LogUtils.w("启动服务");
                Intent intent = new Intent(MyJobService.this, WisdomService.class);
                startService(intent);
            }
            //创建一个新的JobScheduler任务
            scheduleRefresh();
            jobFinished(params, false);
            LogUtils.d("JobSchedulerService", "7.0 handleMessage task end~~" + System.currentTimeMillis());
            return true;
        } else {
            Message m = Message.obtain();
            m.obj = params;
            handler.sendMessage(m);
            return true;
        }


    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogUtils.w("onStopJob");
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


    private void scheduleRefresh() {
        JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        //jobId可根据实际情况设定
        JobInfo.Builder mJobBuilder =
                new JobInfo.Builder(0,
                        new ComponentName(getPackageName(),
                                MyJobService.class.getName()));

        mJobBuilder.setMinimumLatency(2 * 60 * 1000).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobInfo build = mJobBuilder.build();
        int schedule = mJobScheduler.schedule(build);
        if (schedule == JobScheduler.RESULT_SUCCESS) {
            LogUtils.d("JobSchedulerService", "7.0 schedule the service SUCCESS!");
        } else {
            LogUtils.d("JobSchedulerService", "7.0 Unable to schedule the service FAILURE!");
        }
    }
}