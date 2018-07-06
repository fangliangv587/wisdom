package cenco.xz.fangliang.wisdom.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/4.
 */

public class SignResult {

    /**
     * message : success
     * result : {"amount":0,"days":0}
     * status : 200
     */

    private String message;
    private ResultBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SignResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }

    public static class ResultBean {
        /**
         * amount : 0
         * days : 0
         */

        private int amount;
        private int days;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "amount=" + amount +
                    ", days=" + days +
                    '}';
        }
    }
}
