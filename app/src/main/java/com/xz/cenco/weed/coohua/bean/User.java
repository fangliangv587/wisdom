package com.xz.cenco.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/4.
 */

public class User {

    private String androidId;
    private String accountNum;
    private String password;
    private String blueMac;
    private String cpuModel;
    private String imei;
    private String wifiMac;
    private String blackBox;
    private String version;
    private String storageSize;
    private String markId;
    private String screenSize;
    private String model;
    private String comment;

    private boolean isFinish;//一次操作是否完成
    private int index;

    private String baseKey;
    private int coohuaId;

    public User(String androidId, String accountNum, String password, String blueMac, String cpuModel, String imei, String wifiMac, String blackBox, String version, String storageSize, String markId, String screenSize, String model) {
        this.androidId = androidId;
        this.accountNum = accountNum;
        this.password = password;
        this.blueMac = blueMac;
        this.cpuModel = cpuModel;
        this.imei = imei;
        this.wifiMac = wifiMac;
        this.blackBox = blackBox;
        this.version = version;
        this.storageSize = storageSize;
        this.markId = markId;
        this.screenSize = screenSize;
        this.model = model;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBlueMac() {
        return blueMac;
    }

    public void setBlueMac(String blueMac) {
        this.blueMac = blueMac;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public String getBlackBox() {
        return blackBox;
    }

    public void setBlackBox(String blackBox) {
        this.blackBox = blackBox;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBaseKey() {
        return baseKey;
    }

    public void setBaseKey(String baseKey) {
        this.baseKey = baseKey;
    }

    public int getCoohuaId() {
        return coohuaId;
    }

    public void setCoohuaId(int coohuaId) {
        this.coohuaId = coohuaId;
    }

    public String getCoreInfo(){
        return "账号:"+accountNum+",密码:"+password+",baseKey="+baseKey+",coohuaid="+coohuaId;
    }
}
