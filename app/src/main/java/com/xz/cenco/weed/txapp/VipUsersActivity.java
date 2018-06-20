package com.xz.cenco.weed.txapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.google.gson.reflect.TypeToken;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.App;
import com.xz.cenco.wisdom.util.C;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/6/19.
 */

public class VipUsersActivity extends Activity implements AdapterView.OnItemClickListener, UserAdapter.UserAdapterListener {

    ListView listView;
    TextView infoTv;
    EditText searcgEt;
    List<User> users;
    List<User> orinalUsers;
    List<AliPayAccount> alipayAccounts;
    UserAdapter userAdapter;
    public static String ip = "120.27.20.92";
    public static Random random = new Random();
    public static int randNumber = (random.nextInt(0x8) + 0x1e15);
    public static int port = randNumber;

    private static final int msg_progress = 0x0001;
    private static final int msg_time_unreach = 0x0002;
    private static final int msg_record = 0x0003;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_progress:
                    String  p = (String) msg.obj;
                    infoTv.setText(p);
                    break;
                case msg_time_unreach:
                    List<TxRecord> recordList = (List<TxRecord>) msg.obj;
                    showRecordHistory(recordList, true);
                    break;
                case msg_record:
                    List<TxRecord> recordList1 = (List<TxRecord>) msg.obj;
                    showRecordHistory(recordList1, false);
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
        searcgEt = findViewById(R.id.searcgEt);
        infoTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        listView.setOnItemClickListener(this);

        alipayAccounts = getAlipayAccount();
        init();

    }

    private void init() {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                String str = IOUtils.readFile2String(C.file.txapp_user);
                orinalUsers = GsonUtil.fromJson(str, new TypeToken<List<User>>() {
                }.getType());

                if (orinalUsers == null || orinalUsers.size() == 0) {
                    return;
                }

                Level level = getLevel(Integer.parseInt(orinalUsers.get(0).vip));
                int minute = Integer.parseInt(level.txspantime);
                String money = level.txje + ".0";

                Date curDate = new Date();

                for (int j = 0; j < orinalUsers.size(); j++) {
                    User user = orinalUsers.get(j);
                    List<TxRecord> recordList = App.helper.getTxRecordListByUser(user.user);
                    user.recordList = recordList;
                    user.level = level;

                    for (int i = 0; i < recordList.size(); i++) {
                        TxRecord record = recordList.get(i);
                        if (record.txend == 1 || record.txend == 2) {
                            Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                            int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                            record.disminute = disminute;
                            record.standminute = minute;
                            record.standmoney = money;
                            user.txRecord = record;
                            break;
                        }
                    }
                    String msg = (j+1)+"/"+orinalUsers.size();
                    Message message = Message.obtain(handler, msg_progress, msg);
                    handler.sendMessage(message);


                }

                Collections.sort(orinalUsers, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {

                        if (user1.txRecord == null || user2.txRecord == null) {
                            return -1;
                        }

                        TxRecord record1 = user1.txRecord;
                        TxRecord record2 = user2.txRecord;

                        if (record1.disminute < record2.disminute) {
                            return -1;
                        }
                        return 1;
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userAdapter = new UserAdapter(VipUsersActivity.this);
                        users = orinalUsers;
                        userAdapter.setData(orinalUsers);
                        userAdapter.setListener(VipUsersActivity.this);
                        listView.setAdapter(userAdapter);
                    }
                });

            }

            private Level getLevel(int vip) {
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


        list.add(new AliPayAccount("13047488791", "霍彬彬", "", "a72af041a8b6be33"));
        list.add(new AliPayAccount("15588591960", "辛忠", "", "a25b24b851b43890"));
        list.add(new AliPayAccount("13153870185", "辛子财", "", "4782124a207df377"));
        list.add(new AliPayAccount("17864872607", "邱士菊", "", "7f692ba2e3ae7aba"));


        list.add(new AliPayAccount("18678380687", "霍宁宁", "", "a8b66bb0d0ef4227"));

        list.add(new AliPayAccount("13082761640", "霍合忠", "", "83be66aa093da9a3"));
        list.add(new AliPayAccount("13181384566", "谈书云", "", "d03fd655bd01b3d8"));

//        list.add(new AliPayAccount("13468006640", "李琦", "", ""));
//        list.add(new AliPayAccount("13655381031", "张子明", "", ""));

        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = users.get(position);
        if (user.txRecord != null && user.txRecord.standminute > user.txRecord.disminute) {
            ToastUtil.show(this, "时间未到，不能对此用户进行操作");
            return;
        }

        selectAlipayUser(position,user);
    }


    public void selectAlipayUser(int position,final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);
        for (int i = 0; i < alipayAccounts.size(); i++) {
            final AliPayAccount account = alipayAccounts.get(i);
            LinearLayout layout1 = new LinearLayout(this);
            layout1.setOrientation(LinearLayout.HORIZONTAL);
            TextView textView1 = new TextView(this);
            textView1.setText(account.getName());
            textView1.setTextSize(20);
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    withdraw(account, user);
                }
            });
            textView1.setPadding(10, 10, 10, 10);
            layout1.addView(textView1);

            TextView textView2 = new TextView(this);
            textView2.setText("记录");
            textView2.setTextSize(20);
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordByAccount(account);
                }
            });
            textView2.setPadding(10, 10, 10, 10);
            layout1.addView(textView2);

            layout.addView(layout1);
        }
        builder.setTitle("选择提现用户("+(position+1)+"==>"+user.user+")");
        builder.setView(layout);
        builder.create().show();
    }

    private void withdraw(final AliPayAccount aliPayAccount, final User user) {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                Level level = user.level;
                int minute = Integer.parseInt(level.txspantime);
                Date curDate = new Date();
                List<TxRecord> recordList = App.helper.getTxRecordListByAccountName(aliPayAccount.getName());
                for (int i = 0; i < recordList.size(); i++) {
                    TxRecord record = recordList.get(i);
                    if (record.txend == 1 || record.txend == 2) {
                        Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                        int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                        if (disminute < minute) {
                            Message message = Message.obtain(handler, msg_time_unreach, recordList);
                            handler.sendMessage(message);
                            return;
                        }
                    }
                }

                final String mac = TextUtils.isEmpty(aliPayAccount.getMac()) ? Function.getMac() : aliPayAccount.getMac();

                withdraw(aliPayAccount.getName(), aliPayAccount.getAccount(), user.user, mac, level.txje, level.level);
            }


            /**
             * 提现
             * 同一支付宝账号（account）、同一androidId 计算最近的时间
             */
            public void withdraw(String name, String account, String user, String androidId, String money, String vipLevel) {


                addInfo("=>系统用户:" + user + "(vip:" + vipLevel + ")," + "提现:" + account + "(" + name + "),$:" + money);
                LogUtils.d("系统用户:" + user + "(vip:" + vipLevel + ")," + "提现账户:" + account + "(" + name + "),提现金额:" + money + ",mac:" + androidId);

                //提现前需做时间校验

                String commond = android.util.Base64.encodeToString(("5|-|" + user + "|-|" + androidId + "|-|" + android.util.Base64.encodeToString(account.getBytes(), 0) + "|-|" + money + "|-|" + NormalUtils.getUTF8XMLString(name) + "|-|" + vipLevel + "|-|app|-|").getBytes(), 0);

                String result = socket(commond);
                if (result.equals("31")) {
                    addInfo("提现请求成功，请关注提现记录状态");
                } else {
                    if (result.equals("32")) {
                        addInfo("服务器处理提现请求失败，请重试!");
                    } else if (result.equals("33")) {
                        addInfo("失败：体验期已过，目前只支持VIP(xxx)申请提现，请开通后提现");
                    } else if (result.equals("99")) {
                        addInfo("你提交的数据中包含系统保留字符，请重试！");
                    }

                }


                //可做提现记录的查询
            }


            public String socket(String str) {
                return socket(str, null, 0);
            }


            public String socket(String str, String proxyIP, int proxyPort) {
                try {
                    Socket socket = new Socket(ip, port);
                    BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));

                    write.write(str);
                    write.flush();

                    socket.shutdownOutput();


                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String str2 = br.readLine();

                    addInfo("客户端接收服务端发送信息：" + str2);

                    socket.close();
                    return str2;


                } catch (IOException e) {
                    e.printStackTrace();
                    String log = LogUtils.getExceptionLog(e);
                    addInfo(log);
                }

                return null;
            }
        });
    }


    private void showRecordHistory(List<TxRecord> records, boolean showFailReason) {
        if (records == null || records.size() == 0) {
            ToastUtil.show(this, "无记录");
            return;
        }
        StringBuffer sb = new StringBuffer();
        if (showFailReason) {
            sb.append("\n提现失败，当前支付宝账号的提现时间间隔未达等级要求\n\n\n");
        }

        sb.append("1-未支付，正在支付中\n");
        sb.append("2-已结算\n");
        sb.append("3-帐号不是手机号或email，提现失败\n");
        sb.append("4-帐号未注册支付宝，提现失败\n");
        sb.append("5-支付宝帐号未激活，提现失败\n");
        sb.append("6-帐号未实名认证激活冻结，提现失败\n");
        sb.append("7-未知原因1，提现失败\n");
        sb.append("8-支付失败-请开通VIP\n");
        sb.append("9-用户时间限制，提现失败\n");
        sb.append("10-收款帐号异常，不能收款\n");
        sb.append("11-未完善身份信息或没有余额账户\n");
        sb.append("12-公司类型账户须通过认证才可收款\n");
        sb.append("13-可能姓名校验错误，提现失败\n\n");

        for (int i = 0; i < records.size(); i++) {
            TxRecord txRecord = records.get(i);
            sb.append(txRecord.toString() + "\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提现记录");
        TextView textView = new TextView(this);
        textView.setTextSize(12);
        textView.setText(sb.toString());
        textView.setPadding(20,20,20,20);
        builder.setView(textView);
//        builder.setMessage(sb.toString());

        builder.create().show();
    }

    public void addInfo(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                infoTv.setText(infoTv.getText().toString() + "\n " + str);
                int offset = infoTv.getLineCount() * infoTv.getLineHeight();
                if (offset > infoTv.getHeight()) {
                    infoTv.scrollTo(0, offset - infoTv.getHeight());
                }
            }
        });

    }


    public void recordByAccount(final AliPayAccount account){
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<TxRecord> recordList = App.helper.getTxRecordListByAliAccountName(account.getName(),account.getAccount());
                Message message = Message.obtain(handler, msg_record, recordList);
                handler.sendMessage(message);
            }
        });

    }

    @Override
    public void onUserRecord(final User user) {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                final List<TxRecord> recordList = App.helper.getTxRecordListByUser(user.user);

                Message message = Message.obtain(handler, msg_record, recordList);
                handler.sendMessage(message);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        user.recordList = recordList;
                        userAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    public void restoreClick(View view) {
        users=orinalUsers;
        userAdapter.setData(orinalUsers);
        userAdapter.notifyDataSetChanged();
    }

    public void searchClick(View view) {
        String search = searcgEt.getText().toString();
        if (TextUtils.isEmpty(search)){
            ToastUtil.show(this,"请输入搜索内容");
            return;
        }

        if (orinalUsers==null){
            ToastUtil.show(this,"无用户列表");
            return;
        }

        if (userAdapter==null){
            return;
        }

        List<User> list = new ArrayList<>();
        for (int i=0;i<orinalUsers.size();i++){
            User user = orinalUsers.get(i);
            if (user.user.contains(search)){
                list.add(user);
                continue;
            }

            if(user.txRecord==null){
                continue;
            }
            if (user.txRecord.txxm.contains(search)){
                list.add(user);
                continue;
            }
        }

        users=list;
        userAdapter.setData(list);
        userAdapter.notifyDataSetChanged();


    }
}
