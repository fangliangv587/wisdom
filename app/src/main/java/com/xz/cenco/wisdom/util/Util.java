package com.xz.cenco.wisdom.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.service.DetectionService;

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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        }else{
            return WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
//        }
    }


    /**
     * 方法6：使用 Android AccessibilityService 探测窗口变化，跟据系统回传的参数获取 前台对象 的包名与类名
     *
     * @param packageName 需要检查是否位于栈顶的App的包名
     */
    public static boolean isForegroundPkgViaDetectionService(String packageName) {
        return packageName.equals(DetectionService.foregroundPackageName);
    }

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
}
