package com.mobin.common;

import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import com.mobin.crawler.Pollutant;
import org.jsoup.select.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Mobin on 2017/9/11.
 */
public class CrawlerOptions {
    private static final Logger log = LoggerFactory.getLogger(CrawlerOptions.class);
    public String Crawlertype = null;
    public int threadNum = 1;
    private  final HashSet<String> crawlers = new HashSet<>();

    public CrawlerOptions(String[] args){
        for (int i = 0; i < args.length; i ++) {
            String a = args[i];
            switch (a) {
                case "-Crawlertype":
                    Crawlertype = args[++ i];  //启动命令中以逗号分开来标记启动多个爬虫程序
                    break;
                case "-threadNum":
                    threadNum = Integer.valueOf(args[++ i]);
                    break;
                default:
                    log.error("无效的参数" + a);
                    System.exit(-1);
            }
        }
        crawlers.addAll(Arrays.asList(Crawlertype.split(",")));
    }

    public void createCrawler(){
        if (crawlers.contains("capital")){
            Long cstartTime = System.currentTimeMillis();
            log.info("开始爬取世界各首都的天气情况。");
            Capital capital = new Capital();
            Spider.create(capital).
                    addUrl(Config.getStringProperties("capitalURL").split(",")).
                    thread(threadNum).
                    run();
            Long cendTime = System.currentTimeMillis();
            log.info("capital天气数据爬取"+capital.count+"条数据并成功插入pg库：" + (cendTime - cstartTime));
            Long istartTime = System.currentTimeMillis();
            capital.insertTopostgres();
            Long iendTime = System.currentTimeMillis();
            log.info("capital天气数据成功插入pg库中，总耗时："  + (iendTime - istartTime));
        }

        if (Crawlertype.contains("pollutant")){
            Spider.create(new Pollutant()).addUrl(Config.getStringProperties("pollutantURL")).run();
        }
    }

    @Override
    public String toString() {
        return "CrawlerOptions：[type=" +Crawlertype+",threadNum="+threadNum+"]";
    }
}
