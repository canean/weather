package com.cloud.weather.vo;

import java.io.Serializable;

public class Alarm  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String alarm_type;
    private String alarm_level;
    private String alarm_content;

    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getAlarm_level() {
        return alarm_level;
    }

    public void setAlarm_level(String alarm_level) {
        this.alarm_level = alarm_level;
    }

    public String getAlarm_content() {
        return alarm_content;
    }

    public void setAlarm_content(String alarm_content) {
        this.alarm_content = alarm_content;
    }
}
