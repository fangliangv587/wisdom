package com.xz.cenco.weed.txapp;

/**
 * Created by Administrator on 2018/6/11.
 */
public class AliPayAccount {
    private String mac;
    private String account;
    private String name;
    private String user;

    public AliPayAccount(String account, String name,String user,String mac) {
        this.account = account;
        this.name = name;
        this.user = user;
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}