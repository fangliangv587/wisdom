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
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class WisdomAddActivity extends BaseActivity {


    WisdomDao wisdomDao;
    EditText wisdomNameEt;
    EditText commentEt;
    Button stopDateBtn;
    Button startDateBtn;
    Button startPeriodBtn;
    Button stopPeriodBtn;
    String typeName;
    String startDate;
    String stopDate;
    String startTime;
    String stopTime;
    long typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisdom_add);

        wisdomDao = getApp().getDaoSession().getWisdomDao();
        typeName = getIntent().getStringExtra("typeName");
        typeId = getIntent().getLongExtra("typeId", -1);

        initView();
    }

    private void initView() {
        wisdomNameEt = (EditText)findViewById(R.id.wisdomName);
        commentEt =(EditText) findViewById(R.id.comment);
        startDateBtn =(Button) findViewById(R.id.startDate);
        stopDateBtn =(Button) findViewById(R.id.stopDate);
        startPeriodBtn =(Button) findViewById(R.id.startPeriod);
        stopPeriodBtn =(Button) findViewById(R.id.stopPeriod);

        startDate = getDateString("2000","1","1");
        stopDate = getDateString("2100","1","1");
        startTime = getTimeString("0","0");
        stopTime = getTimeString("23","59");
        startDateBtn.setText(startDate);
        stopDateBtn.setText(stopDate);
        startPeriodBtn.setText(startTime);
        stopPeriodBtn.setText(stopTime);

    }

    public String getDateString(String year,String month,String day){
        String str = year + "-" + month + "-" + day;
        return str;
    }
    public String getTimeString(String hour,String minute){
        String str = hour + ":" + minute ;
        return str;
    }

    public void onYearMonthDayPicker(final int tag,Date date) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2200, 1, 11);
        picker.setRangeStart(1900, 8, 29);
        picker.setSelectedItem(DateUtil.getYear(date), DateUtil.getMonth(date), DateUtil.getDay(date));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String str =getDateString(year,month,day);
                if (tag == 0){
                    startDate = str;
                    startDateBtn.setText(str);
                }else {
                    stopDate = str;
                    stopDateBtn.setText(str);
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


    public void onTimePicker(final int tag,Date date) {
        final TimePicker picker = new TimePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));

        picker.setRangeEnd(23,59);
        picker.setRangeStart(0,0);
        picker.setSelectedItem(DateUtil.getHour(date), DateUtil.getMinute(date));
        picker.setResetWhileWheel(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                if (tag == 0){
                    startTime = getTimeString(hour,minute);
                    startPeriodBtn.setText(startTime);
                }else {
                    stopTime = getTimeString(hour,minute);
                    stopPeriodBtn.setText(stopTime);
                }
            }
        });
        picker.setOnWheelListener(new TimePicker.OnWheelListener() {
            @Override
            public void onHourWheeled(int index, String hour) {
                picker.setTitleText(hour+":"+picker.getSelectedMinute());
            }

            @Override
            public void onMinuteWheeled(int index, String minute) {
                picker.setTitleText(picker.getSelectedHour()+":"+minute);
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

        if(TextUtils.isEmpty(startDate)){
            ToastUtil.show(this,"开始时间不能为空");
            return;
        }
        if(TextUtils.isEmpty(stopDate)){
            ToastUtil.show(this,"结束时间不能为空");
            return;
        }

        Wisdom wisdom = new Wisdom();
        wisdom.setText(text);
        wisdom.setType(typeId);
        wisdom.setDate(new Date());
        wisdom.setStartDate(startDate);
        wisdom.setStopDate(stopDate);
        wisdom.setStartPeriodTime(startTime);
        wisdom.setStopPeriodTime(stopTime);
        wisdom.setComment(commentEt.getText().toString().trim());
        wisdomDao.insert(wisdom);

        setResult(RESULT_OK);
        finish();
    }

    public void onStartDateClick(View view) {
        Date date = DateUtil.getDate(startDate, DateUtil.FORMAT_YMD);
        onYearMonthDayPicker(0,date);

    }

    public void onStopDateClick(View view) {
        Date date = DateUtil.getDate(stopDate, DateUtil.FORMAT_YMD);
        onYearMonthDayPicker(1,date);
    }

    public void onStartPeriodClick(View view) {
        Date date = DateUtil.getDate(startTime, DateUtil.FORMAT_HM);
        onTimePicker(0,date);
    }

    public void onStopPeriodClick(View view) {
        Date date = DateUtil.getDate(stopTime, DateUtil.FORMAT_HM);
        onTimePicker(1,date);

    }
}
