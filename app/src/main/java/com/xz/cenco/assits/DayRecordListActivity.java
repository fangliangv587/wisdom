package com.xz.cenco.assits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cenco.lib.common.DateUtil;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.util.C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DayRecordListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private RecordDao recordDao;
    private ListView listView;
    private List<String> dayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_record_list);
        recordDao = getApp().getDaoSession().getRecordDao();
        listView = findViewById(R.id.listView);
        dayList = getDayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    public List<String> getDayList(){
        List<String> data =new ArrayList<>();

        List<Record> list = recordDao.queryBuilder().list();
        for (Record r : list){
            String date = r.getDate();

            if (!data.contains(date)){
                data.add(date);
            }
        }

        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Date date1 = DateUtil.getDate(o1, DateUtil.FORMAT_YMD);
                Date date2 = DateUtil.getDate(o2, DateUtil.FORMAT_YMD);
                if (date1.after(date2)){
                    return -1;
                }

                return 1;
            }
        });

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
