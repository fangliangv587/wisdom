package com.xz.cenco.wisdom.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.WindowManager;

import com.cenco.lib.common.log.LogUtils;

/**
 * Created by Administrator on 2018/2/27.
 */

public class Util {
    public static  int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getInverseColor(int color){
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        LogUtils.w("目标色：red:"+red+",green:"+green+",blue:"+blue);
        int red1 = (red + 255/2)%255;
        int green1 = (green + 255/2)%255;
        int blue1 = (blue + 255/2)%255;
        LogUtils.w("相反色：red:"+red1+",green:"+green1+",blue:"+blue1);
        return Color.argb(255,red1,green1,blue1);
    }

    /**
     * 弹出框类型
     * @return
     */
    public static int getWindowType(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            return WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }
    }


    /**
     * 方法6：使用 Android AccessibilityService 探测窗口变化，跟据系统回传的参数获取 前台对象 的包名与类名
     *
     */
//    public static boolean isForegroundPkgViaDetectionService(String packageName) {
//        return packageName.equals(DetectionService.foregroundPackageName);
//    }

    public static String getColor(int color){
        String red = Integer.toHexString(Color.red(color));
        if (red.length() == 1){
            red = "0"+ red;
        }
        String green = Integer.toHexString(Color.green(color));
        if (green.length() == 1){
            green = "0"+ green;
        }
        String blue = Integer.toHexString(Color.blue(color));
        if (blue.length() == 1){
            blue = "0"+ blue;
        }

        return "#"+red+green+blue;
    }


    public static String getProgramNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = null;
        try {
            name = pm.getApplicationLabel(
                    pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }


    public static String getStayTimeString(int seconds){
        if (seconds<60){
            return seconds+"秒";
        }

        if (seconds<60*60){
            int minute = seconds/60;
            int second = seconds%60;
            return minute+"分"+second+"秒";
        }

        int hour = seconds/(60*60);
        int sec = seconds % (60 * 60);
        int minute = sec/60;
        int second = sec%60;
        return hour+"时"+minute+"分"+second+"秒";
    }

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

}
