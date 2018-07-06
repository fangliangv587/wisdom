package cenco.xz.fangliang.wisdom.weed.txapp;

/**
 * Created by Administrator on 2018/7/2.
 */

public class AliRecord {
    private String date;
    private String txtime;
    private double money;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxtime() {
        return txtime;
    }

    public void setTxtime(String txtime) {
        this.txtime = txtime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
