package cenco.xz.fangliang.wisdom.weed.thumber.bean;

import android.widget.CheckBox;

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
    private String email;
    private String balance;
    private String cookie;
    private String peopleName;
    private int signDays;
    private Map<String,Boolean> result;
    private CheckBox checkBox;
    private boolean isDesposited;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }



    public Account(String username, String password, String peopleName,String bank,String email,boolean isDesposited) {
        this.username = username;
        this.password = password;
        this.bank = bank;
        this.email = email;
        this.peopleName=peopleName;
        this.isDesposited = isDesposited;
        result = new HashMap<String, Boolean>();
    }

    public boolean isDesposited() {
        return isDesposited;
    }

    public void setDesposited(boolean desposited) {
        isDesposited = desposited;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


    public String getIndentify(){
        return username+"_"+peopleName;
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
