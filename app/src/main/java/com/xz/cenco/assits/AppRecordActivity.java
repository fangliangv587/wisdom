package com.xz.cenco.assits;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.util.C;
import com.xz.cenco.wisdom.util.Util;

import java.util.ArrayList;
import java.util.List;

public class AppRecordActivity extends BaseActivity {

    private RecordDao recordDao;
    private String date;
    private TextView infoTv;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_record);
        date = getIntent().getStringExtra(C.extra.date);
        recordDao = getApp().getDaoSession().getRecordDao();
        infoTv = findViewById(R.id.infoTv);
        infoTv.setText(date);

        List<String> appRecords = getAppRecords();
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appRecords);
        listView.setAdapter(adapter);
    }

    public List<String> getAppRecords(){
        List<String> data = new ArrayList<>();
        if (date == null){
            return data;
        }

        List<Record> list = recordDao.queryBuilder().where(RecordDao.Properties.Date.eq(date)).list();
        for (Record r:list){
            data.add(getFormatString(r));
        }
        return data;
    }

    public String getFormatString(Record r){
        int stayTime = r.getStayTime();
//        return r.getId()+": "+r.getDate()+" "+ r.getInTime()+"进入，"+r.getOutTime()+"离开，驻留时间"+r.getStayTimeString(stayTime)+",应用:"+ Util.getProgramNameByPackageName(this,r.getPackageName());

        return Util.getProgramNameByPackageName(this,r.getPackageName())+"  "+ r.getInTime() + "-"+ r.getOutTime()+"    "+r.getStayTimeString(stayTime);
    }
}
