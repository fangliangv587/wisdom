package cenco.xz.fangliang.wisdom.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/4.
 */

public class OpenBoxResult {

    /**
     * message : success
     * result : {"seconds":10800,"goldCoinNum":5,"boxState":1}
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
        return "OpenBoxResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }

    public static class ResultBean {
        /**
         * seconds : 10800
         * goldCoinNum : 5
         * boxState : 1
         */

        private int seconds;
        private int goldCoinNum;
        private int boxState;

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public int getGoldCoinNum() {
            return goldCoinNum;
        }

        public void setGoldCoinNum(int goldCoinNum) {
            this.goldCoinNum = goldCoinNum;
        }

        public int getBoxState() {
            return boxState;
        }

        public void setBoxState(int boxState) {
            this.boxState = boxState;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "seconds=" + seconds +
                    ", goldCoinNum=" + goldCoinNum +
                    ", boxState=" + boxState +
                    '}';
        }
    }
}
