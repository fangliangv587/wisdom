package com.xz.cenco.weed.txapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/9 0009.
 */
public class User {
    public int id;//int
    public String user;//varchar
    public String password;//varchar
    public String status;//无效
    public String tjr;//推广注册人数量
    public String tjrjsstatus;
    public float jine;//账户金额
    public String txjl;//varchar
    public int txnumber;//int
    public String vip;//varchar
    public TxRecord txRecord;

    public String viptime;//varchar
    public int tgjifen;//推广查看人数量
    public String tgip;//varchar
    public String mac;//varchar
    public String regip;//varchar
    public String logintime;//varchar
    public String regtime;//varchar
    public String todayjine;//varchar
    public String dayendtime;//varchar
    public String app;//varchar

    public String vipMoney;
    public String vipCashTime;
    public String latestTxTime;

    public List<TxRecord> recordList;
    public Level level;
    public List<String> names = new ArrayList<>();



    @Override
    public String toString() {
        return "账号:"+user+",密码:"+getPassword(password);
    }

    public static String getPassword(String pass) {

        byte[] decode = Base64.decode(pass, 0);
        String ds = new String(decode);
        return ds;
    }

    public String getName(){
        StringBuffer sb = new StringBuffer();
        for (String str :names){
            sb.append(str).append("  ");
        }
        return sb.toString();
    }
}
