package cenco.xz.fangliang.wisdom.weed.aiaixg;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/28.
 */
public class User {
    private String name;
    private String pass;
    private String dosubmit="1";
    private String cookietime="2592000";
    private Map<String,Boolean> result;

    public User() {
    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        result = new HashMap<String, Boolean>();
    }

    public Map<String, Boolean> getResult() {
        return result;
    }

    public void putResult(String date, boolean isok){
        result.put(date,isok);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDosubmit() {
        return dosubmit;
    }

    public void setDosubmit(String dosubmit) {
        this.dosubmit = dosubmit;
    }

    public String getCookietime() {
        return cookietime;
    }

    public void setCookietime(String cookietime) {
        this.cookietime = cookietime;
    }
}
