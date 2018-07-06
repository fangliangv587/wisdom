package cenco.xz.fangliang.wisdom.weed.thumber.bean;

/**
 * Created by Administrator on 2018/4/27.
 */
public class LoginResult {

    private String Msg;
    private int Success;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getSuccess() {
        return Success;
    }

    public void setSuccess(int success) {
        Success = success;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "Msg='" + Msg + '\'' +
                ", Success=" + Success +
                '}';
    }
}
