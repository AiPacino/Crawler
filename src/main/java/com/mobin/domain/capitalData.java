package com.mobin.domain;

import java.sql.Timestamp;

/**
 * Created by Mobin on 2017/9/12.
 * 地区"12日09时,多云,22℃,西风,微风"
 * 7:30, 11:30 , 18:00更新一次
 */
public class CapitalData{
    private String city;
    private String time;
    private String weather;
    private String centigrade;
    private String windDirection;
    private String windRate;
    private Timestamp created_at;
    private Timestamp updated_at;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCentigrade() {
        return centigrade;
    }

    public void setCentigrade(String centigrade) {
        this.centigrade = centigrade;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindRate() {
        return windRate;
    }

    public void setWindRate(String windRate) {
        this.windRate = windRate;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "CapitalData{" +
                "city='" + city + '\'' +
                "time='" + time + '\'' +
                ", weather='" + weather + '\'' +
                ", centigrade='" + centigrade + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", windRate='" + windRate + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
