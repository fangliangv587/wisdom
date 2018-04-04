package com.xz.cenco.wisdom.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.bean.Backups;
import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomDao;
import com.xz.cenco.wisdom.entity.WisdomType;
import com.xz.cenco.wisdom.entity.WisdomTypeDao;
import com.xz.cenco.wisdom.util.C;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/25.
 */

public class TypeActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    WisdomTypeDao typeDao;

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



    public void  addClick(View view){
        Intent intent = new Intent(this, TypeAddActivity.class);
        startActivityForResult(intent,C.request.type_add);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode == C.request.type_add){
            query();
        }
    }

    private void initView() {


        adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_expandable_list_item_1,list);
        ListView listView = (ListView)findViewById(R.id.recycleView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final Dialog dialog = new Dialog(this);
        Button button = new Button(this);
        button.setText("删  除");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisdomType wisdomType = wisdomTypes.get(position);
                typeDao.delete(wisdomType);

                WisdomDao wisdomDao = getApp().getDaoSession().getWisdomDao();
                List<Wisdom> list = wisdomDao.queryBuilder().where(WisdomDao.Properties.Type.eq(wisdomType.getId())).list();
                for (Wisdom w : list){
                    wisdomDao.delete(w);
                }
                dialog.dismiss();
                query();
            }
        });
        dialog.setContentView(button);
        dialog.show();
        return true;
    }
}
