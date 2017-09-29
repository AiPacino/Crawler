package com.mobin.config;

import java.io.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Mobin on 2017/9/8.
 */
public class Config {
    public static final String VERSION = "1.0.0";
    private static final Properties config = loadConfig();

    public static final String BAIDU_AK = getStringProperties("baidi_ak");
    public static final String TENCENT_AK = getStringProperties("tencent_ak");
    public static  final  Calendar calendar= Calendar.getInstance();
    public static  final  int YEAR = calendar.get(Calendar.YEAR);
    public static  final  int MONTH = calendar.get(Calendar.MONTH)+ 1;
    public static  final  Timestamp TIMESTAM = new Timestamp(calendar.getTimeInMillis());
    public static final HashMap<Integer, String> windRateMap = new HashMap<>();
    public static final HashMap<Integer, String> weatherMap = new HashMap<>(44);
    public static final HashMap<Integer, String> windDirectionMap = new HashMap<>();

    private static Properties loadConfig(){
        String confFile  = System.getProperty("Crawler"); //可以在VM启动时配置该参数：-DCrawler=路径
        InputStream in = null;
        if (confFile != null){
            try {
                in = new FileInputStream(new File(confFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (in == null) {
            in = Config.class.getResourceAsStream("/Crawler.properties");
        }
        Properties properties = new Properties();
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            throw  new RuntimeException("Failed to load Crawler.properties");
        }
      return properties;
    }

    //获取配置文件属性
    public static String getStringProperties(String key) {
        String value = config.getProperty(key);
        if (value == null) {
            return null;
        }
        value = value.trim();
        return value;
    }

    public static void initWindRateMap(){
        windRateMap.put(0, "<3级");
        windRateMap.put(1, "3-4级");
        windRateMap.put(2, "4-5级");
        windRateMap.put(3, "5-6级");
        windRateMap.put(4, "6-7级");
        windRateMap.put(5, "7-8级");
        windRateMap.put(6, "8-9级");
        windRateMap.put(7, "9-10级");
        windRateMap.put(8, "10-11级");
        windRateMap.put(9, "11-12级");
    }

    public static void initWeatherMap(){
        weatherMap.put(0,"晴");
        weatherMap.put(1,"多云");
        weatherMap.put(2,"阴");
        weatherMap.put(3,"阵雨");
        weatherMap.put(4,"雷阵雨");
        weatherMap.put(5,"雷阵雨伴有冰雹");
        weatherMap.put(6,"雨夹雪");
        weatherMap.put(7,"小雨");
        weatherMap.put(8,"中雨");
        weatherMap.put(9,"大雨");
        weatherMap.put(10,"暴雨");
        weatherMap.put(11,"大暴雨");
        weatherMap.put(12,"特大暴雨");
        weatherMap.put(13,"阵雪");
        weatherMap.put(14,"小雪");
        weatherMap.put(15,"中雪");
        weatherMap.put(16,"大雪");
        weatherMap.put(17,"暴雪");
        weatherMap.put(18,"雾");
        weatherMap.put(19,"冻雨");
        weatherMap.put(20,"沙尘暴");
        weatherMap.put(21,"小到中雨");
        weatherMap.put(22,"中到大雨");
        weatherMap.put(23,"大到暴雨");
        weatherMap.put(24,"暴雨到大暴雨");
        weatherMap.put(25,"大暴雨到特大暴雨");
        weatherMap.put(26,"小到中雪");
        weatherMap.put(27,"中到大雪");
        weatherMap.put(28,"大到暴雪");
        weatherMap.put(29,"浮尘");
        weatherMap.put(30,"扬沙");
        weatherMap.put(31,"强沙尘暴");
        weatherMap.put(32,"浓雾");
        weatherMap.put(53,"霾");
        weatherMap.put(99,"无");
        weatherMap.put(49,"强浓雾");
        weatherMap.put(54,"中度霾");
        weatherMap.put(55,"重度霾");
        weatherMap.put(56,"严重霾");
        weatherMap.put(57,"大雾");
        weatherMap.put(58,"特强浓雾");
        weatherMap.put(97,"雨");
        weatherMap.put(98,"雪");
        weatherMap.put(301,"雨");
        weatherMap.put(302,"雪");
    }

    public static void initWindDirectionMap(){
        windDirectionMap.put(0,"无持续风向");
        windDirectionMap.put(1,"东北风");
        windDirectionMap.put(2,"东风");
        windDirectionMap.put(3,"东南风");
        windDirectionMap.put(4,"南风");
        windDirectionMap.put(5,"西南风");
        windDirectionMap.put(6,"西风");
        windDirectionMap.put(7,"西北风");
        windDirectionMap.put(8,"北风");
        windDirectionMap.put(9,"旋转风");
    }
}
