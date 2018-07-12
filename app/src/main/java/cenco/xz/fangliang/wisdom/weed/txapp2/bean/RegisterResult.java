package cenco.xz.fangliang.wisdom.weed.txapp2.bean;

/**
 * Created by Administrator on 2018/7/11.
 */

public class RegisterResult {

    /**
     * data : 保存成功
     * state : success
     */

    private String data;
    private String state;

    public boolean isSuccess(){
        return state.equals("success");
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
