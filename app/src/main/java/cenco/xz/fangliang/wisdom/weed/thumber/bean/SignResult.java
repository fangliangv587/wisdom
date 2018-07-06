package cenco.xz.fangliang.wisdom.weed.thumber.bean;

/**
 * Created by Administrator on 2018/4/27.
 */
public class SignResult {
    private boolean Success;
    private String Msg;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @Override
    public String toString() {
        return "SignResult{" +
                "Success=" + Success +
                ", Msg='" + Msg + '\'' +
                '}';
    }
}
