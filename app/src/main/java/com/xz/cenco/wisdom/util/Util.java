package com.xz.cenco.wisdom.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.cenco.lib.common.LogUtil;

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
        LogUtil.w("目标色：red:"+red+",green:"+green+",blue:"+blue);
        int red1 = (red + 255/2)%255;
        int green1 = (green + 255/2)%255;
        int blue1 = (blue + 255/2)%255;
        LogUtil.w("相反色：red:"+red1+",green:"+green1+",blue:"+blue1);
        return Color.argb(255,red1,green1,blue1);
    }
}
