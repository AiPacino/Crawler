package com.mobin.domain;

import java.sql.Timestamp;

/**
 * Created by Mobin on 2017/9/24.
 */
public class ChinaCityData {
    private String time;
    private String weather;
    private String temperature;
    private String windRate;
    private String windDirection;
    private String sunsup;
    private String sunset;
    private Timestamp created_at;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindRate() {
        return windRate;
    }

    public void setWindRate(String windRate) {
        this.windRate = windRate;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getSunsup() {
        return sunsup;
    }

    public void setSunsup(String sunsup) {
        this.sunsup = sunsup;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }


    @Override
    public String toString() {
        return "ChinaCityData{" +
                "time='" + time + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", windRate='" + windRate + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", sunsup='" + sunsup + '\'' +
                ", sunset='" + sunset + '\'' +
                ", sunset='" + created_at + '\'' +
                '}';
    }
}
