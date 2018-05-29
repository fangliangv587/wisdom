package com.xz.cenco.weed;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xz.cenco.weed.aiaixg.AiaixgHelper;
import com.xz.cenco.weed.coohua.CoohuaActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class WeedListActivity extends ListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> arrayData = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayData);
        setListAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("不倒翁");
        data.add("酷划");
        data.add("国证投资");
        return data;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Intent intent = null;
        if (position==0){
            intent =  new Intent(this, TumblerActivity.class);
            startActivity(intent);
            return;
        }
        if (position==1){
            intent =  new Intent(this, CoohuaActivity.class);
            startActivity(intent);
            return;
        }

        if (position==2){
            AiaixgHelper main = new AiaixgHelper(this);
            main.start();
            return;
        }


    }
}
