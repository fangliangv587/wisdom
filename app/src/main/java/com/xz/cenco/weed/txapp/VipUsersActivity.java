package com.xz.cenco.weed.txapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
                            if (user.txRecord==null){
                                Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                                int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                                record.disminute = disminute;
                                record.standminute = minute;
                                record.standmoney = money;
                                user.txRecord = record;
                            }
                            if (record.txend == 2 || record.txend==1){
                                String name =record.txxm;
                                for (int k=0;k<alipayAccounts.size();k++){
                                    AliPayAccount account = alipayAccounts.get(k);
                                    String name1 = account.getName();
                                    if (name.equals(name1)&& !user.names.contains(name)){
                                        user.names.add(name);
                                    }
                                }
                            }


                        }
                    }
                    String msg = (j+1)+"/"+orinalUsers.size();
                    Message message = Message.obtain(handler, msg_progress, msg);
                    handler.sendMessage(message);


                }

                Collections.sort(orinalUsers, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {

                        if (user1==user2){
                            return 0;
                        }
                        if (user1.txRecord == null && user2.txRecord == null) {
                            return 0;
                        }

                        if (user1.txRecord == null ) {
                            return 1;
                        }

                        if (user2.txRecord == null ) {
                            return -1;
                        }
                        TxRecord record1 = user1.txRecord;
                        TxRecord record2 = user2.txRecord;

                        if (record1.disminute < record2.disminute) {
                            return 1;
                        }
                        return -1;
                    }
                });

                Collections.sort(orinalUsers, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {

                        int size1 = user1.names.size();
                        int size2 = user2.names.size();


                        if (size1 < size2) {
                            return 1;
                        }
                        if (size1 == size2) {
                            return 0;
                        }
                        return -1;
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
        list.add(new AliPayAccount("15665851629", "霍彬彬", "", "6d89828bd6001ddb"));
        list.add(new AliPayAccount("15588591960", "辛忠", "", "a25b24b851b43890"));
        list.add(new AliPayAccount("13153870185", "辛子财", "", "4782124a207df377"));
        list.add(new AliPayAccount("17864872607", "邱士菊", "", "7f692ba2e3ae7aba"));


        list.add(new AliPayAccount("18678380687", "霍宁宁", "", "a8b66bb0d0ef4227"));

        list.add(new AliPayAccount("13082761640", "霍合忠", "", "83be66aa093da9a3"));
        list.add(new AliPayAccount("13181384566", "谈书云", "", "d03fd655bd01b3d8"));

//        list.add(new AliPayAccount("13468006640", "李琦", "", "68f691e12a5a6827"));
//        list.add(new AliPayAccount("13655381031", "张子明", "", "3bab4473e93d0962"));

        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = users.get(position);
        if (user.txRecord != null && user.txRecord.standminute > user.txRecord.disminute) {
            ToastUtil.show(this, "时间未到，不能对此用户进行操作");
            return;
        }

        queryAlipayRecord(position,user);
    }
    ProgressDialog progressDialog;
    private void dismissProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
    private void queryAlipayRecord(final int position,final User user){
        progressDialog = ProgressDialog.show(this, "请稍后...", "");
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                List<BlackAccount> accounts = App.helper.getBlackAccounts();

                double money =0;
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < alipayAccounts.size(); i++) {
                    AliPayAccount aliPayAccount = alipayAccounts.get(i);
                    aliPayAccount.setRecord(null);

                    for (int j=0;j<accounts.size();j++){
                        BlackAccount account = accounts.get(j);
                        if (account.zh.equals(aliPayAccount.getAccount())){
                            aliPayAccount.setBlack(true);
                            break;
                        }
                    }


                    List<TxRecord> records = App.helper.getTxRecordListByAliAccountName(aliPayAccount.getName(), aliPayAccount.getAccount());
                    double usermoney=0;
                    for (int j=0;j<records.size();j++){
                        TxRecord record = records.get(j);
                        if(record.txend==2){
                            double sigleMoney = Double.parseDouble(record.txje);
                            usermoney+=sigleMoney;
                        }

                        if (aliPayAccount.getRecord()==null &&(record.txend==1 || record.txend == 2)){
                            aliPayAccount.setRecord(record);
                        }
                    }
                    sb.append(aliPayAccount.getName()+":"+usermoney+"元  ");
                    money+=usermoney;
                }


                Collections.sort(alipayAccounts, new Comparator<AliPayAccount>() {
                    @Override
                    public int compare(AliPayAccount o1, AliPayAccount o2) {
                        if (o1.isBlack()==o2.isBlack()){
                            return 0;
                        }

                        if (o1.isBlack()!=o2.isBlack()  && o1.isBlack()){
                            return 1;
                        }

                        if (o1.isBlack()!=o2.isBlack()  && o2.isBlack()){
                            return -1;
                        }

                        return 0;

                    }
                });

                sb.append("总:"+money);
                final String str = sb.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        selectAlipayUser(position,user,str);
                    }
                });
            }
        });
    }


    public void selectAlipayUser(int position,final User user,final String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);


        TextView textView = new TextView(this);
        textView.setText(info);
        textView.setTextSize(10);
        layout.addView(textView);

        Date curDate = new Date();

        String userinfo="";

        for (int i = 0; i < alipayAccounts.size(); i++) {
            final AliPayAccount account = alipayAccounts.get(i);


            View view = LayoutInflater.from(this).inflate(R.layout.item_view_alipayuser, null);
            TextView userNameTv = view.findViewById(R.id.userNameTv);
            TextView infoTv = view.findViewById(R.id.infoTv);
            TextView recordTv = view.findViewById(R.id.recordTv);
            View layoutView = view.findViewById(R.id.layout);

            userNameTv.setText(account.getName());
            infoTv.setText(account.getRecord()==null?"无成功记录":account.getRecord().toString());

            if (account.isBlack()){
                userNameTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                infoTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }


            boolean userState = false;
            int minute = Integer.parseInt(user.level.txspantime);

            if(user.txRecord!=null){
                Date date = DateUtil.getDate(user.txRecord.txtime, DateUtil.FORMAT_YMDHMS);
                int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);

                userinfo ="系统用户最近的提现间隔:"+disminute+"("+minute+")   \n下次提现时间:"+DateUtil.getDateString(getDate(date,minute),DateUtil.FORMAT_YMDHMS)+"\n\n";
                userState = disminute>minute;
            }else {
                userinfo = "用户无最近提现记录    \n\n";
                userState = true;
            }

            boolean aliState = false;
            StringBuffer aliSb = new StringBuffer();
            if (account.getRecord()!=null){

                Date date1 = DateUtil.getDate(account.getRecord().txtime, DateUtil.FORMAT_YMDHMS);
                int disminute1 = (int) ((curDate.getTime() - date1.getTime()) / 1000 / 60);
                aliSb.append("最近的提现间隔:"+disminute1+"("+minute+")   \n下次提现时间:"+DateUtil.getDateString(getDate(date1,minute),DateUtil.FORMAT_YMDHMS));
                aliState = disminute1>minute;

            }else {
                aliSb.append("无最近提现记录");
                aliState = true;
            }


            boolean isOk = userState && aliState;
            account.setTimeOk(isOk);
            infoTv.setText(infoTv.getText()+"\n"+aliSb.toString());

            if (!isOk){
                layoutView.setBackgroundColor(Color.GRAY);
            }

            layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    withdraw(account, user);
                }
            });

            recordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordByAccount(account);
                }
            });

            layout.addView(view);
        }

        textView.setText(textView.getText()+"\n\n"+userinfo);
        builder.setTitle("选择提现用户("+(position+1)+"==>"+user.user+")");
        builder.setView(layout);
        builder.create().show();
    }

    private Date getDate(Date date,int minute){

        long time = date.getTime()+minute*60*1000;
        Date date1 = new Date();
        date1.setTime(time);
        return date1;
    }

    private void withdraw(final AliPayAccount aliPayAccount, final User user) {

        if (aliPayAccount.isBlack()){
            ToastUtil.show(this,"支付宝账户被拉黑名单！");
            return;
        }

        if (!aliPayAccount.isTimeOk()){
            ToastUtil.show(this,"时间限制");
            return;
        }

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
