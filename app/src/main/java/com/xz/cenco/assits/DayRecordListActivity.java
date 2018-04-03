package com.xz.cenco.assits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.util.C;

import java.util.ArrayList;
import java.util.List;

public class DayRecordListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private String yearmonth;
    private RecordDao recordDao;
    private ListView listView;
    private List<String> dayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_record_list);
        yearmonth = getIntent().getStringExtra(C.extra.yearmonth);
        recordDao = getApp().getDaoSession().getRecordDao();
        listView = findViewById(R.id.listView);
        dayList = getDayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    public List<String> getDayList(){
        List<String> data =new ArrayList<>();
        if (yearmonth == null){
            return data;
        }

        List<Record> list = recordDao.queryBuilder().where(RecordDao.Properties.Date.like(yearmonth+"%")).list();
        for (Record r : list){
            String date = r.getDate();

            if (!data.contains(date)){
                data.add(date);
            }
        }

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String date = dayList.get(position);
        Intent intent = new Intent(this, AppRecordActivity.class);
        intent.putExtra(C.extra.date,date);
        startActivity(intent);
    }
}
