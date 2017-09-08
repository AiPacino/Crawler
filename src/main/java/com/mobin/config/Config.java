package com.mobin.config;

import java.io.*;
import java.util.Properties;

/**
 * Created by Mobin on 2017/9/8.
 */
public class Config {
    public static final String VERSION = "1.0.0";
    private static final Properties config = loadConfig();

    public static final String BAIDU_AK = getStringProperties("baidi_ak");
    public static final String TENCENT_AK = getStringProperties("tencent_ak");

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
}
