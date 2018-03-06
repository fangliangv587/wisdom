package com.xz.cenco.wisdom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/2/25.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected App getApp(){
        return (App) getApplication();
    }
}
