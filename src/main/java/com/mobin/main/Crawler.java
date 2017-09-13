package com.mobin.main;

import com.mobin.common.CrawlerOptions;
import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;
import us.codecraft.webmagic.Spider;

import java.io.*;
import java.util.Properties;

/**
 * Created by Mobin on 2017/9/11.
 */
public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);

    static {
        initLog4j();
    }

    public static synchronized void initLog4j(){
        String log4jFile = System.getProperty("log4j");
        InputStream in = null;
        if (log4jFile != null){
            try {
                in = new FileInputStream(new File(log4jFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (in == null) {
            in = Config.class.getResourceAsStream("/log4j.properties");
        }
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("日志配置加载完成！");
    }

    public static void main(String[] args) {
        CrawlerOptions options = new CrawlerOptions(args);
        run(options);
    }

    public static void run(CrawlerOptions options){
         log.info("Starting data crawler," + options);
         options.createCrawler();

    }
}
