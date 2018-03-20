package com.xz.cenco.wisdom.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.ToastUtil;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;

import java.util.Date;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class WisdomAddActivity extends BaseActivity {


    WisdomDao wisdomDao;
    EditText wisdomNameEt;
    EditText commentEt;
    Button stopDate;
    Button startDate;
    String typeName;
    String startTime;
    String stopTime;
    long typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisdom_add);
        wisdomNameEt = findViewById(R.id.wisdomName);
        commentEt = findViewById(R.id.comment);
        startDate = findViewById(R.id.startDate);
        stopDate = findViewById(R.id.stopDate);
        wisdomDao = getApp().getDaoSession().getWisdomDao();
        typeName = getIntent().getStringExtra("typeName");
        typeId = getIntent().getLongExtra("typeId", -1);
    }


    public void onYearMonthDayPicker(final int tag) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2050, 1, 11);
        picker.setRangeStart(2000, 8, 29);
        picker.setSelectedItem(2018, 3, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String str = year + "-" + month + "-" + day;
                if (tag == 0){
                    startTime = str;
                    startDate.setText(str);
                }else {
                    stopTime = str;
                    stopDate.setText(str);
                }
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public void addClick(View view){
        String text = wisdomNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(text)){
            ToastUtil.show(this,"内容不能为空");
            return;
        }

        if(TextUtils.isEmpty(startTime)){
            ToastUtil.show(this,"开始时间不能为空");
            return;
        }
        if(TextUtils.isEmpty(stopTime)){
            ToastUtil.show(this,"结束时间不能为空");
            return;
        }

        Wisdom wisdom = new Wisdom();
        wisdom.setText(text);
        wisdom.setType(typeId);
        wisdom.setDate(new Date());
        wisdom.setStartDate(DateUtil.getDate(startTime,DateUtil.FORMAT_YMD));
        wisdom.setStopDate(DateUtil.getDate(stopTime,DateUtil.FORMAT_YMD));
        wisdom.setComment(commentEt.getText().toString().trim());
        wisdomDao.insert(wisdom);

        setResult(RESULT_OK);
        finish();
    }

    public void onStartDateClick(View view) {
        onYearMonthDayPicker(0);
    }

    public void onStopDateClick(View view) {
        onYearMonthDayPicker(1);
    }
}
