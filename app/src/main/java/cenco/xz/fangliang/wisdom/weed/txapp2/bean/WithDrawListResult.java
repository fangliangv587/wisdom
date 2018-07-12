package cenco.xz.fangliang.wisdom.weed.txapp2.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/11.
 */

public class WithDrawListResult {

    /**
     * status : true
     * data : [{"createDate":"2018-07-11 11:19:04","createDateBegin":null,"createDateEnd":null,"createUser":1988,"createUserDept":1003,"createUserOrg":null,"deleteFlag":0,"extDate1":null,"extDate1Begin":null,"extDate1End":null,"extDate2":null,"extDate2Begin":null,"extDate2End":null,"extNum1":null,"extNum2":null,"extNum3":null,"dataAccessDynamicSQL":null,"extStr1":"15588591960","extStr2":null,"extStr3":"1","extStr4":null,"extStr5":null,"id":2699,"modifyDate":"2018-07-11 11:19:04","modifyDateBegin":null,"modifyDateEnd":null,"modifyDateNew":null,"modifyUser":1988,"weight":null,"primaryKeys":null,"billNumber":"15312791443687604","alipayAccount":"15588591960","alipayName":"辛忠","billDescribe":null,"billStatus":"1","billDate":1531279144000,"userId":1988,"withdrawNumber":"0.5","withdrawDate":"2018-07-11 11:19:04","remark":"佣金提现","orderBy":null},{"createDate":"2018-07-10 15:34:43","createDateBegin":null,"createDateEnd":null,"createUser":1988,"createUserDept":1003,"createUserOrg":null,"deleteFlag":0,"extDate1":null,"extDate1Begin":null,"extDate1End":null,"extDate2":null,"extDate2Begin":null,"extDate2End":null,"extNum1":null,"extNum2":null,"extNum3":null,"dataAccessDynamicSQL":null,"extStr1":"15588591960","extStr2":null,"extStr3":"1","extStr4":null,"extStr5":null,"id":1988,"modifyDate":"2018-07-10 15:34:43","modifyDateBegin":null,"modifyDateEnd":null,"modifyDateNew":null,"modifyUser":1988,"weight":null,"primaryKeys":null,"billNumber":"15312080835333130","alipayAccount":"15588591960","alipayName":"辛忠","billDescribe":null,"billStatus":"1","billDate":1531208083000,"userId":1988,"withdrawNumber":"0.5","withdrawDate":"2018-07-10 15:34:43","remark":"佣金提现","orderBy":null}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createDate : 2018-07-11 11:19:04
         * createDateBegin : null
         * createDateEnd : null
         * createUser : 1988
         * createUserDept : 1003
         * createUserOrg : null
         * deleteFlag : 0
         * extDate1 : null
         * extDate1Begin : null
         * extDate1End : null
         * extDate2 : null
         * extDate2Begin : null
         * extDate2End : null
         * extNum1 : null
         * extNum2 : null
         * extNum3 : null
         * dataAccessDynamicSQL : null
         * extStr1 : 15588591960
         * extStr2 : null
         * extStr3 : 1
         * extStr4 : null
         * extStr5 : null
         * id : 2699
         * modifyDate : 2018-07-11 11:19:04
         * modifyDateBegin : null
         * modifyDateEnd : null
         * modifyDateNew : null
         * modifyUser : 1988
         * weight : null
         * primaryKeys : null
         * billNumber : 15312791443687604
         * alipayAccount : 15588591960
         * alipayName : 辛忠
         * billDescribe : null
         * billStatus : 1
         * billDate : 1531279144000
         * userId : 1988
         * withdrawNumber : 0.5
         * withdrawDate : 2018-07-11 11:19:04
         * remark : 佣金提现
         * orderBy : null
         */

        private String createDate;
        private Object createDateBegin;
        private Object createDateEnd;
        private int createUser;
        private int createUserDept;
        private Object createUserOrg;
        private int deleteFlag;
        private Object extDate1;
        private Object extDate1Begin;
        private Object extDate1End;
        private Object extDate2;
        private Object extDate2Begin;
        private Object extDate2End;
        private Object extNum1;
        private Object extNum2;
        private Object extNum3;
        private Object dataAccessDynamicSQL;
        private String extStr1;
        private Object extStr2;
        private String extStr3;
        private Object extStr4;
        private Object extStr5;
        private int id;
        private String modifyDate;
        private Object modifyDateBegin;
        private Object modifyDateEnd;
        private Object modifyDateNew;
        private int modifyUser;
        private Object weight;
        private Object primaryKeys;
        private String billNumber;
        private String alipayAccount;
        private String alipayName;
        private Object billDescribe;
        private String billStatus;
        private long billDate;
        private int userId;
        private String withdrawNumber;
        private String withdrawDate;
        private String remark;
        private Object orderBy;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getCreateDateBegin() {
            return createDateBegin;
        }

        public void setCreateDateBegin(Object createDateBegin) {
            this.createDateBegin = createDateBegin;
        }

        public Object getCreateDateEnd() {
            return createDateEnd;
        }

        public void setCreateDateEnd(Object createDateEnd) {
            this.createDateEnd = createDateEnd;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public int getCreateUserDept() {
            return createUserDept;
        }

        public void setCreateUserDept(int createUserDept) {
            this.createUserDept = createUserDept;
        }

        public Object getCreateUserOrg() {
            return createUserOrg;
        }

        public void setCreateUserOrg(Object createUserOrg) {
            this.createUserOrg = createUserOrg;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public Object getExtDate1() {
            return extDate1;
        }

        public void setExtDate1(Object extDate1) {
            this.extDate1 = extDate1;
        }

        public Object getExtDate1Begin() {
            return extDate1Begin;
        }

        public void setExtDate1Begin(Object extDate1Begin) {
            this.extDate1Begin = extDate1Begin;
        }

        public Object getExtDate1End() {
            return extDate1End;
        }

        public void setExtDate1End(Object extDate1End) {
            this.extDate1End = extDate1End;
        }

        public Object getExtDate2() {
            return extDate2;
        }

        public void setExtDate2(Object extDate2) {
            this.extDate2 = extDate2;
        }

        public Object getExtDate2Begin() {
            return extDate2Begin;
        }

        public void setExtDate2Begin(Object extDate2Begin) {
            this.extDate2Begin = extDate2Begin;
        }

        public Object getExtDate2End() {
            return extDate2End;
        }

        public void setExtDate2End(Object extDate2End) {
            this.extDate2End = extDate2End;
        }

        public Object getExtNum1() {
            return extNum1;
        }

        public void setExtNum1(Object extNum1) {
            this.extNum1 = extNum1;
        }

        public Object getExtNum2() {
            return extNum2;
        }

        public void setExtNum2(Object extNum2) {
            this.extNum2 = extNum2;
        }

        public Object getExtNum3() {
            return extNum3;
        }

        public void setExtNum3(Object extNum3) {
            this.extNum3 = extNum3;
        }

        public Object getDataAccessDynamicSQL() {
            return dataAccessDynamicSQL;
        }

        public void setDataAccessDynamicSQL(Object dataAccessDynamicSQL) {
            this.dataAccessDynamicSQL = dataAccessDynamicSQL;
        }

        public String getExtStr1() {
            return extStr1;
        }

        public void setExtStr1(String extStr1) {
            this.extStr1 = extStr1;
        }

        public Object getExtStr2() {
            return extStr2;
        }

        public void setExtStr2(Object extStr2) {
            this.extStr2 = extStr2;
        }

        public String getExtStr3() {
            return extStr3;
        }

        public void setExtStr3(String extStr3) {
            this.extStr3 = extStr3;
        }

        public Object getExtStr4() {
            return extStr4;
        }

        public void setExtStr4(Object extStr4) {
            this.extStr4 = extStr4;
        }

        public Object getExtStr5() {
            return extStr5;
        }

        public void setExtStr5(Object extStr5) {
            this.extStr5 = extStr5;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getModifyDateBegin() {
            return modifyDateBegin;
        }

        public void setModifyDateBegin(Object modifyDateBegin) {
            this.modifyDateBegin = modifyDateBegin;
        }

        public Object getModifyDateEnd() {
            return modifyDateEnd;
        }

        public void setModifyDateEnd(Object modifyDateEnd) {
            this.modifyDateEnd = modifyDateEnd;
        }

        public Object getModifyDateNew() {
            return modifyDateNew;
        }

        public void setModifyDateNew(Object modifyDateNew) {
            this.modifyDateNew = modifyDateNew;
        }

        public int getModifyUser() {
            return modifyUser;
        }

        public void setModifyUser(int modifyUser) {
            this.modifyUser = modifyUser;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getPrimaryKeys() {
            return primaryKeys;
        }

        public void setPrimaryKeys(Object primaryKeys) {
            this.primaryKeys = primaryKeys;
        }

        public String getBillNumber() {
            return billNumber;
        }

        public void setBillNumber(String billNumber) {
            this.billNumber = billNumber;
        }

        public String getAlipayAccount() {
            return alipayAccount;
        }

        public void setAlipayAccount(String alipayAccount) {
            this.alipayAccount = alipayAccount;
        }

        public String getAlipayName() {
            return alipayName;
        }

        public void setAlipayName(String alipayName) {
            this.alipayName = alipayName;
        }

        public Object getBillDescribe() {
            return billDescribe;
        }

        public void setBillDescribe(Object billDescribe) {
            this.billDescribe = billDescribe;
        }

        public String getBillStatus() {
            return billStatus;
        }

        public void setBillStatus(String billStatus) {
            this.billStatus = billStatus;
        }

        public long getBillDate() {
            return billDate;
        }

        public void setBillDate(long billDate) {
            this.billDate = billDate;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getWithdrawNumber() {
            return withdrawNumber;
        }

        public void setWithdrawNumber(String withdrawNumber) {
            this.withdrawNumber = withdrawNumber;
        }

        public String getWithdrawDate() {
            return withdrawDate;
        }

        public void setWithdrawDate(String withdrawDate) {
            this.withdrawDate = withdrawDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }
    }
}
