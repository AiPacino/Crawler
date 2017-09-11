package com.mobin;

import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import com.mobin.crawler.Pollutant;
import org.junit.Test;
import us.codecraft.webmagic.Spider;

/**
 * Created by Mobin on 2017/9/8.
 */
public class CrawlerTest {
    @Test
    public void pollutant(){
        Spider.create(new Pollutant()).addUrl(Config.getStringProperties("pollutantURL")).run();
    }

    @Test
    public void capital(){
        Long startTime = System.currentTimeMillis();
        Spider.create(new Capital()).
                addUrl(Config.getStringProperties("capitalURL").split(",")).
                thread(2).run();
        Long endTime = System.currentTimeMillis();
        System.out.println("爬虫总消耗时间：" + (endTime - startTime)+ "ms");
    }
}
