package cenco.xz.fangliang.wisdom;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.log.Level;
import com.cenco.lib.common.log.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xz.cenco.wisdom.BuildConfig;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cenco.xz.fangliang.wisdom.core.C;
import cenco.xz.fangliang.wisdom.weed.txapp.DBHelper;

/**
 * Created by Administrator on 2018/2/25.
 */

public class App extends Application {


    public static boolean isTimer;
    public static DBHelper helper;

    public static String bdOrcToken;

    @Override
    public void onCreate() {
        super.onCreate();

        initBugly();
        initCommonLib();
        //1
        //2
        //a
        //b
        //c
    }



    private void initCommonLib() {
        int level = Level.DEBUG;
        LogUtils.init("c", level, C.file.log_path);
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
