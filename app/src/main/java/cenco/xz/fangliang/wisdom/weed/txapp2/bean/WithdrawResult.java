package cenco.xz.fangliang.wisdom.weed.txapp2.bean;

/**
 * Created by Administrator on 2018/7/11.
 */

public class WithdrawResult {

    /**
     * state : success
     * successMessage : 提现成功
     */

    private String state;
    private String successMessage;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
