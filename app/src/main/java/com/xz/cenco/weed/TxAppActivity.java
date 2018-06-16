package com.xz.cenco.weed;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.weed.txapp.AliPayAccount;
import com.xz.cenco.weed.txapp.DBHelper;
import com.xz.cenco.weed.txapp.Function;
import com.xz.cenco.weed.txapp.Level;
import com.xz.cenco.weed.txapp.NormalUtils;
import com.xz.cenco.weed.txapp.TxRecord;
import com.xz.cenco.wisdom.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_app);

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                main();

//                Function.refreshLoginTimeWithFangliang();
//                Function.accountFangliang();

            }

            private void main() {
                List<AliPayAccount> accounts = getAlipayAccount();
                AliPayAccount aliPayAccount = accounts.get(0);

                DBHelper helper = new DBHelper();
                int vip = 2;
                List<TxRecord> list = helper.getTxRecordList(vip);

                LogUtils.v("txapp","有提现记录且vip="+vip+"的用户数量:"+list.size()+"");

                Level level = getLevel(helper, vip);
                int minute = Integer.parseInt(level.txspantime);
                String money = level.txje+".0";

                List<TxRecord> validRecord = getValidRecord(list, minute);

                LogUtils.d("txapp","超时可使用的账户数量:"+validRecord.size()+"");

                TxRecord record = getUser(validRecord,aliPayAccount);

                String mac = TextUtils.isEmpty(aliPayAccount.getMac())? Function.getMac():aliPayAccount.getMac();
                withdraw(aliPayAccount.getName(),aliPayAccount.getAccount(),record.user,mac,money,vip+"");
            }

            private TxRecord getUser(List<TxRecord> validRecord, AliPayAccount aliPayAccount) {
                String user = aliPayAccount.getUser();
                for (TxRecord record : validRecord){
                    if (record.user.equals(user)){
                        return record;
                    }
                }

                return validRecord.get(0);
            }

            /**
             * 提现
             * 同一支付宝账号（account）、同一androidId 计算最近的时间
             */
            public void withdraw(String name, String account, String user, String androidId, String money, String vipLevel) {

//        money = "0.5";

                LogUtils.i("txapp","系统用户:" + user + "(vip:" + vipLevel + ")," + "提现账户:" + account + "(" + name + "),提现金额:" + money + ",mac:" + androidId);

                //提现前需做时间校验

                String commond = Base64.encodeToString(("5|-|" + user + "|-|" + androidId + "|-|" + Base64.encodeToString(account.getBytes(), 0) + "|-|" + money + "|-|" + NormalUtils.getUTF8XMLString(name) + "|-|" + vipLevel + "|-|app|-|").getBytes(), 0);

                String result = socket(commond);

                if (result == null) {
                    LogUtils.w("txapp","返回空");
                } else if (result.equals("31")) {
                    LogUtils.w("txapp","提现请求成功，请关注提现记录状态");
                } else if (result.equals("32")) {
                    LogUtils.w("txapp","服务器处理提现请求失败，请重试!");
                } else if (result.equals("33")) {
                    LogUtils.w("txapp","失败：体验期已过，目前只支持VIP(xxx)申请提现，请开通后提现");
                } else if (result.equals("99")) {
                    LogUtils.w("txapp","你提交的数据中包含系统保留字符，请重试！");
                }

                //可做提现记录的查询
            }


            public  String socket(String str) {
                return socket(str, null, 0);
            }


            public  String socket(String str, String proxyIP, int proxyPort) {
                try {
                    Socket socket =new Socket(ip, port);  ;

//            PrintWriter write = new PrintWriter(socket.getOutputStream());
                    BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
                    LogUtils.i("txapp","socket 写命令");
                    write.write(str);
                    write.flush();
                    LogUtils.i("txapp","socket 写入");
                    socket.shutdownOutput();


                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    LogUtils.i("txapp","读取数据");
                    String str2 = br.readLine();
                    LogUtils.i("txapp","读取数据成功");

//            socket.shutdownInput();

                    LogUtils.i("txapp","客户端接收服务端发送信息：" + str2);
                    socket.close();
                    return str2;


                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e("txapp",e);
                }

                return null;
            }


            public  List<AliPayAccount> getAlipayAccount() {
                List<AliPayAccount> list = new ArrayList<AliPayAccount>();


                list.add(new AliPayAccount("13047488791", "霍彬彬", "1396464186", "D5883F51982CD813"));
                list.add(new AliPayAccount("13153870185", "辛子财", "19910217", "d57be4ef31095beb"));
                list.add(new AliPayAccount("17864872607", "邱士菊", "1572515687", "7f692ba2e3ae7aba"));
                list.add(new AliPayAccount("15588591960", "辛忠", "36865", "e534f347bb1a2a09"));


                list.add(new AliPayAccount("18678380687", "霍宁宁", "", ""));

                list.add(new AliPayAccount("13468006640", "李琦", "", ""));
                list.add(new AliPayAccount("15665788385", "王洪伟", "", ""));
                list.add(new AliPayAccount("13655381031", "张子明", "", ""));
                return list;
            }

            private  List<TxRecord>  getValidRecord(List<TxRecord> list, int minute) {
                List<TxRecord> validList = new ArrayList<>();
                Date curDate = new Date();
                for (int i =0;i<list.size();i++){
                    TxRecord record = list.get(i);
                    if (record.user.equals("08177219")){
                        LogUtils.d("aa");
                    }
                    Date date = DateUtil.getDate(record.txtime, DateUtil.FORMAT_YMDHMS);
                    int disminute = (int) ((curDate.getTime()-date.getTime())/1000/60);
                    if (disminute>minute){
                        validList.add(record);
                    }
                }
                return validList;
            }



            private Level getLevel(DBHelper helper, int vip) {
                List<Level> levels = helper.getLevels();
                for (Level level :levels){
                    if (level.level.equals(vip+"")){
                        return level;
                    }
                }
                return null;
            }
        });



    }
}
