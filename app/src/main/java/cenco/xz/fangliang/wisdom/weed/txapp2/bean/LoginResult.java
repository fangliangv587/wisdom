package cenco.xz.fangliang.wisdom.weed.txapp2.bean;

/**
 * Created by Administrator on 2018/7/11.
 */

public class LoginResult {

    /**
     * token : 7cfaafd8a549845bf728224cb1e6a9bfadeed16bc1eeb4ef183d7e05
     * sessionId : 195b475e-ff94-422f-abb1-ff1cb375ca54
     * data : {"id":1988,"displayName":"辛忠","code":"1","loginName":"15588591960","mobile":"0","email":null,"dutyId":null,"photo":null,"deptId":1003,"leaderId":50,"deptLeader":"0.5","chargeLeader":100,"weight":50,"deptName":"用户","orgId":1,"orgName":"APP人员","extstr1":null,"officeTel":"15588591960","cardNo":null,"isDriver":1,"sex":"sex_1","kind":"0","level":null,"status":"status_0","political":null,"orderNo":2,"jobTitle":null,"entryPoliticalDate":null,"birthday":null,"ethnic":null,"hometown":"","degree":"1","cername":"0.5","maritalStatus":null,"isAdmin":0,"payCardNo":"866822033471269","cardBank":null,"cardName":null,"lastLonginDate":null,"wrongCount":null,"lockStartDate":null,"officeAddress":null,"isLeader":null,"answer":null,"question":null,"openCale":null,"isCheck":null,"lockType":null,"groupTel":null,"modifyPwdFlag":0,"theme":null}
     * state : success
     */

    private String token;
    private String sessionId;
    private DataBean data;
    private String state;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * id : 1988
         * displayName : 辛忠
         * code : 1
         * loginName : 15588591960
         * mobile : 0
         * email : null
         * dutyId : null
         * photo : null
         * deptId : 1003
         * leaderId : 50
         * deptLeader : 0.5
         * chargeLeader : 100
         * weight : 50
         * deptName : 用户
         * orgId : 1
         * orgName : APP人员
         * extstr1 : null
         * officeTel : 15588591960
         * cardNo : null
         * isDriver : 1
         * sex : sex_1
         * kind : 0
         * level : null
         * status : status_0
         * political : null
         * orderNo : 2
         * jobTitle : null
         * entryPoliticalDate : null
         * birthday : null
         * ethnic : null
         * hometown :
         * degree : 1
         * cername : 0.5
         * maritalStatus : null
         * isAdmin : 0
         * payCardNo : 866822033471269
         * cardBank : null
         * cardName : null
         * lastLonginDate : null
         * wrongCount : null
         * lockStartDate : null
         * officeAddress : null
         * isLeader : null
         * answer : null
         * question : null
         * openCale : null
         * isCheck : null
         * lockType : null
         * groupTel : null
         * modifyPwdFlag : 0
         * theme : null
         */

        private boolean isVertify;
        private int id;
        private String displayName;
        private String code;
        private String loginName;
        private String mobile;
        private Object email;
        private Object dutyId;
        private Object photo;
        private int deptId;
        private int leaderId;
        private String deptLeader;
        private int chargeLeader;
        private int weight;
        private String deptName;
        private int orgId;
        private String orgName;
        private Object extstr1;
        private String officeTel;
        private Object cardNo;
        private int isDriver;
        private String sex;
        private String kind;
        private Object level;
        private String status;
        private Object political;
        private int orderNo;
        private Object jobTitle;
        private Object entryPoliticalDate;
        private Object birthday;
        private Object ethnic;
        private String hometown;
        private String degree;
        private String cername;
        private Object maritalStatus;
        private int isAdmin;
        private String payCardNo;
        private Object cardBank;
        private Object cardName;
        private Object lastLonginDate;
        private Object wrongCount;
        private Object lockStartDate;
        private Object officeAddress;
        private Object isLeader;
        private Object answer;
        private Object question;
        private Object openCale;
        private Object isCheck;
        private Object lockType;
        private Object groupTel;
        private int modifyPwdFlag;
        private Object theme;

        public boolean isVertify() {
            return officeTel!=null;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getDutyId() {
            return dutyId;
        }

        public void setDutyId(Object dutyId) {
            this.dutyId = dutyId;
        }

        public Object getPhoto() {
            return photo;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getLeaderId() {
            return leaderId;
        }

        public void setLeaderId(int leaderId) {
            this.leaderId = leaderId;
        }

        public String getDeptLeader() {
            return deptLeader;
        }

        public void setDeptLeader(String deptLeader) {
            this.deptLeader = deptLeader;
        }

        public int getChargeLeader() {
            return chargeLeader;
        }

        public void setChargeLeader(int chargeLeader) {
            this.chargeLeader = chargeLeader;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public Object getExtstr1() {
            return extstr1;
        }

        public void setExtstr1(Object extstr1) {
            this.extstr1 = extstr1;
        }

        public String getOfficeTel() {
            return officeTel;
        }

        public void setOfficeTel(String officeTel) {
            this.officeTel = officeTel;
        }

        public Object getCardNo() {
            return cardNo;
        }

        public void setCardNo(Object cardNo) {
            this.cardNo = cardNo;
        }

        public int getIsDriver() {
            return isDriver;
        }

        public void setIsDriver(int isDriver) {
            this.isDriver = isDriver;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getPolitical() {
            return political;
        }

        public void setPolitical(Object political) {
            this.political = political;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public Object getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(Object jobTitle) {
            this.jobTitle = jobTitle;
        }

        public Object getEntryPoliticalDate() {
            return entryPoliticalDate;
        }

        public void setEntryPoliticalDate(Object entryPoliticalDate) {
            this.entryPoliticalDate = entryPoliticalDate;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getEthnic() {
            return ethnic;
        }

        public void setEthnic(Object ethnic) {
            this.ethnic = ethnic;
        }

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getCername() {
            return cername;
        }

        public void setCername(String cername) {
            this.cername = cername;
        }

        public Object getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(Object maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public int getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public String getPayCardNo() {
            return payCardNo;
        }

        public void setPayCardNo(String payCardNo) {
            this.payCardNo = payCardNo;
        }

        public Object getCardBank() {
            return cardBank;
        }

        public void setCardBank(Object cardBank) {
            this.cardBank = cardBank;
        }

        public Object getCardName() {
            return cardName;
        }

        public void setCardName(Object cardName) {
            this.cardName = cardName;
        }

        public Object getLastLonginDate() {
            return lastLonginDate;
        }

        public void setLastLonginDate(Object lastLonginDate) {
            this.lastLonginDate = lastLonginDate;
        }

        public Object getWrongCount() {
            return wrongCount;
        }

        public void setWrongCount(Object wrongCount) {
            this.wrongCount = wrongCount;
        }

        public Object getLockStartDate() {
            return lockStartDate;
        }

        public void setLockStartDate(Object lockStartDate) {
            this.lockStartDate = lockStartDate;
        }

        public Object getOfficeAddress() {
            return officeAddress;
        }

        public void setOfficeAddress(Object officeAddress) {
            this.officeAddress = officeAddress;
        }

        public Object getIsLeader() {
            return isLeader;
        }

        public void setIsLeader(Object isLeader) {
            this.isLeader = isLeader;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public Object getQuestion() {
            return question;
        }

        public void setQuestion(Object question) {
            this.question = question;
        }

        public Object getOpenCale() {
            return openCale;
        }

        public void setOpenCale(Object openCale) {
            this.openCale = openCale;
        }

        public Object getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(Object isCheck) {
            this.isCheck = isCheck;
        }

        public Object getLockType() {
            return lockType;
        }

        public void setLockType(Object lockType) {
            this.lockType = lockType;
        }

        public Object getGroupTel() {
            return groupTel;
        }

        public void setGroupTel(Object groupTel) {
            this.groupTel = groupTel;
        }

        public int getModifyPwdFlag() {
            return modifyPwdFlag;
        }

        public void setModifyPwdFlag(int modifyPwdFlag) {
            this.modifyPwdFlag = modifyPwdFlag;
        }

        public Object getTheme() {
            return theme;
        }

        public void setTheme(Object theme) {
            this.theme = theme;
        }
    }
}
