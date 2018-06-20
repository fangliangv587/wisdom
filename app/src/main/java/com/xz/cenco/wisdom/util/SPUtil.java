/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.xz.cenco.wisdom.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.WindowManager.LayoutParams;

import com.cenco.lib.common.ScreenUtil;

/**
 * Created by Administrator on 2018/2/23.
 */

public class SPUtil {

    private static final String spname = "setting";

    private static final String alpha = "alpha";
    private static final String mode = "mode";
    private static final String color = "color";
    private static final String bgcolor = "bgcolor";
    private static final String hasBgcolor = "hasBgcolor";
    private static final String size = "size";
    private static final String positionX = "positionX";
    private static final String positionY = "positionY";
    private static final String interval = "internval";
    private static final String oritation = "oritation";
    private static final String autocolor = "autocolor";
    private static final String startX = "startX";
    private static final String stopX = "stopX";
    private static final String containSystemProcess = "containSystemProcess";

    private static final int defaultMode = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE | LayoutParams.FLAG_LAYOUT_IN_SCREEN;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(spname, 0);
    }

    public static void setContainSystemProcess(Context context,boolean alphaValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(containSystemProcess,alphaValue).commit();
    }

    public static boolean getContainSystemProcess(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        boolean alphaValue = sp.getBoolean(containSystemProcess,false);
        return alphaValue;
    }
    public static void setAutocolor(Context context,boolean alphaValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(autocolor,alphaValue).commit();
    }

    public static boolean getAutocolor(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        boolean alphaValue = sp.getBoolean(autocolor,false);
        return alphaValue;
    }

    public static void setOritation(Context context,boolean alphaValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(oritation,alphaValue).commit();
    }

    public static boolean getOritation(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        boolean alphaValue = sp.getBoolean(oritation,true);
        return alphaValue;
    }

    public static void setAlpha(Context context,float alphaValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putFloat(alpha,alphaValue).commit();
    }

    public static float getAlpha(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        float alphaValue = sp.getFloat(alpha,1);
        return alphaValue;
    }
    public static void setMode(Context context,int modeValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(mode,modeValue).commit();
    }

    public static int getMode(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int modeValue = sp.getInt(mode,defaultMode);
        return modeValue;
    }

    public static void setInterval(Context context,int colorValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(interval,colorValue).commit();
    }

    public static int getInterval(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int colorValue = sp.getInt(interval, 5);
        return colorValue;
    }
    public static void setColor(Context context,int colorValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(color,colorValue).commit();
    }

    public static int getColor(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int colorValue = sp.getInt(color, Color.WHITE);
        return colorValue;
    }
    public static void setBgColor(Context context,int colorValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(bgcolor,colorValue).commit();
    }

    public static int getBgColor(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int colorValue = sp.getInt(bgcolor, Color.GREEN);
        return colorValue;
    }

    public static void setHasBgColor(Context context,boolean hasBg){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(hasBgcolor,hasBg).commit();
    }

    public static boolean hasBgColor(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        boolean hasBg = sp.getBoolean(hasBgcolor,false);
        return hasBg;
    }

    public static void setSize(Context context,int sizeValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(size,sizeValue).commit();
    }

    public static int getSize(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int sizeValue = sp.getInt(size,12);
        return sizeValue;
    }
    public static void setPositionX(Context context,int positionXValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(positionX,positionXValue).commit();
    }

    public static int getPositionX(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int positionXValue = sp.getInt(positionX,0);
        return positionXValue;
    }
    public static void setPositionY(Context context,int positionYValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(positionY,positionYValue).commit();
    }

    public static int getPositionY(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int positionYValue = sp.getInt(positionY,0);
        return positionYValue;
    }

    public static void setStartX(Context context,int positionXValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(startX,positionXValue).commit();
    }

    public static int getStartX(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int positionXValue = sp.getInt(startX,158);
        return positionXValue;
    }
    public static void setStopX(Context context,int positionXValue){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(stopX,positionXValue).commit();
    }

    public static int getStopX(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int positionXValue = sp.getInt(stopX, 662);
        return positionXValue;
    }


}
