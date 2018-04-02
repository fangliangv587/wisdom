package com.xz.cenco.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.ToastUtil;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.util.DataUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by Administrator on 2018/3/29.
 */

public class DoctorQueryActivity extends BaseActivity {

    EditText curAgeEt;
    EditText endAgeEt;
    CheckBox hasDoctorCB;
    Button calcBtn;
    TextView personFeeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_query);

         curAgeEt = findViewById(R.id.curAgeEt);
         endAgeEt = findViewById(R.id.endAgeEt);
         hasDoctorCB = findViewById(R.id.hasDoctorCB);
        calcBtn = findViewById(R.id.calcBtn);
        personFeeTv = findViewById(R.id.personFeeTv);

        showPersonFee("2018");

    }

    public void yearClick(View view){
        {
            Date date = new Date();
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
                    showPersonFee(year);
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
    }

    private void showPersonFee(String year) {
        int y = Integer.parseInt(year);
        int total = DataHelper.getYearFeeInFamily(y);
        String string = DataHelper.getPersonString();
        personFeeTv.setText(string+"\r\n在"+year+"年总计缴费"+total+"元");
    }

    public void sureClick(View view){
        int curAge = Integer.parseInt(curAgeEt.getText().toString());
        int endAge = Integer.parseInt(endAgeEt.getText().toString());
        if (endAge<curAge){
            ToastUtil.show(this,"越活越小？");
            return;
        }
        int totalFee = DataHelper.getTotalFee(curAge, endAge, hasDoctorCB.isChecked());
        calcBtn.setText("计算"+totalFee);
    }

    public List<String> convert(List<Person> personList){
        List<String> list = new ArrayList<>();
        for (Person p : personList){
            list.add(p.getName()+"  "+p.getCurAge()+"   "+ p.getMaxAge() + "  "+(p.isDoctor()?"有医保":"无医保"));
        }
        return list;
    }
}
