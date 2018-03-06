package com.xz.cenco.wisdom.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.xz.cenco.wisdom.R;

/**
 * Created by Administrator on 2018/3/6.
 */

public class CancelNoticeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        startForeground(WisdomService.NOTICE_ID,builder.build());
        // 开启一条线程，去移除DaemonService弹出的通知
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 延迟1s
                SystemClock.sleep(1000);
                // 取消CancelNoticeService的前台
                stopForeground(true);
                // 移除DaemonService弹出的通知
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(WisdomService.NOTICE_ID);
                // 任务完成，终止自己
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
