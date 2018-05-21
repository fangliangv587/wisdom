package com.xz.cenco.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.xz.cenco.test.async.AsyncActivity;
import com.xz.cenco.test.baidu_police_vertify.PoliceVertifyActivity;
import com.xz.cenco.weed.coohua.CoohuaActivity;
import com.xz.cenco.wisdom.BuildConfig;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;

/**
 * Created by Administrator on 2018/4/13.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView tv = findViewById(R.id.tv);
        tv.setText(BuildConfig.adUrl);
    }

    public void baiduPoliceVertifyClick(View view) {
        Intent intent = new Intent(this, PoliceVertifyActivity.class);
        startActivity(intent);
    }

    public void asynClick(View view) {
        Intent intent = new Intent(this, AsyncActivity.class);
        startActivity(intent);
    }

    public void kuhuaClick(View view) {
        Intent intent = new Intent(this, CoohuaActivity.class);
        startActivity(intent);
    }
}
