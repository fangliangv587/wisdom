package com.xz.cenco.weed.txapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.json.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.xz.cenco.wisdom.R;
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

public class VipUsersActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView listView;
    TextView infoTv;
     List<User> users;
    List<AliPayAccount> alipayAccounts;
    private static final int msg_progress = 0x0001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case msg_progress:
                    int p = (int) msg.obj;
                    infoTv.setText(p+"/100");
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vip_users);
         listView = findViewById(R.id.listview);
         infoTv = findViewById(R.id.infoTv);
        listView.setOnItemClickListener(this);

        alipayAccounts = getAlipayAccount();
        init();

    }

    private void init() {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                String str = IOUtils.readFile2String(C.file.txapp_user);
                users = GsonUtil.fromJson(str, new TypeToken<List<User>>(){}.getType());

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
                    user.recordList = recordList;

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
                    int p = (int) (j*1.0f/users.size()*100);
                    Message message = Message.obtain(handler, msg_progress, p);
                    handler.sendMessage(message);


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
                        listView.setAdapter(userAdapter);
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


    public List<AliPayAccount> getAlipayAccount() {
        List<AliPayAccount> list = new ArrayList<AliPayAccount>();


        list.add(new AliPayAccount("13047488791", "霍彬彬", "", ""));
        list.add(new AliPayAccount("15588591960", "辛忠", "", ""));
        list.add(new AliPayAccount("13153870185", "辛子财", "", ""));
        list.add(new AliPayAccount("17864872607", "邱士菊", "", ""));


        list.add(new AliPayAccount("18678380687", "霍宁宁", "", ""));

        list.add(new AliPayAccount("13468006640", "李琦", "", ""));
        list.add(new AliPayAccount("13655381031", "张子明", "", ""));
        list.add(new AliPayAccount("15665788385", "王洪伟", "", ""));
        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = users.get(position);
        selectAlipayUser(user);
    }


    public void selectAlipayUser( User user){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20,20,20,20);
        for (int i=0;i<alipayAccounts.size();i++){
            AliPayAccount account = alipayAccounts.get(i);
            TextView textView = new TextView(this);
            textView.setText(account.getName());
            textView.setTextSize(20);
            textView.setPadding(10,10,10,10);
            layout.addView(textView);
        }
        builder.setTitle("选择提现用户");
        builder.setView(layout);
        builder.create().show();
    }
}
