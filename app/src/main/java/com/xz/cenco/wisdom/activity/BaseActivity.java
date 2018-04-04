package com.xz.cenco.wisdom.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

/**
 * Created by Administrator on 2018/2/25.
 */

public class BaseActivity extends Activity {
    protected Context mContext;
    protected String TAG = this.getClass().getName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
    }


    protected App getApp(){
        return (App) getApplication();
    }
}
