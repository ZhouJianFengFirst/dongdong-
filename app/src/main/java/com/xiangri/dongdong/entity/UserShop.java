package com.xiangri.dongdong.entity;

public class UserShop {
    private String url;
    private int pid;

    public UserShop(String url, int pid) {
        this.url = url;
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
