package com.xz.cenco.weed;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.cenco.lib.common.ImageUtil;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.xz.cenco.wisdom.R;

/**
 * Created by Administrator on 2018/4/25.
 */

public class TumblerActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler);

        ImageView iv =(ImageView) findViewById(R.id.image);
        String url = "http://www.ybol.vip/CheckCode?flag=3";
        ImageUtil.loadImage(this,url,iv);
    }


    public void init(){

    }
}
