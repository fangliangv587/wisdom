package com.xz.cenco.doctor;

import com.cenco.lib.common.DateUtil;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/29.
 */

public class Person {

    public static final int MAX = 80;

    private int curAge;
    private int maxAge;
    private boolean isDoctor;
    private String name;
    private int birthYear;
    private int destYear;
    private int fee;

    public int getDestYear() {
        return destYear;
    }

    public void setDestYear(int destYear) {
        this.destYear = destYear;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
        int year = DateUtil.getYear(new Date());
        this.curAge = year - birthYear;
    }

    public Person(String name, int birthYear,boolean isDoctor) {
        this.isDoctor = isDoctor;
        this.name = name;
        this.birthYear = birthYear;
        int year = DateUtil.getYear(new Date());
        this.curAge = year - birthYear;
    }

    public int getCurAge() {
        return curAge;
    }

    public void setCurAge(int curAge) {
        this.curAge = curAge;
    }

    public int getMaxAge() {
        if (maxAge == 0){
            return MAX;
        }
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }


    @Override
    public String toString() {
        return this.name+"[出生年份:"+this.birthYear+","+(isDoctor()?"有":"无")+"医保,在"+destYear+"年(即"+(destYear-birthYear)+"周岁)需要缴纳"+fee+"元保费]";
    }
}
