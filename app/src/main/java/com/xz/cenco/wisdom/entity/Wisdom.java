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

    private Long type;

    @NotNull
    private Date date;


    @Generated(hash = 279511505)
    public Wisdom(Long id, @NotNull String text, Long type, @NotNull Date date) {
        this.id = id;
        this.text = text;
        this.type = type;
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

}
