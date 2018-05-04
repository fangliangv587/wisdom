package com.xz.cenco.weed.coohua.bean;

/**
 * Created by Administrator on 2018/5/4.
 */

public class LoginResult {

    /**
     * message : success
     * result : {"phoneNo":"15588591960","birthYear":1989,"sex":1,"ticket":"a8c78c26bfd022ac32b56dbf164f69f3","imei":"dce5cf6a3e87078888d8332796c97e36-U","coohuaId":8965486}
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
        return "LoginResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }

    public static class ResultBean {
        /**
         * phoneNo : 15588591960
         * birthYear : 1989
         * sex : 1
         * ticket : a8c78c26bfd022ac32b56dbf164f69f3
         * imei : dce5cf6a3e87078888d8332796c97e36-U
         * coohuaId : 8965486
         */

        private String phoneNo;
        private int birthYear;
        private int sex;
        private String ticket;
        private String imei;
        private int coohuaId;

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public int getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(int birthYear) {
            this.birthYear = birthYear;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public int getCoohuaId() {
            return coohuaId;
        }

        public void setCoohuaId(int coohuaId) {
            this.coohuaId = coohuaId;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "phoneNo='" + phoneNo + '\'' +
                    ", birthYear=" + birthYear +
                    ", sex=" + sex +
                    ", ticket='" + ticket + '\'' +
                    ", imei='" + imei + '\'' +
                    ", coohuaId=" + coohuaId +
                    '}';
        }
    }
}
