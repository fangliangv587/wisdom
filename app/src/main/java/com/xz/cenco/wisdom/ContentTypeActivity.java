package com.xz.cenco.wisdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.cenco.lib.common.ToastUtil;
import com.xz.cenco.wisdom.entity.WisdomType;
import com.xz.cenco.wisdom.entity.WisdomTypeDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/25.
 */

public class ContentTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    WisdomTypeDao typeDao;
    EditText typeNameEt;
    ArrayAdapter<String> adapter;
    List<WisdomType> wisdomTypes;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        typeDao = getApp().getDaoSession().getWisdomTypeDao();
        initView();

    }

    public void addClick(View view){
        String typeName = typeNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(typeName)){
            ToastUtil.show(this,"不能为空");
            return;
        }
        WisdomType type = new WisdomType();
        type.setName(typeName);
        type.setDate(new Date());
        typeDao.insert(type);
        query();
    }

    private void initView() {

        typeNameEt = findViewById(R.id.typeName);
        adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_expandable_list_item_1,list);
        ListView listView = findViewById(R.id.recycleView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        query();

    }

    private void query(){
        wisdomTypes = typeDao.queryBuilder().list();
        Log.d("xztag",wisdomTypes.size()+"");
        toStringList(wisdomTypes);
        adapter.notifyDataSetChanged();
    }

    private void toStringList( List<WisdomType> wisdomTypes){
        list.clear();
        for (WisdomType type:wisdomTypes) {
            list.add(type.getName());
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WisdomType wisdomType = wisdomTypes.get(position);
        Intent intent = new Intent(this, WisdomActivity.class);
        intent.putExtra("typeName",wisdomType.getName());
        intent.putExtra("typeId",wisdomType.getId());
        startActivity(intent);
    }
}
