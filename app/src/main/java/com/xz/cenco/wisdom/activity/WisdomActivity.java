package com.xz.cenco.wisdom.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cenco.lib.common.ToastUtil;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.util.C;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by Administrator on 2018/2/25.
 */

public class WisdomActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    WisdomDao wisdomDao;
    EditText typeNameEt;
    String typeName;
    long typeId;
    ArrayAdapter<String> adapter;
    List<Wisdom> wisdoms;
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisdom);
        wisdomDao = getApp().getDaoSession().getWisdomDao();
        typeName = getIntent().getStringExtra("typeName");
        typeId = getIntent().getLongExtra("typeId", -1);
        initView();

    }


    public void addClick(View view){
        Intent intent = new Intent(this, WisdomAddActivity.class);
        intent.putExtra("typeName",typeName);
        intent.putExtra("typeId",typeId);
        startActivityForResult(intent, C.request.wisdom_add);
    }

    private void initView() {

        typeNameEt = findViewById(R.id.typeName);

        adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_expandable_list_item_1,list);
        ListView listView = findViewById(R.id.recycleView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        query();

    }

    private void query(){
        wisdoms = wisdomDao.queryBuilder().where(WisdomDao.Properties.Type.eq(typeId)).list();
        Log.d("xztag", wisdoms.size()+"");
        toStringList(wisdoms);
        adapter.notifyDataSetChanged();
    }

    private void toStringList( List<Wisdom> wisdomTypes){
        list.clear();
        for (Wisdom type:wisdomTypes) {
            list.add(type.getText());
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final Dialog dialog = new Dialog(this);
        Button button = new Button(this);
        button.setText("删  除");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wisdom wisdom = wisdoms.get(position);
                wisdomDao.delete(wisdom);
                dialog.dismiss();
                query();
            }
        });
        dialog.setContentView(button);
        dialog.show();

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode == C.request.wisdom_add){
            query();
        }
    }
}
