package cenco.xz.fangliang.wisdom.weed.thumber.bean;

/**
 * Created by Administrator on 2018/4/27.
 */
public class SignUserResult {

    /**
     * Success : true
     * Msg : 亲，您今天已签过到了，手下留情！
     * Obj : {"id":1,"oneMoney":0.1,"twoMoney":0.2,"threeMoney":0.3,"fourMoney":0.4,"fiveMoney":0.5,"sixMoney":0.6,"sevenMoney":0.7,"user_CheckNum":3,"IsNo":1}
     */

    private boolean Success;
    private String Msg;
    private ObjBean Obj;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public ObjBean getObj() {
        return Obj;
    }

    public void setObj(ObjBean Obj) {
        this.Obj = Obj;
    }

    @Override
    public String toString() {
        return "SignUserResult{" +
                "Success=" + Success +
                ", Msg='" + Msg + '\'' +
                ", Obj=" + Obj +
                '}';
    }

    public static class ObjBean {
        /**
         * id : 1
         * oneMoney : 0.1
         * twoMoney : 0.2
         * threeMoney : 0.3
         * fourMoney : 0.4
         * fiveMoney : 0.5
         * sixMoney : 0.6
         * sevenMoney : 0.7
         * user_CheckNum : 3
         * IsNo : 1
         */

        private int id;
        private double oneMoney;
        private double twoMoney;
        private double threeMoney;
        private double fourMoney;
        private double fiveMoney;
        private double sixMoney;
        private double sevenMoney;
        private int user_CheckNum;
        private int IsNo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getOneMoney() {
            return oneMoney;
        }

        public void setOneMoney(double oneMoney) {
            this.oneMoney = oneMoney;
        }

        public double getTwoMoney() {
            return twoMoney;
        }

        public void setTwoMoney(double twoMoney) {
            this.twoMoney = twoMoney;
        }

        public double getThreeMoney() {
            return threeMoney;
        }

        public void setThreeMoney(double threeMoney) {
            this.threeMoney = threeMoney;
        }

        public double getFourMoney() {
            return fourMoney;
        }

        public void setFourMoney(double fourMoney) {
            this.fourMoney = fourMoney;
        }

        public double getFiveMoney() {
            return fiveMoney;
        }

        public void setFiveMoney(double fiveMoney) {
            this.fiveMoney = fiveMoney;
        }

        public double getSixMoney() {
            return sixMoney;
        }

        public void setSixMoney(double sixMoney) {
            this.sixMoney = sixMoney;
        }

        public double getSevenMoney() {
            return sevenMoney;
        }

        public void setSevenMoney(double sevenMoney) {
            this.sevenMoney = sevenMoney;
        }

        public int getUser_CheckNum() {
            return user_CheckNum;
        }

        public void setUser_CheckNum(int user_CheckNum) {
            this.user_CheckNum = user_CheckNum;
        }

        public int getIsNo() {
            return IsNo;
        }

        public void setIsNo(int IsNo) {
            this.IsNo = IsNo;
        }

        @Override
        public String toString() {
            return "ObjBean{" +
                    "id=" + id +
                    ", oneMoney=" + oneMoney +
                    ", twoMoney=" + twoMoney +
                    ", threeMoney=" + threeMoney +
                    ", fourMoney=" + fourMoney +
                    ", fiveMoney=" + fiveMoney +
                    ", sixMoney=" + sixMoney +
                    ", sevenMoney=" + sevenMoney +
                    ", user_CheckNum=" + user_CheckNum +
                    ", IsNo=" + IsNo +
                    '}';
        }
    }
}
