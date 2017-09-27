package com.mobin.domain;

import java.sql.Timestamp;

/**
 * Created by Mobin on 2017/9/24.
 */
public class ChinaCityData {
    private String tp;
    private String weather;
    private String temperature;
    private String rainfall;
    private String windRate;
    private String windDirection;
    private String airPressure;
    private String humidity;
    private String cludeage;
    private String visibility;

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
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

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
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

    public String getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(String airPressure) {
        this.airPressure = airPressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCludeage() {
        return cludeage;
    }

    public void setCludeage(String cludeage) {
        this.cludeage = cludeage;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "ChinaCityData{" +
                "tp=" + tp +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", rainfall='" + rainfall + '\'' +
                ", windRate='" + windRate + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", airPressure='" + airPressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", cludeage='" + cludeage + '\'' +
                ", visibility='" + visibility + '\'' +
                '}';
    }
}
