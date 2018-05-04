package com.xz.cenco.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/4.
 */

public class HomeResult {

    /**
     * message : success
     * result : {"homeDailyCount":1,"goldCoinNum":37,"addGoldCoinNum":3,"homeHourCount":1}
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
        return "HomeResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }

    public static class ResultBean {
        /**
         * homeDailyCount : 1
         * goldCoinNum : 37
         * addGoldCoinNum : 3
         * homeHourCount : 1
         */

        private int homeDailyCount;
        private int goldCoinNum;
        private int addGoldCoinNum;
        private int homeHourCount;

        public int getHomeDailyCount() {
            return homeDailyCount;
        }

        public void setHomeDailyCount(int homeDailyCount) {
            this.homeDailyCount = homeDailyCount;
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

        public int getHomeHourCount() {
            return homeHourCount;
        }

        public void setHomeHourCount(int homeHourCount) {
            this.homeHourCount = homeHourCount;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "homeDailyCount=" + homeDailyCount +
                    ", goldCoinNum=" + goldCoinNum +
                    ", addGoldCoinNum=" + addGoldCoinNum +
                    ", homeHourCount=" + homeHourCount +
                    '}';
        }
    }
}
