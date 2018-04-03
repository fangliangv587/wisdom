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

public class AssitActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private RecordDao recordDao;
    private ListView listView;
    private List<String> monthList;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assit);
        listView = findViewById(R.id.listView);
        recordDao = getApp().getDaoSession().getRecordDao();
        monthList = getMonthList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monthList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    public List<String> getMonthList(){
        List<String> data =new ArrayList<>();
        List<Record> list = recordDao.queryBuilder().list();
        for (Record r : list){
            String date = r.getDate();
            String substring = date.substring(0, 7);
            if (!data.contains(substring)){
                data.add(substring);
            }
        }

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DayRecordListActivity.class);
        String date = monthList.get(position);
        intent.putExtra(C.extra.yearmonth,date);
        startActivity(intent);
    }
}
