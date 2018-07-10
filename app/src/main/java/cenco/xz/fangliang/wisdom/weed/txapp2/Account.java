package cenco.xz.fangliang.wisdom.weed.txapp2;

/**
 * Created by Administrator on 2018/7/10.
 */

public class Account {
    private String phone;
    private String realname;
    private String username;
    private String registetime;
    private String pass;
    private String mac;

    public Account(String phone, String realname,String pass, String mac) {
        this.phone = phone;
        this.realname = realname;
        this.mac = mac;
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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
