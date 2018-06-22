package com.xz.cenco.weed.thumber.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */
public class RecordNumResult {

    /**
     * success : true
     * list : [{"id":"243995","issuenum":"201806150853","lotterynum":"2","starttime":"1529043120","endtime":"1529043180","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"351"},{"id":"243994","issuenum":"201806150852","lotterynum":"1","starttime":"1529043060","endtime":"1529043120","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"101"},{"id":"243993","issuenum":"201806150851","lotterynum":"2","starttime":"1529043000","endtime":"1529043060","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"31"},{"id":"243992","issuenum":"201806150850","lotterynum":"2","starttime":"1529042940","endtime":"1529043000","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"706"},{"id":"243991","issuenum":"201806150849","lotterynum":"2","starttime":"1529042880","endtime":"1529042940","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"456"},{"id":"243990","issuenum":"201806150848","lotterynum":"2","starttime":"1529042820","endtime":"1529042880","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"199"},{"id":"243989","issuenum":"201806150847","lotterynum":"2","starttime":"1529042760","endtime":"1529042820","lotterytime":null,"status":"1","createtime":null,"edittime":null,"kjhm":"17"}]
     */

    private boolean success;
    private List<ListBean> list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 243995
         * issuenum : 201806150853
         * lotterynum : 2
         * starttime : 1529043120
         * endtime : 1529043180
         * lotterytime : null
         * status : 1
         * createtime : null
         * edittime : null
         * kjhm : 351
         */

        private String id;
        private String issuenum;
        private String lotterynum;
        private String starttime;
        private String endtime;
        private Object lotterytime;
        private String status;
        private Object createtime;
        private Object edittime;
        private String kjhm;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIssuenum() {
            return issuenum;
        }

        public void setIssuenum(String issuenum) {
            this.issuenum = issuenum;
        }

        public String getLotterynum() {
            return lotterynum;
        }

        public void setLotterynum(String lotterynum) {
            this.lotterynum = lotterynum;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public Object getLotterytime() {
            return lotterytime;
        }

        public void setLotterytime(Object lotterytime) {
            this.lotterytime = lotterytime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }

        public Object getEdittime() {
            return edittime;
        }

        public void setEdittime(Object edittime) {
            this.edittime = edittime;
        }

        public String getKjhm() {
            return kjhm;
        }

        public void setKjhm(String kjhm) {
            this.kjhm = kjhm;
        }
    }
}
