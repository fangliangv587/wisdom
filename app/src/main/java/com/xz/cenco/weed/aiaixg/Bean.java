package com.xz.cenco.weed.aiaixg;

/**
 * Created by Administrator on 2018/5/29.
 */
public class Bean {

    /**
     * info : xxx
     * status : 0
     * url :
     */

    private String info;
    private int status;
    private String url;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "info='" + info + '\'' +
                ", status=" + status +
                ", url='" + url + '\'' +
                '}';
    }
}
