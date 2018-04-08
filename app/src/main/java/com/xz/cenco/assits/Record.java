package com.xz.cenco.assits;

import com.cenco.lib.common.DateUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/25.
 */
@Entity
public class Record {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String packageName;

    @NotNull
    private String date;//yyyy-mm-dd

    private String inTime;//hh:mm:ss

    private String outTime;

    @Generated(hash = 943508128)
    public Record(Long id, @NotNull String packageName, @NotNull String date,
            String inTime, String outTime) {
        this.id = id;
        this.packageName = packageName;
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    @Generated(hash = 477726293)
    public Record() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInTime() {
        return this.inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return this.outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }


    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", date='" + date + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                '}';
    }



    /**
     * 返回驻留时间，单位秒
     * @return
     */
    public int getStayTime(){
        if (inTime == null || outTime == null){
            return 0;
        }

        Date dateIn = DateUtil.getDate(inTime, DateUtil.FORMAT_HMS);
        Date dateOut = DateUtil.getDate(outTime, DateUtil.FORMAT_HMS);
        int seconds = (int) ((dateOut.getTime()-dateIn.getTime())/1000);
        return seconds;

    }
}
