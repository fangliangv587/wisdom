package com.xz.cenco.weed.txapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.json.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.xz.cenco.weed.TestMessageActivity;
import com.xz.cenco.weed.TumblerActivity;
import com.xz.cenco.weed.TxAppActivity;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.App;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class AliUsersActivity extends Activity implements AdapterView.OnItemClickListener {

    List<AliPayAccount> accounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aliusers);

        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        List<String> arrayData = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayData);
        listView.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        accounts = Utils.getAlipayAccount();
        for (AliPayAccount account:accounts){
            String path = Utils.getAliDataPath(account);
            File file = new File(path);
            if (!file.exists()){
                data.add(account.getIdentify()+" 总提现金额:0元");
            }else {
                String string = IOUtils.readFile2String(path);
                List<AliRecord> list = GsonUtil.fromJson(string,new TypeToken<List<AliRecord>>(){}.getType());
                double money=0;
                for (AliRecord ar :list){
                    money+=ar.getMoney();
                }
                data.add(account.getIdentify()+" 总提现金额:"+money+"元");
            }
        }

        return data;
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AliPayAccount account = accounts.get(position);
        Intent intent = new Intent(this, AliUserDetailActivity.class);
        intent.putExtra("aliuser",account);
        startActivity(intent);
    }
}
