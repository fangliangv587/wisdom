package com.xz.cenco.wisdom.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/2/25.
 */

public class BaseActivity extends Activity {
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }


    protected App getApp(){
        return (App) getApplication();
    }
}
