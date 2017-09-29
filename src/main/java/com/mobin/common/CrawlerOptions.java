package com.mobin.common;

import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import com.mobin.crawler.Pollutant;
import com.mobin.domain.ChinaCityData;
import org.jsoup.select.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mobin on 2017/9/11.
 */
public class CrawlerOptions {
    private static final Logger log = LoggerFactory.getLogger(CrawlerOptions.class);
    public String Crawlertype = null;
    public int threadNum = 1;
    private  final HashSet<String> crawlers = new HashSet<>();
    private PageProcessor pageProcessor;

    public CrawlerOptions(String[] args){
        for (int i = 0; i < args.length; i ++) {
            String a = args[i];
            switch (a) {
                case "-CrawlerType":
                    Crawlertype = args[++ i];  //启动命令中以逗号分开来标记启动多个爬虫程序
                    break;
                case "-ThreadNum":
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
            log.info("开始爬取世界各首都的天气情况........................");
            Capital capital = new Capital();
            crawlerTimeConsuming(capital,"capitalURL", capital.count);
            insertTopg(capital);
        }

        if (Crawlertype.contains("pollutant")){
            log.info("开始爬取空气质量健康指数........................");
            Pollutant pollutant = new Pollutant();
            crawlerTimeConsuming(pollutant,"pollutantURL", pollutant.count);
            insertTopg(pollutant);
          //  Spider.create(new Pollutant()).addUrl(Config.getStringProperties("pollutantURL")).run();
        }

        if (Crawlertype.contains("chinaData")){
            log.info("对天气静态数据进行初始化........................");
            Config.initWindRateMap();
            Config.initWeatherMap();
            Config.initWindDirectionMap();
            log.info("开始爬取中国各城市的天气情况.........................");

        }
    }


    public void crawlerTimeConsuming(PageProcessor spider, String url, AtomicInteger count){
        Long startTime = timestamp();
        Spider.create(spider).
                addUrl(Config.getStringProperties(url).split(",")).
                thread(threadNum).
                run();
        Long endTime = timestamp();
        log.info("本次共爬取"+ count + "条数据,耗时：" +  (endTime - startTime) + "ms");
    }

    public void insertTopg(InsertToPg insert){
        try {
            Long istartTime = timestamp();
            insert.insertTopostgres();
            Long iendTime = timestamp();
            log.info("capital天气数据成功插入pg库中，总耗时："  + (iendTime - istartTime) + "ms");
        }catch (Exception e){
            log.error("数据插入失败" + e);
        }
    }

    public Long timestamp(){
        return  System.currentTimeMillis();
    }



    @Override
    public String toString() {
        return "CrawlerOptions：[type=" +Crawlertype+",threadNum="+threadNum+"]";
    }
}
