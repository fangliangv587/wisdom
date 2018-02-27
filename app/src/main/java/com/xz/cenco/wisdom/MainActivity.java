package com.xz.cenco.wisdom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.TextView;

import com.cenco.lib.common.LogUtil;
import com.cenco.lib.common.ScreenUtil;
import com.xz.cenco.wisdom.util.Util;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_MEDIA_PROJECTION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        hideWisdom();
        startCapture();

    }

    private void startCapture() {

        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager)
                getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(
                mMediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
        App.mediaProjectionManager = mMediaProjectionManager;
        App.screenDensity = ScreenUtil.getScreenDensity(this);
    }

    private void initView() {
//        TextView tv = findViewById(R.id.float_id);
//        tv.setSelected(true);
    }

    private void protect() {

    }

    public void contentClick(View view){
        Intent intent = new Intent(this, ContentTypeActivity.class);
        startActivity(intent);
    }
    public void settingClick(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    public void showClick(View view){
        Intent intent = new Intent(MainActivity.this, FxService2.class);
        startService(intent);
        finish();
        protect();
    }

    private void hideWisdom(){
        Intent intent = new Intent(MainActivity.this, FxService2.class);
        stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_MEDIA_PROJECTION && resultCode == Activity.RESULT_OK ){
            App.captureResultCode = resultCode;
            App.captureIntent = data;
        }
    }
}
