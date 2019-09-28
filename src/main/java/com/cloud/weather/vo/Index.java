package com.cloud.weather.vo;

import java.io.Serializable;

public class Index implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String level;
    private String desc;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
