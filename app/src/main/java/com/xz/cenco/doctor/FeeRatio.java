package com.xz.cenco.doctor;

/**
 * Created by Administrator on 2018/3/28.
 */

public class FeeRatio {

    private int startAge;
    private int stopAge;
    private int priceWithHealth;
    private int priceWithoutHealth;

    public FeeRatio(int startAge, int stopAge, int priceWithHealth, int priceWithoutHealth) {
        this.startAge = startAge;
        this.stopAge = stopAge;
        this.priceWithHealth = priceWithHealth;
        this.priceWithoutHealth = priceWithoutHealth;
    }


    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getStopAge() {
        return stopAge;
    }

    public void setStopAge(int stopAge) {
        this.stopAge = stopAge;
    }

    public int getPriceWithHealth() {
        return priceWithHealth;
    }

    public void setPriceWithHealth(int priceWithHealth) {
        this.priceWithHealth = priceWithHealth;
    }

    public int getPriceWithoutHealth() {
        return priceWithoutHealth;
    }

    public void setPriceWithoutHealth(int priceWithoutHealth) {
        this.priceWithoutHealth = priceWithoutHealth;
    }
}
