package com.xz.cenco.weed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.weed.txapp.AliPayAccount;
import com.xz.cenco.weed.txapp.DBHelper;
import com.xz.cenco.weed.txapp.Function;
import com.xz.cenco.weed.txapp.Level;
import com.xz.cenco.weed.txapp.NormalUtils;
import com.xz.cenco.weed.txapp.TxRecord;
import com.xz.cenco.weed.txapp.User;
import com.xz.cenco.wisdom.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TxAppActivity extends Activity {

    public static String ip = "120.27.20.92";
    public static Random random = new Random();
    public static int randNumber = (random.nextInt(0x8) + 0x1e15);
    public static int port = randNumber;
    public List<AliPayAccount> aliPayAccountList;
    public TextView infoTv;

    private static final int mes_db = 0x0001;
    private static final int mes_socket = 0x0002;
    private static final int mes_record = 0x0003;
    private int vip = 2;
    public DBHelper helper;
    private int validIndex;
    private String systemusername;

    private CheckBox forceCB;
    private Button person1Btn;
    private Button person2Btn;
    private Button person3Btn;
    private Button person4Btn;
    private Button person5Btn;
    private Button person6Btn;
    private Button person7Btn;
    private Button person8Btn;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mes_socket:
                    break;
                case mes_db:
                    break;
                case mes_record:
                    List<TxRecord> records = (List<TxRecord>) msg.obj;
                    showRecordHistory(records);
                    break;
            }
        }
    };

    private void showRecordHistory(List<TxRecord> records) {
        if (records==null ||records.size()==0){
            ToastUtil.show(this,"无记录");
            return;
        }
        StringBuffer sb = new StringBuffer();

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

        for (int i=0;i<records.size();i++){
            TxRecord txRecord = records.get(i);
            sb.append(txRecord.toString()+"\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提现记录");
        builder.setMessage(sb.toString());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_app);

        infoTv = findViewById(R.id.infoTv);
        infoTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        forceCB = findViewById(R.id.forceCB);
       person1Btn = findViewById(R.id.person1Btn);
       person2Btn = findViewById(R.id.person2Btn);
       person3Btn = findViewById(R.id.person3Btn);
       person4Btn = findViewById(R.id.person4Btn);
       person5Btn = findViewById(R.id.person5Btn);
       person6Btn = findViewById(R.id.person6Btn);
       person7Btn = findViewById(R.id.person7Btn);
       person8Btn = findViewById(R.id.person8Btn);

        aliPayAccountList = getAlipayAccount();


        init();


    }

    private void init() {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                helper = new DBHelper();
                addInfo("连接远程数据库成功");

            }

        });
    }

    public List<AliPayAccount> getAlipayAccount() {
        List<AliPayAccount> list = new ArrayList<AliPayAccount>();


        list.add(new AliPayAccount("13047488791", "霍彬彬", "1396464186", "D5883F51982CD813"));
        list.add(new AliPayAccount("15588591960", "辛忠", "36865", "e534f347bb1a2a09"));
        list.add(new AliPayAccount("13153870185", "辛子财", "19910217", "d57be4ef31095beb"));
        list.add(new AliPayAccount("17864872607", "邱士菊", "1572515687", "7f692ba2e3ae7aba"));


        list.add(new AliPayAccount("18678380687", "霍宁宁", "", ""));

        list.add(new AliPayAccount("13468006640", "李琦", "", ""));
        list.add(new AliPayAccount("13655381031", "张子明", "", ""));
        list.add(new AliPayAccount("15665788385", "王洪伟", "", ""));
        return list;
    }

    public void signMoneyClcik(View view) {
        validIndex = 0;
        AliPayAccount payAccount=null;
        if (view==person1Btn){
            payAccount = aliPayAccountList.get(0);
        }
        if (view==person2Btn){
            payAccount = aliPayAccountList.get(1);
        }
        if (view==person3Btn){
            payAccount = aliPayAccountList.get(2);
        }
        if (view==person4Btn){
            payAccount = aliPayAccountList.get(3);
        }
        if (view==person5Btn){
            payAccount = aliPayAccountList.get(4);
        }
        if (view==person6Btn){
            payAccount = aliPayAccountList.get(5);
        }
        if (view==person7Btn){
            payAccount = aliPayAccountList.get(6);
        }
        if (view==person8Btn){
            payAccount = aliPayAccountList.get(7);
        }
        signMoney(payAccount);
    }



    private void signMoney(final AliPayAccount aliPayAccount) {

        if (aliPayAccount==null){
            addInfo("请选择提现账户");
            return;
        }

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                Level level = getLevel(vip);
                int minute = Integer.parseInt(level.txspantime);
                String money = level.txje + ".0";

                Date curDate = new Date();

                //校验提现账户是否可以提现
                List<TxRecord> txRecordList = helper.getTxRecordListByAccountName(aliPayAccount.getName());
                for (int i=0;i<txRecordList.size();i++){
                    TxRecord record = txRecordList.get(i);
                    if (record.txend==1 || record.txend==2){
                        Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                        int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                        if (disminute < minute) {
                            systemusername = record.user;
                            addInfo("当前提现账户未到时间，不能提现");
                            addInfo(record.toString());
                            return;
                        }

                    }
                }




                List<User> userList = helper.getUsersByVip(vip);
                addInfo("等级"+vip+"的用户数量:"+userList.size());

                List<User> filetUsers = new ArrayList<>();

                for (int i=0;i<userList.size();i++){
                    User user = userList.get(i);

                    List<String> blackList = getBlackList();
                    if(blackList.contains(user.user)){
                     LogUtils.d("黑名单用户:"+user.user);
                     continue;
                    }
                    List<TxRecord> recordList = helper.getTxRecordListByUser(user.user);
                    if (recordList==null){
                        LogUtils.i("增加user:"+user.user);
                        filetUsers.add(user);
                        break;
                    }

                    for (int j=0;j<recordList.size();j++){
                        TxRecord record = recordList.get(j);
                        if (record.txend==1 || record.txend==2){
                            Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                            int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                            if (disminute > minute) {
                                filetUsers.add(user);
                                LogUtils.i("增加到时间user:"+user.user);
                            }

                            break;
                        }

                    }

                }



                addInfo("可用账号:"+filetUsers.size());




               if (filetUsers.size()==0){
                   return;
               }

                for (int i =0;i<filetUsers.size();i++){
                    User user = filetUsers.get(i);
                    addInfo(i+"==>"+user.user);
                }


                if (forceCB.isChecked()){
                    if (!TextUtils.isEmpty(aliPayAccount.getUser())){
                        boolean has = isContainForceUser(filetUsers,aliPayAccount.getUser());
                        if (!has){
                            addInfo("指定用户("+aliPayAccount.getUser()+")时间未到");
                            systemusername = aliPayAccount.getUser();
                            queryRecordClick(null);
                            return;
                        }
                    }
                }






                User user = getBestUser(filetUsers,aliPayAccount.getUser());
                if (user==null){
                    addInfo("没有更多账户可用");
                }
                systemusername = user.user;

                final String mac = TextUtils.isEmpty(aliPayAccount.getMac()) ? Function.getMac() : aliPayAccount.getMac();

                withdraw(aliPayAccount.getName(), aliPayAccount.getAccount(), user.user, mac, money, vip + "");
            }


            public User getBestUser(List<User> list,String name){
                for (int i =0;i<list.size();i++){
                    User user = list.get(i);
                    if (user.user.equalsIgnoreCase(name)){
                        return user;
                    }
                }


                if (list.size()<=validIndex){

                    return null;
                }
                return list.get(validIndex);
            }


            private boolean isContainForceUser(List<User> list,String name){
                for (int i =0;i<list.size();i++){
                    User user = list.get(i);
                    if (user.user.equalsIgnoreCase(name)){
                        return true;
                    }
                }

                return false;
            }


            private List<String> getBlackList(){
                ArrayList<String> list = new ArrayList<>();
                list.add("88888888");


                return list;
            }


            private List<TxRecord> getValidRecord(List<TxRecord> list, int minute) {
                List<TxRecord> validList = new ArrayList<>();
                Date curDate = new Date();
                for (int i = 0; i < list.size(); i++) {
                    TxRecord record = list.get(i);
                    if (record.user.equals("08177219")) {
                        LogUtils.d("aa");
                    }
                    Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                    int disminute = (int) ((curDate.getTime() - date.getTime()) / 1000 / 60);
                    if (disminute > minute) {
                        validList.add(record);
                    }
                }
                return validList;
            }


            private Level getLevel( int vip) {
                List<Level> levels = helper.getLevels();
                for (Level level : levels) {
                    if (level.level.equals(vip + "")) {
                        return level;
                    }
                }
                return null;
            }

            /**
             * 提现
             * 同一支付宝账号（account）、同一androidId 计算最近的时间
             */
            public void withdraw(String name, String account, String user, String androidId, String money, String vipLevel) {

//        money = "0.5";

                addInfo(validIndex+"=>系统用户:" + user + "(vip:" + vipLevel + ")," + "提现:" + account + "(" + name + "),$:" + money);
                LogUtils.d("系统用户:" + user + "(vip:" + vipLevel + ")," + "提现账户:" + account + "(" + name + "),提现金额:" + money + ",mac:" + androidId);

                //提现前需做时间校验

                String commond = Base64.encodeToString(("5|-|" + user + "|-|" + androidId + "|-|" + Base64.encodeToString(account.getBytes(), 0) + "|-|" + money + "|-|" + NormalUtils.getUTF8XMLString(name) + "|-|" + vipLevel + "|-|app|-|").getBytes(), 0);

                String result = socket(commond);
                if (result.equals("31")){
                    addInfo("提现请求成功，请关注提现记录状态");
                }else {
                    if (result.equals("32")) {
                    addInfo("服务器处理提现请求失败，请重试!");
                } else if (result.equals("33")) {
                    addInfo("失败：体验期已过，目前只支持VIP(xxx)申请提现，请开通后提现");
                } else if (result.equals("99")) {
                    addInfo("你提交的数据中包含系统保留字符，请重试！");
                }
                    validIndex++;
                    signMoney(aliPayAccount);
                }


                //可做提现记录的查询
            }


            public String socket(String str) {
                return socket(str, null, 0);
            }


            public String socket(String str, String proxyIP, int proxyPort) {
                try {
                    Socket socket = new Socket(ip, port);
                    ;

//            PrintWriter write = new PrintWriter(socket.getOutputStream());
                    BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));

                    write.write(str);
                    write.flush();

                    socket.shutdownOutput();


                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);


                    String str2 = br.readLine();


//            socket.shutdownInput();

                    addInfo("客户端接收服务端发送信息：" + str2);

                    socket.close();
                    return str2;


                } catch (IOException e) {
                    e.printStackTrace();
                    String log = LogUtils.getExceptionLog(e);
                    addInfo(log);
//                            LogUtils.e("txapp",e);
                }

                return null;
            }

        });


    }



    private TxRecord getUser(List<TxRecord> validRecord, AliPayAccount aliPayAccount) {
        if (validRecord == null || validRecord.size() <= validIndex) {
            return null;
        }
        String user = aliPayAccount.getUser();
        for (TxRecord record : validRecord) {
            if (record.user.equals(user)) {
                return record;
            }
        }

        return validRecord.get(validIndex);
    }

    public void queryRecordClick(View view) {
        if (systemusername == null) {
            return;
        }
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<TxRecord> records = helper.getTxRecordListByUser(systemusername);
                Message message = Message.obtain();
                message.what = mes_record;
                message.obj = records;
                handler.sendMessage(message);
            }
        });
    }

    public void jumpNextClick(View view) {
        validIndex++;
    }
}
