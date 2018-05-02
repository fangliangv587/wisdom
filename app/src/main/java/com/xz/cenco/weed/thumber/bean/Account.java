package com.xz.cenco.weed.thumber.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/27.
 */
public class Account {

    private String username;
    private String password;
    private String bank;
    private String balance;
    private int signDays;
    private Map<String,Boolean> result;

    public Account(String username, String password,String bank) {
        this.username = username;
        this.password = password;
        this.bank = bank;
        result = new HashMap<String, Boolean>();
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Boolean> getResult() {
        return result;
    }

    public int getSignDays() {
        return signDays;
    }

    public void setSignDays(int signDays) {
        this.signDays = signDays;
    }

    public void putResult(String date, boolean isok){
        result.put(date,isok);
    }


    @Override
    public String toString() {

        String mes =username+":"+balance;

        Iterator<String> iterator = result.keySet().iterator();
        StringBuffer sb =  new StringBuffer();
        sb.append("-----------------"+mes+"--------------------");
        while (iterator.hasNext()){
            String key = iterator.next();
            boolean value = result.get(key);
            sb.append("\r\n");
            sb.append("日期:"+key+",是否签到:"+value);
        }


        return sb.toString();
    }
}
