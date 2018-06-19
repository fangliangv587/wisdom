package com.xz.cenco.weed.txapp;


import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.log.LogUtils;

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
import java.util.Properties;
import java.util.Random;

/**
 * Created by Administrator on 2018/6/9 0009.
 */
public class Function  {

    public static String ip = "120.27.20.92";
    public static Random random = new Random();
    public static int randNumber = (random.nextInt(0x8) + 0x1e15);
    public static int port = randNumber;

    public static String proxyIP;
    public static int proxyPort = -1;

    public static DBHelper dbHelper;

    public static void main(String[] args) {

//        String password = decode("MTMyMTA0MTA1MzM=");
//        log(password);

        dbHelper = new DBHelper();
        withDraw();

//        accountFangliang();
//        refreshLoginTimeWithFangliang();
        refreshMoneyWithFangliang();
//        registrUserTest();
    }

    public static String decode(String pass) {

        byte[] decode = Base64.decode(pass, 0);
        String ds = new String(decode);
        return ds;
    }

    public static String encode(String str) {

        String  s = Base64.encodeToString(str.getBytes(), 0);
        LogUtils.d(s);
        return s;
    }


    public static List<AliPayAccount> getAlipayAccount() {
        List<AliPayAccount> list = new ArrayList<AliPayAccount>();
//        list.add(new AliPayAccount("18250518896", "李剑飞", "lf667788", "11670f970d9c25a0"));


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

    private static void withDraw() {
        List<User> userList = dbHelper.getNotCashUserAtToday();
        List<Level> levels = dbHelper.getLevels();

        List<AliPayAccount> accounts = getAlipayAccount();
        AliPayAccount aliPayAccount = accounts.get(2);

        List<User> validUser = getValidUser(userList, levels, aliPayAccount);
        log("\n未到时间的用户:"+(userList.size()-validUser.size()+"\n"));

        List<String> errorUsers = getErrorUser();

        for(int i= validUser.size()-1;i>=0;i--){
            User user = validUser.get(i);
            if (errorUsers.contains(user.user)){
                validUser.remove(i);
                log("移除提现为7的用户："+user.user);
            }
        }

        log("\n可用账户数量:" + validUser.size());
        if (validUser.size() > 0) {

//            for (User user:validUser){
//                log(user.latestTxTime + " -- "+user.user+" -- "+decode(user.password)+" -- "+user.mac);
//            }

            User user = validUser.get(0);
            log(user.latestTxTime + " -- " + user.user + " -- " + decode(user.password) + " -- " + user.mac);
            account(aliPayAccount, user);
        }
    }

    public static List<String> getErrorUser(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("77321289");
        list.add("lf667788");
        list.add("dy3232323");
        list.add("2900145554");
        list.add("801229");
        list.add("77321289");

        list.add("Llz823");
        list.add("aaa123456");
        list.add("08177219");
        list.add("5952");
        list.add("800912");
        list.add("187007238");
        list.add("7140");
        list.add("108805");
        list.add("1500501");
        list.add("3045");
        list.add("1518763387");
        list.add("3111");
        list.add("dm9897");

        //33
//        list.add("gps130136");
//        list.add("88888888");
//        list.add("3187775106");
//        list.add("2024485532");
//        list.add("550961449");


        //排除已经用的
        list.add("1396464186");
        list.add("19910217");
//        list.add("1572515687");
        list.add("36865");


        return list;
    }


    private static List<User> getValidUser(List<User> userList, List<Level> levels, AliPayAccount aliPayAccount) {
        List<User> valids = new ArrayList<User>();
        for (User user : userList) {

            String desUser = aliPayAccount.getUser();

            if (desUser != null && desUser.length() != 0 && !user.user.equals(desUser)) {
                continue;
            }

            log("\n用户:" + user.user);
            TxRecord latestRecord = dbHelper.getTxLatestRecord(aliPayAccount.getAccount(), "", user.user);
            long time = 0;
            if (latestRecord != null) {
                log("最近的提现时间：" + latestRecord.txtime);
                user.latestTxTime = latestRecord.txtime;
                Date date = DateUtil.getDate(latestRecord.txtime, DateUtil.FORMAT_YMDHMS);
                time = date.getTime();
            }
            Date date = new Date();
            long curtime = date.getTime();


            for (Level level : levels) {
                if (level.level.equals(user.vip)) {
                    user.vipMoney = level.txje;
                    user.vipMoney=user.vipMoney+".0";
                    user.vipCashTime = level.txspantime;

                    break;
                }
            }
            int cashTime = Integer.parseInt(user.vipCashTime);
//            log("提现间隔:"+(cashTime/60)+"小时");
            int minute = (int) ((curtime - time) / 1000 / 60);
            if (minute > cashTime) {
                log("可以提现");
                valids.add(user);
            } else {
                log("时间未到，不可以提现");
            }
        }

        Collections.sort(valids, new Comparator<User>() {
            public int compare(User u1, User u2) {
                if (u1.latestTxTime == null) {
                    return -1;
                }
                if (u2.latestTxTime == null) {
                    return -1;
                }
                Date date1 = DateUtil.getDate(u1.latestTxTime, DateUtil.FORMAT_YMDHMS);
                Date date2 = DateUtil.getDate(u2.latestTxTime, DateUtil.FORMAT_YMDHMS);
                if (date1.after(date2)) {
                    return 1;
                }
                return -1;
            }
        });

        return valids;
    }



    /**
     * 获取随机mac
     *
     * @return
     */
    public static String getMac() {
        String[] str = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "b", "d", "e", "f"};
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int next = random.nextInt(str.length);
            sb.append(str[next]);
        }
        return sb.toString();

    }


    public static void account(AliPayAccount aliPayAccount, User user) {
        log("账户余额：" + user.jine);
        String mac = aliPayAccount.getMac();
        if (mac == null || mac.length() == 0) {
            mac = getMac();
        }
        actionCashWithdraw(aliPayAccount.getName(), aliPayAccount.getAccount(), user.user, mac, user.vipMoney, user.vip);
    }

    public static void accountFangliang() {
        actionCashWithdraw("辛子财", "13153870185", "fangliang", "38362c3026739cab", "0.5", "0");
    }

    public static void refreshLoginTimeWithFangliang() {
        refreshLoginTime("31847", "fangliang");
    }

    public static void refreshMoneyWithFangliang() {
        //需根据用户vip等级查询viplevel表获取单日最大金额
        refreshMoney("26190", "77321289", "104.35", "132");
    }

    public static void registrUserTest() {
//        registrUser("wangfa","wangfa","2d687ea19e642c3b","fangliang");
//        registrUser("zhangshun","zhangshun","2786dea19e642c3b","fangliang");
//        registrUser("liming","liming","d687ea19e642c3b2","fangliang");
        registrUser("mayanxue", "mayanxue", "687ea19e642c3b2d", "fangliang");
    }

    /**
     * 提现
     * 同一支付宝账号（account）、同一androidId 计算最近的时间
     */
    public static void actionCashWithdraw(String name, String account, String user, String androidId, String money, String vipLevel) {

//        money = "0.5";

        log("系统用户:" + user + "(vip:" + vipLevel + ")," + "提现账户:" + account + "(" + name + "),提现金额:" + money + ",mac:" + androidId);

        //提现前需做时间校验

        String commond = Base64.encodeToString(("5|-|" + user + "|-|" + androidId + "|-|" + Base64.encodeToString(account.getBytes(), 0) + "|-|" + money + "|-|" + NormalUtils.getUTF8XMLString(name) + "|-|" + vipLevel + "|-|app|-|").getBytes(), 0);

        String result = socket(commond);

        if (result == null) {
            log("返回空");
        } else if (result.equals("31")) {
            log("提现请求成功，请关注提现记录状态");
        } else if (result.equals("32")) {
            log("服务器处理提现请求失败，请重试!");
        } else if (result.equals("33")) {
            log("失败：体验期已过，目前只支持VIP(xxx)申请提现，请开通后提现");
        } else if (result.equals("99")) {
            log("你提交的数据中包含系统保留字符，请重试！");
        }

        //可做提现记录的查询
    }

    /**
     * 刷新金额
     *
     * @param id
     * @param user
     * @param money       总money
     * @param dayMaxMoney 日最大金额
     */
    public static void refreshMoney(String id, String user, String money, String dayMaxMoney) {
        log("刷新金额" + user + "(" + id + "):" + money + "(最大日增量:" + dayMaxMoney + ")");
        String str = Base64.encodeToString(("4|-|" + id + "|-|" + user + "|-|" + money + "|-|" + dayMaxMoney + "|-|").getBytes(), 0);
        String result = socket(str);
        if (result.equals("40")) {
            log("成功");
        }
    }

    /**
     * 刷新登录时间
     *
     * @param id   用户id
     * @param user 用户账号
     */
    public static void refreshLoginTime(String id, String user) {
        //Base64.encodeToString(("3|-|" + this.loadData.getId() + "|-|" + this.zhanghao).getBytes(), 0));
        log("刷新登录时间:" + user + "(" + id + ")");
        String str = Base64.encodeToString(("3|-|" + id + "|-|" + user).getBytes(), 0);
        log(str);
        socket(str);
    }


    /**
     * 注册新用户
     *
     * @param user
     * @param pass
     * @param recommond 推荐人
     *                  注册ip由服务器自动识别！！！
     */
    public static void registrUser(String user, String pass, String androidId, String recommond) {
        log("注册新用户:" + user + ",密码:" + pass + ",推荐人:" + recommond);
        String str = Base64.encodeToString(("1|-|" + user + "|-|" + pass + "|-|" + androidId + "|-|" + recommond + "|-|app|-|").getBytes(), 0);
        String result = socket(str);
        if (result.equals("11")) {
            log("注册成功");
        }
    }

    public static String socket(String str) {
        return socket(str, proxyIP, proxyPort);
    }

    public static String socket(String str, String proxyIP, int proxyPort) {
        try {
            Socket socket =new Socket(ip, port);  ;

//            PrintWriter write = new PrintWriter(socket.getOutputStream());
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
            log("socket 写命令");
            write.write(str);
            write.flush();
            log("socket 写入");
            socket.shutdownOutput();


            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            log("读取数据");
            String str2 = br.readLine();
            log("读取数据成功");

//            socket.shutdownInput();

            log("客户端接收服务端发送信息：" + str2);
            socket.close();
            return str2;


        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("txapp",e);
        }

        return null;
    }


    private static void proxy() {
        //https://blog.csdn.net/mebusw/article/details/6372714


        String ip = "51.255.115.224";
        int port = 1080;

        Properties prop = System.getProperties();
        // 设置http访问要使用的代理服务器的地址
        // 设置http访问要使用的代理服务器的端口
//        prop.setProperty("http.proxySet", "true");
//        prop.setProperty("http.proxyHost", ip);
//        prop.setProperty("http.proxyPort", port+"");

        // socks代理服务器的地址与端口
        prop.setProperty("socksProxySet", "true");
        prop.setProperty("socksProxyHost", ip);
        prop.setProperty("socksProxyPort", port + "");
        // 设置登陆到代理服务器的用户名和密码
//        Authenticator.setDefault(new MyAuthenticator("userName", "Password"));
    }

    public static  void log(String str){
        LogUtils.d("txapp",str);
    }


}
