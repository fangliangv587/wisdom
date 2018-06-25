package com.xz.cenco.weed.qutoutiao;

/**
 * Created by Administrator on 2018/6/25.
 */

public class Result {


    /**
     * code : 0
     * message : 成功
     * showErr : 0
     * currentTime : 1529908648
     * data : {"curr_task":{"amount":8},"read_conf":{"single_limit":1,"read_rate":40,"read_random":45,"coins_h5_uri":"https://h5ssl.1sapp.com/qukan_new2/dest/read_timer/inapp/read_timer/index.html","onehour_reward":0},"next_task":{"id":14,"amount":8,"time":30,"node":5}}
     */

    private int code;
    private String message;
    private int showErr;
    private int currentTime;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getShowErr() {
        return showErr;
    }

    public void setShowErr(int showErr) {
        this.showErr = showErr;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * curr_task : {"amount":8}
         * read_conf : {"single_limit":1,"read_rate":40,"read_random":45,"coins_h5_uri":"https://h5ssl.1sapp.com/qukan_new2/dest/read_timer/inapp/read_timer/index.html","onehour_reward":0}
         * next_task : {"id":14,"amount":8,"time":30,"node":5}
         */

        private CurrTaskBean curr_task;
        private ReadConfBean read_conf;
        private NextTaskBean next_task;

        public CurrTaskBean getCurr_task() {
            return curr_task;
        }

        public void setCurr_task(CurrTaskBean curr_task) {
            this.curr_task = curr_task;
        }

        public ReadConfBean getRead_conf() {
            return read_conf;
        }

        public void setRead_conf(ReadConfBean read_conf) {
            this.read_conf = read_conf;
        }

        public NextTaskBean getNext_task() {
            return next_task;
        }

        public void setNext_task(NextTaskBean next_task) {
            this.next_task = next_task;
        }

        public static class CurrTaskBean {
            /**
             * amount : 8
             */

            private int amount;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }
        }

        public static class ReadConfBean {
            /**
             * single_limit : 1
             * read_rate : 40
             * read_random : 45
             * coins_h5_uri : https://h5ssl.1sapp.com/qukan_new2/dest/read_timer/inapp/read_timer/index.html
             * onehour_reward : 0
             */

            private int single_limit;
            private int read_rate;
            private int read_random;
            private String coins_h5_uri;
            private int onehour_reward;

            public int getSingle_limit() {
                return single_limit;
            }

            public void setSingle_limit(int single_limit) {
                this.single_limit = single_limit;
            }

            public int getRead_rate() {
                return read_rate;
            }

            public void setRead_rate(int read_rate) {
                this.read_rate = read_rate;
            }

            public int getRead_random() {
                return read_random;
            }

            public void setRead_random(int read_random) {
                this.read_random = read_random;
            }

            public String getCoins_h5_uri() {
                return coins_h5_uri;
            }

            public void setCoins_h5_uri(String coins_h5_uri) {
                this.coins_h5_uri = coins_h5_uri;
            }

            public int getOnehour_reward() {
                return onehour_reward;
            }

            public void setOnehour_reward(int onehour_reward) {
                this.onehour_reward = onehour_reward;
            }
        }

        public static class NextTaskBean {
            /**
             * id : 14
             * amount : 8
             * time : 30
             * node : 5
             */

            private int id;
            private int amount;
            private int time;
            private int node;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getNode() {
                return node;
            }

            public void setNode(int node) {
                this.node = node;
            }
        }
    }
}
