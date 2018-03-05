package com.xz.cenco.wisdom;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;

import com.cenco.lib.common.log.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xz.cenco.wisdom.entity.DaoMaster;
import com.xz.cenco.wisdom.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/25.
 */

public class App extends Application {


    private DaoSession daoSession;
    public static int screenDensity;
    public static int captureResultCode;
    public static Intent captureIntent;
    public static MediaProjectionManager mediaProjectionManager;
    ;

    @Override
    public void onCreate() {
        super.onCreate();

        initBugly();
        initLog();
    }

    private void initLog() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"wisdom"+File.separator+"log";
        LogUtils.init(this,path);


    }

    private void initBugly() {

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), "c6e6255e03", true);
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "wisdom-db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
        }

        return daoSession;

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
