package com.xz.cenco.wisdom.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/2/25.
 */
@Entity
public class Wisdom {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String text;

    private String comment;

    private Long type;

    private Date startDate;
    private Date stopDate;

    @NotNull
    private Date date;


    @Generated(hash = 1672336008)
    public Wisdom(Long id, @NotNull String text, String comment, Long type,
            Date startDate, Date stopDate, @NotNull Date date) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.type = type;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.date = date;
    }

    @Generated(hash = 1827825799)
    public Wisdom() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getType() {
        return this.type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return this.stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
