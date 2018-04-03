package com.xz.cenco.assits;

import android.os.Bundle;
import android.widget.TextView;

import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.util.C;
import com.xz.cenco.wisdom.util.Util;

import java.util.List;

public class AppRecordActivity extends BaseActivity {

    private RecordDao recordDao;
    private String date;
    private TextView infoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_record);
        date = getIntent().getStringExtra(C.extra.date);
        recordDao = getApp().getDaoSession().getRecordDao();
        infoTv = findViewById(R.id.infoTv);
        String appRecords = getAppRecords();
        infoTv.setText(appRecords);
    }

    public String getAppRecords(){
        if (date == null){
            return null;
        }

        List<Record> list = recordDao.queryBuilder().where(RecordDao.Properties.Date.eq(date)).list();
        StringBuffer sb = new StringBuffer();
        for (Record r:list){
            sb.append(getFormatString(r)).append("\r\n");
        }
        return sb.toString();
    }

    public String getFormatString(Record r){
        int stayTime = r.getStayTime();
        return r.getId()+": "+r.getDate()+" "+ r.getInTime()+"进入，"+r.getOutTime()+"离开，驻留时间"+r.getStayTimeString(stayTime)+",应用:"+ Util.getProgramNameByPackageName(this,r.getPackageName());

    }
}
