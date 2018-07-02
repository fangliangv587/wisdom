package com.xz.cenco.weed.txapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.json.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.xz.cenco.wisdom.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/2.
 */

public class AliUserDetailActivity extends Activity {

    Map<String,List<AliRecord>> maps = new HashMap<>();
    Myadapter myadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliuser_detali);

        ExpandableListView listView = findViewById(R.id.listView);

        AliPayAccount aliuser = (AliPayAccount) getIntent().getSerializableExtra("aliuser");
        buildData(aliuser);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayData);
//        listView.setAdapter(adapter);
         myadapter = new Myadapter(maps, this,listView);
        listView.setAdapter(myadapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myadapter.notifyDataSetChanged();
    }

    private void buildData(AliPayAccount account ) {
        String path = Utils.getAliDataPath(account);
        String string = IOUtils.readFile2String(path);
        List<AliRecord> list = GsonUtil.fromJson(string,new TypeToken<List<AliRecord>>(){}.getType());
        if (list==null){
            return ;
        }

        for (AliRecord ar :list){
            String month = ar.getDate().substring(0,7);
            if (maps.keySet().contains(month)){
                List<AliRecord> aliRecords = maps.get(month);
                aliRecords.add(ar);
            }else {
                List<AliRecord> list1 = new ArrayList<>();
                list1.add(ar);
                maps.put(month,list1);
            }

        }

    }


}
