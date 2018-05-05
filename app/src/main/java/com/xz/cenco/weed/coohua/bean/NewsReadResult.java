package com.xz.cenco.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class NewsReadResult {

    /**
     * message : success
     * result : {"readDailyCount":2,"readAddRate":100,"goldCoinNum":78,"addGoldCoinNum":5}
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
        return "NewsReadResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }

    public static class ResultBean {
        /**
         * readDailyCount : 2
         * readAddRate : 100
         * goldCoinNum : 78
         * addGoldCoinNum : 5
         */

        private int readDailyCount;
        private int readAddRate;
        private int goldCoinNum;
        private int addGoldCoinNum;

        public int getReadDailyCount() {
            return readDailyCount;
        }

        public void setReadDailyCount(int readDailyCount) {
            this.readDailyCount = readDailyCount;
        }

        public int getReadAddRate() {
            return readAddRate;
        }

        public void setReadAddRate(int readAddRate) {
            this.readAddRate = readAddRate;
        }

        public int getGoldCoinNum() {
            return goldCoinNum;
        }

        public void setGoldCoinNum(int goldCoinNum) {
            this.goldCoinNum = goldCoinNum;
        }

        public int getAddGoldCoinNum() {
            return addGoldCoinNum;
        }

        public void setAddGoldCoinNum(int addGoldCoinNum) {
            this.addGoldCoinNum = addGoldCoinNum;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "readDailyCount=" + readDailyCount +
                    ", readAddRate=" + readAddRate +
                    ", goldCoinNum=" + goldCoinNum +
                    ", addGoldCoinNum=" + addGoldCoinNum +
                    '}';
        }
    }
}
