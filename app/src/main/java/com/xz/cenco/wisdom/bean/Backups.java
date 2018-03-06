package com.xz.cenco.wisdom.bean;

import com.xz.cenco.wisdom.entity.Wisdom;
import com.xz.cenco.wisdom.entity.WisdomType;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 * 备份类
 */

public class Backups {

    private List<Backup> data;

    public List<Backup> getData() {
        return data;
    }

    public void setData(List<Backup> data) {
        this.data = data;
    }

    public static class Backup{
        private WisdomType type;
        private List<Wisdom> wisdoms;

        public WisdomType getType() {
            return type;
        }

        public void setType(WisdomType type) {
            this.type = type;
        }

        public List<Wisdom> getWisdoms() {
            return wisdoms;
        }

        public void setWisdoms(List<Wisdom> wisdoms) {
            this.wisdoms = wisdoms;
        }
    }
}
