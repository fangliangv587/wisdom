package com.xz.cenco.wisdom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        hideWisdom();

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
}
