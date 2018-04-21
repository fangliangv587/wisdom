package com.xz.cenco.test.baidu_police_vertify;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class BMatchResult {

    /**
     * result : [{"index_i":"0","index_j":"1","score":74.972137451172}]
     * result_num : 1
     * ext_info : {"faceliveness":"0,3.5245545859652E-7"}
     * log_id : 4073243534042111
     */

    private int result_num;
    private ExtInfoBean ext_info;
    private long log_id;
    private List<ResultBean> result;

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public ExtInfoBean getExt_info() {
        return ext_info;
    }

    public void setExt_info(ExtInfoBean ext_info) {
        this.ext_info = ext_info;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ExtInfoBean {
        /**
         * faceliveness : 0,3.5245545859652E-7
         */

        private String faceliveness;

        public String getFaceliveness() {
            return faceliveness;
        }

        public void setFaceliveness(String faceliveness) {
            this.faceliveness = faceliveness;
        }
    }

    public static class ResultBean {
        /**
         * index_i : 0
         * index_j : 1
         * score : 74.972137451172
         */

        private String index_i;
        private String index_j;
        private double score;

        public String getIndex_i() {
            return index_i;
        }

        public void setIndex_i(String index_i) {
            this.index_i = index_i;
        }

        public String getIndex_j() {
            return index_j;
        }

        public void setIndex_j(String index_j) {
            this.index_j = index_j;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
