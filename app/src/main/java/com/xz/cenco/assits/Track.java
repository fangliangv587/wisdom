package com.xz.cenco.assits;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.xz.cenco.wisdom.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class Track {
    private String packageName;
    private int seconds;
    private Drawable drawable;
    private List<Record> records;

    public Track(String packageName, int seconds, List<Record> records) {
        this.packageName = packageName;
        this.seconds = seconds;
        this.records = records;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    public String getAppName(Context context){
        if (context == null){
            return "";
        }
        return Util.getProgramNameByPackageName(context,this.getPackageName());
    }

    public String getStanderTimeString(){
        return Util.getStayTimeString(seconds);
    }
}
