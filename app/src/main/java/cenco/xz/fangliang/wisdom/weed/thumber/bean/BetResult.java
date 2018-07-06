package cenco.xz.fangliang.wisdom.weed.thumber.bean;

/**
 * Created by Administrator on 2018/6/15.
 */
public class BetResult {

    /**
     * success : true
     * msg : 投注成功！
     * list : {"type":1,"amount":0,"info":"201806150917个人返水1","time":1529046962,"uid":"4403","zBet":"1.00","fBet":"0.00"}
     */

    private boolean success;
    private String msg;
    private ListBean list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * type : 1
         * amount : 0
         * info : 201806150917个人返水1
         * time : 1529046962
         * uid : 4403
         * zBet : 1.00
         * fBet : 0.00
         */

        private int type;
        private int amount;
        private String info;
        private int time;
        private String uid;
        private String zBet;
        private String fBet;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getZBet() {
            return zBet;
        }

        public void setZBet(String zBet) {
            this.zBet = zBet;
        }

        public String getFBet() {
            return fBet;
        }

        public void setFBet(String fBet) {
            this.fBet = fBet;
        }
    }
}
