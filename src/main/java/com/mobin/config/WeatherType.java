package com.mobin.config;

/**
 * Created by Mobin on 2017/9/24.
 */
public class WeatherType {
    public static String get(String type){
        switch (type){
            case "1":
                return "晴";
            case "2":
                return "阴";
            case "3":
                return "雨";
            case "4":
                return "雷阵雨";
            default:
                throw new RuntimeException("Unknow Type: " + type);
        }
    }
}
