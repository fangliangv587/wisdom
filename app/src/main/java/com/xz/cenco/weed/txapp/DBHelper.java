package com.xz.cenco.weed.txapp;


import android.util.Base64;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.log.LogUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * public void getInstance() {
 * if(connection == null) {
 * synchronized(Connection.class) {
 * if(connection == null) {
 * (new Runnable(this) {
 * <p>
 * public void run() {
 * connection = MySqlUtils.openConnection("jdbc:mysql://47.104.122.103/wakuang", "wakuangb", "xcvlkbjdlf@dcl4D");
 * Message message = new Message();
 * message.what = 0x1;
 * EventBus.getDefault().post(message);
 * }
 * }).start();
 * }
 * }
 */
public class DBHelper {
    public static final String url = "jdbc:mysql://47.104.122.103/wakuang";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "wakuangb";
    public static final String password = "xcvlkbjdlf@dcl4D";

    public Connection conn = null;
    public PreparedStatement pst = null;
    public Statement stmt = null;

    public static void main(String[] args) {
        DBHelper helper = new DBHelper();

        helper.getNotCashUserAtToday();

//        helper.getUser("cenco0415");

//        helper.getTxLatestRecord("15588591960","");

//        helper.getLevels();
    }



    public List<TxRecord> getTxRecordList() {
        return getTxRecordList(2);
    }
    public List<TxRecord> getTxRecordList(int vip) {
        LogUtils.i("txapp","获取vip="+vip+"的每个用户的最新提现（中）成功记录");
        String sql = "select * from (select * from txjl WHERE vip = "+vip+" AND (txend=1 OR txend =2)order by txtime desc) t group by user";
        return getTxRecordList(sql);
    }

    public List<TxRecord> getTxRecordListByAccountName(String accountName) {
        String sql = "select * from txjl WHERE txxm = '"+accountName+"' order by txtime desc";
        return getTxRecordList(sql);
    }
    public List<TxRecord> getTxRecordListByUser(String user) {
        String sql = "select * from txjl WHERE user = '"+user+"' order by txtime desc";
        return getTxRecordList(sql);
    }
    public List<TxRecord> getTxRecordList(String sql) {
        log("获取体现记录:" + sql);
        try {
            List<TxRecord> list = new ArrayList<TxRecord>();

            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

            //从结果集中提取数据
            while (rs.next()) {
                TxRecord record = new TxRecord();
                record.id = rs.getInt("id");
                record.txend = rs.getInt("txend");
                record.user = rs.getString("user");
                record.txyhk = rs.getString("txyhk");
                record.txzh = rs.getString("txzh");
                record.txje = rs.getString("txje");
                record.txxm = rs.getString("txxm");
                record.vip = rs.getString("vip");
                record.mac = rs.getString("mac");
                record.ip = rs.getString("ip");
                record.txtime = rs.getString("txtime");
                record.endtime = rs.getString("endtime");
                record.app = rs.getString("app");

                list.add(record);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public DBHelper() {
        try {
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接
            stmt = conn.createStatement();
            LogUtils.d("conn:" + conn);
//            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            LogUtils.e(e);
            e.printStackTrace();
        }
    }


    public List<Level> getLevels() {
        String sql = "SELECT * FROM viplevel";
        try {
            List<Level> list = new ArrayList<Level>();

            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

            //从结果集中提取数据
            while (rs.next()) {
                Level level = new Level();
                level.id = rs.getString("id");
                level.level = rs.getString("level");
                level.tjrnum = rs.getString("tjrnum");
                level.tgfennum = rs.getString("tgfennum");
                level.minje = rs.getString("minje");
                level.maxje = rs.getString("maxje");
                level.dayje = rs.getString("dayje");
                level.txje = rs.getString("txje");
                level.txspantime = rs.getString("txspantime");
                level.mintasktime = rs.getString("mintasktime");
                level.maxtasktime = rs.getString("maxtasktime");
                level.mintaskingtime = rs.getString("mintaskingtime");
                level.maxtaskingtime = rs.getString("maxtaskingtime");
                level.txtgfennum = rs.getString("txtgfennum");
                level.cuetxt = rs.getString("cuetxt");

                list.add(level);
            }
//            log("查询到的等级数量:"+list.size());
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取最近的体现记录
     *
     * @param account 支付宝账号
     * @param mac
     * @return
     */
    public TxRecord getTxLatestRecord(String account, String mac, String user) {
        String sql = "select * from txjl where (txzh='" + Base64.encodeToString(account.getBytes(), 0).trim() + " ' or mac='" + mac + "' or user='" + user + "') and (txend='2' or txend='1') order by id desc";
        log("获取最近的体现记录:" + sql);
        try {
            List<TxRecord> list = new ArrayList<TxRecord>();

            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

            //从结果集中提取数据
            while (rs.next()) {
                TxRecord record = new TxRecord();
                record.id = rs.getInt("id");
                record.txend = rs.getInt("txend");
                record.user = rs.getString("user");
                record.txyhk = rs.getString("txyhk");
                record.txzh = rs.getString("txzh");
                record.txje = rs.getString("txje");
                record.txxm = rs.getString("txxm");
                record.vip = rs.getString("vip");
                record.mac = rs.getString("mac");
                record.ip = rs.getString("ip");
                record.txtime = rs.getString("txtime");
                record.endtime = rs.getString("endtime");
                record.app = rs.getString("app");

                list.add(record);
            }
//            log("查询到的提现记录数量:"+list.size());

            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUser(String name) {
        List<User> users = getUsers(name);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }


    public List<User> getNotCashUserAtToday() {
        return getNotCashUserAtDay("2", DateUtil.getDateString(DateUtil.FORMAT_YMD));
    }


    public List<User> getNotCashUserAtDay(String vip, String day) {
        try {
            List<String> cashUsers = new ArrayList<String>();
            String sql1 = "SELECT DISTINCT user from txjl WHERE vip = " + vip + " and  endtime  like '" + day + "%'  AND ( txend = 2 OR txend = 1)";
            ResultSet rs = null;
            rs = stmt.executeQuery(sql1);
            while (rs.next()) {
                String user = rs.getString("user");
//                log(user);
                cashUsers.add(user);
            }
            log("已提现等级为" + vip + "的用户数量:" + cashUsers.size());
            List<User> users = getUsers();
            for (int i = users.size() - 1; i >= 0; i--) {
                User user = users.get(i);


                if (!user.vip.equals(vip)) {
                    users.remove(user);
//                    log("移除等级不为"+vip+"的用户");
                    continue;
                }

                for (String name : cashUsers) {
                    if (user.user.equals(name)) {
                        users.remove(user);
                        break;
//                        log("移除已经提现的用户："+name);
                    }
                }
            }
            log("未提现等级为" + vip + "的用户：" + users.size());
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getUsers() {
        List<User> users = getUsers(null);
        return users;
    }

    public List<User> getUsersByVip(int vip){
        String sql = "select * from userlist where vip = "+vip;
        return  getUsersBySql(sql);
    }

    public List<User> getUsersBySql(String sql){
        try {

            List<User> list = new ArrayList<User>();

            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

            //从结果集中提取数据
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.user = rs.getString("user");//varchar
                user.password = rs.getString("password");//varchar
                user.status = rs.getString("status");//varchar
                user.tjr = rs.getString("tjr");//varchar
                user.tjrjsstatus = rs.getString("tjrjsstatus");
                user.jine = rs.getFloat("jine");//decimal
                user.txjl = rs.getString("txjl");//varchar
                user.txnumber = rs.getInt("txnumber");//int
                user.vip = rs.getString("vip");//varchar
                user.viptime = rs.getString("viptime");//varchar
                user.tgjifen = rs.getInt("tgjifen");//int
                user.tgip = rs.getString("tgip");//varchar
                user.mac = rs.getString("mac");//varchar
                user.regip = rs.getString("regip");//varchar
                user.logintime = rs.getString("logintime");//varchar
                user.regtime = rs.getString("regtime");//varchar
                user.todayjine = rs.getString("todayjine");//varchar
                user.dayendtime = rs.getString("dayendtime");//varchar
                user.app = rs.getString("app");//varchar
//                log(user.toString());
                list.add(user);

            }
            log("查询到的用户数量:" + list.size());

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getUsers(String username) {
        try {

            List<User> list = new ArrayList<User>();

            String sql = "select * from userlist";
            if (username != null) {
                sql = sql + " where user = '" + username + "'";
            }
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

            //从结果集中提取数据
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.user = rs.getString("user");//varchar
                user.password = rs.getString("password");//varchar
                user.status = rs.getString("status");//varchar
                user.tjr = rs.getString("tjr");//varchar
                user.tjrjsstatus = rs.getString("tjrjsstatus");
                user.jine = rs.getFloat("jine");//decimal
                user.txjl = rs.getString("txjl");//varchar
                user.txnumber = rs.getInt("txnumber");//int
                user.vip = rs.getString("vip");//varchar
                user.viptime = rs.getString("viptime");//varchar
                user.tgjifen = rs.getInt("tgjifen");//int
                user.tgip = rs.getString("tgip");//varchar
                user.mac = rs.getString("mac");//varchar
                user.regip = rs.getString("regip");//varchar
                user.logintime = rs.getString("logintime");//varchar
                user.regtime = rs.getString("regtime");//varchar
                user.todayjine = rs.getString("todayjine");//varchar
                user.dayendtime = rs.getString("dayendtime");//varchar
                user.app = rs.getString("app");//varchar
//                log(user.toString());
                list.add(user);

            }
            log("查询到的用户数量:" + list.size());

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void log(String str) {
        LogUtils.d("txapp", str);
    }

    public void close() {
        try {
            if (this.conn!=null){
                this.conn.close();
            }

            if (this.pst!=null){
                this.pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}  