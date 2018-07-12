package cenco.xz.fangliang.wisdom.weed.txapp2.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/10.
 */

public class Account implements Serializable{
    private boolean isRegister;
    private String phone;
    private String phoneName;
    private String username;
    private String registetime;
    private String pass;
    private String mac;
    private String sex;
    private String aliPhone;
    private String aliName;
    private int id;
    private String token;
    private String withdrawStatus;
    private String txmoney;

    public Account(String phone, String phoneName,String pass, String mac,String sex,String aliPhone,String aliName,boolean isRegister) {
        this.phone = phone;
        this.phoneName = phoneName;
        this.mac = mac;
        this.pass = pass;
        this.sex = sex;
        this.isRegister = isRegister;
        this.aliName = aliName;
        this.aliPhone = aliPhone;
    }

    public String getTxmoney() {
        return txmoney;
    }

    public void setTxmoney(String txmoney) {
        this.txmoney = txmoney;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAliPhone() {
        return aliPhone;
    }

    public void setAliPhone(String aliPhone) {
        this.aliPhone = aliPhone;
    }

    public String getAliName() {
        return aliName;
    }

    public void setAliName(String aliName) {
        this.aliName = aliName;
    }

    public String getSexTag(){
        if (sex.equals("男")){
            return "sex_1";
        }
        return "sex_2";
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIndentify(){
        return aliName +"_"+aliPhone;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegistetime() {
        return registetime;
    }

    public void setRegistetime(String registetime) {
        this.registetime = registetime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
