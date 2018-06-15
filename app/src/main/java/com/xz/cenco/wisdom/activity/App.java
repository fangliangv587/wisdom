package com.xz.cenco.wisdom.activity;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.log.Level;
import com.cenco.lib.common.log.LogUtils;
import com.facebook.stetho.Stetho;
import com.tencent.bugly.crashreport.CrashReport;
import com.xz.cenco.assits.DaoMaster;
import com.xz.cenco.assits.DaoSession;
import com.xz.cenco.wisdom.util.C;

import org.greenrobot.greendao.database.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/25.
 */

public class App extends Application {


    public static boolean isTimer;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initBugly();
        initLog();
        initAlive();
        initStetho();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initAlive() {

    }

    private void initLog() {
        LogUtils.init("appwisdom", Level.WARN, C.file.log_path);
        HttpUtil.init(this);
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
