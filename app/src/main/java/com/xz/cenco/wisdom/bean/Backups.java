package com.xz.cenco.wisdom.bean;

import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomType;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 * 备份类
 */

public class Backups {

    private List<Wisdom> wisdoms;
    private String date;
    private Setting setting;

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public List<Wisdom> getWisdoms() {
        return wisdoms;
    }

    public void setWisdoms(List<Wisdom> wisdoms) {
        this.wisdoms = wisdoms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
