package com.xz.cenco.weed.txapp;

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



    @Override
    public String toString() {
        return "账号:"+user+",密码:"+password+",mac:"+mac+",注册ip:"+regip;
    }
}
