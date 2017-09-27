package com.mobin.domain;

import java.sql.Timestamp;

/**
 * Created by Mobin on 2017/9/27.
 */
public class PollutantData {
    private String station;
    private Timestamp collected_at;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double co;
    private Double pm10;
    private Double pm25;
    private Timestamp created_at;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Timestamp getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(Timestamp collected_at) {
        this.collected_at = collected_at;
    }

    public Double getNo2() {
        return no2;
    }

    public void setNo2(Double no2) {
        this.no2 = no2;
    }

    public Double getO3() {
        return o3;
    }

    public void setO3(Double o3) {
        this.o3 = o3;
    }

    public Double getSo2() {
        return so2;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    public Double getCo() {
        return co;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public Double getPm10() {
        return pm10;
    }

    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    public Double getPm25() {
        return pm25;
    }

    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "PollutantData{" +
                "station='" + station + '\'' +
                ", collected_at=" + collected_at +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", so2=" + so2 +
                ", co=" + co +
                ", pm10=" + pm10 +
                ", pm25=" + pm25 +
                ", created_at=" + created_at +
                '}';
    }
}
