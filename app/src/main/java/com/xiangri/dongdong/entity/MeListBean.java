package com.xiangri.dongdong.entity;
public  class MeListBean   {
    private String names;
    private int images;


    public MeListBean(String names, int images) {
        this.names = names;
        this.images = images;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
