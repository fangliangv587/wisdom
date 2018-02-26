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
public class WisdomType {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Date date;

    @Generated(hash = 1393015048)
    public WisdomType(Long id, @NotNull String name, @NotNull Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Generated(hash = 2054877767)
    public WisdomType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
