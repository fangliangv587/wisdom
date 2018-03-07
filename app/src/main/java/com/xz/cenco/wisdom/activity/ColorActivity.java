package com.xz.cenco.wisdom.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.SeekBar;

import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.util.C;
import com.xz.cenco.wisdom.util.SPUtil;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ColorActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar redSeekbar;
    private SeekBar greenSeekbar;
    private SeekBar blueSeekbar;
    View colorView;
    int red;
    int green;
    int blue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);


        initView();
    }

    private void initView() {

        redSeekbar = findViewById(R.id.redSeekbar);
        greenSeekbar = findViewById(R.id.greenSeekbar);
        blueSeekbar = findViewById(R.id.blueSeekbar);
        redSeekbar.setOnSeekBarChangeListener(this);
        greenSeekbar.setOnSeekBarChangeListener(this);
        blueSeekbar.setOnSeekBarChangeListener(this);

        colorView = findViewById(R.id.colorView);

        int color = getIntent().getIntExtra(C.extra.color, 0);
        colorView.setBackgroundColor(color);

         red = Color.red(color);
         green = Color.green(color);
         blue = Color.blue(color);
        redSeekbar.setProgress(red);
        greenSeekbar.setProgress(green);
        blueSeekbar.setProgress(blue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) return;
        if (seekBar == redSeekbar){
            red = progress;
        }
        if (seekBar == blueSeekbar){
            blue = progress;
        }
        if (seekBar == greenSeekbar){
            green = progress;
        }

        int rgb = Color.rgb(red, green, blue);
        colorView.setBackgroundColor(rgb);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void sureClick(View view){
        int rgb = Color.rgb(red, green, blue);
        Intent intent = new Intent();
        intent.putExtra(C.extra.color,rgb);
        setResult(RESULT_OK,intent);
        finish();
    }
}
