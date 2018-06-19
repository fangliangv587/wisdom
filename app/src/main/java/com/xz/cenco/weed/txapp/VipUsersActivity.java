package com.xz.cenco.weed.txapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.json.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.xz.cenco.wisdom.activity.App;
import com.xz.cenco.wisdom.util.C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class VipUsersActivity extends ListActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String json = getIntent().getStringExtra(Constant.extra_user_list);
//        users = GsonUtil.fromJson(json, new TypeToken<List<User>>(){}.getType());
//        List<String> data = getData(users);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
//        setListAdapter(adapter);

        init();
    }

    private void init() {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                String str = IOUtils.readFile2String(C.file.txapp_user);
                final List<User> users = GsonUtil.fromJson(str, new TypeToken<List<User>>(){}.getType());

                if (users==null || users.size()==0){
                    return;
                }

                Level level = getLevel(Integer.parseInt(users.get(0).vip));
                int minute = Integer.parseInt(level.txspantime);
                String money = level.txje + ".0";

                Date curDate = new Date();

                for (int  j =0;j<users.size();j++){
                    User user = users.get(j);
                    List<TxRecord> recordList = App.helper.getTxRecordListByUser(user.user);

                    for (int i=0;i<recordList.size();i++){
                        TxRecord record = recordList.get(i);
                        if (record.txend==1 || record.txend==2){
                            Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                            int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                            record.disminute = disminute;
                            record.standminute = minute;
                            record.standmoney = money;
                            user.txRecord = record;
                        }
                    }


                }

                Collections.sort(users, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {

                        if (user1.txRecord==null || user2.txRecord==null){
                            return -1;
                        }

                        TxRecord record1 = user1.txRecord;
                        TxRecord record2 = user2.txRecord;

                        if (record1.disminute<record2.disminute){
                            return -1;
                        }
                        return 1;
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserAdapter userAdapter = new UserAdapter(VipUsersActivity.this);
                        userAdapter.setData(users);
                        setListAdapter(userAdapter);
                    }
                });

            }

            private Level getLevel( int vip) {
                List<Level> levels = App.helper.getLevels();
                for (Level level : levels) {
                    if (level.level.equals(vip + "")) {
                        return level;
                    }
                }
                return null;
            }
        });
    }

    private List<String> getData(List<User> users){
        ArrayList<String> list = new ArrayList<>();
        for (int i =0;i<users.size();i++){
            User user = users.get(i);
            list.add(user.user);
        }
        return list;
    }
}
