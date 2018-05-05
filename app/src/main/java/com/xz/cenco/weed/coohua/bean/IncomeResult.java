package com.xz.cenco.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/4.
 */

public class IncomeResult {

    /**
     * message : success
     * result : {"currentCredit":369,"currentGoldCoin":19,"totalCredit":369,"totalGoldCoin":59}
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
        return "Deblock{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }

    public static class ResultBean {
        /**
         * currentCredit : 369
         * currentGoldCoin : 19
         * totalCredit : 369
         * totalGoldCoin : 59
         */

        private int currentCredit;
        private int currentGoldCoin;
        private int totalCredit;
        private int totalGoldCoin;

        public int getCurrentCredit() {
            return currentCredit;
        }

        public void setCurrentCredit(int currentCredit) {
            this.currentCredit = currentCredit;
        }

        public int getCurrentGoldCoin() {
            return currentGoldCoin;
        }

        public void setCurrentGoldCoin(int currentGoldCoin) {
            this.currentGoldCoin = currentGoldCoin;
        }

        public int getTotalCredit() {
            return totalCredit;
        }

        public void setTotalCredit(int totalCredit) {
            this.totalCredit = totalCredit;
        }

        public int getTotalGoldCoin() {
            return totalGoldCoin;
        }

        public void setTotalGoldCoin(int totalGoldCoin) {
            this.totalGoldCoin = totalGoldCoin;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "currentCredit=" + currentCredit +
                    ", currentGoldCoin=" + currentGoldCoin +
                    ", totalCredit=" + totalCredit +
                    ", totalGoldCoin=" + totalGoldCoin +
                    '}';
        }
    }
}
