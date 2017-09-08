package com.mobin;

import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mobin on 2017/9/8.
 */
public class JsonFormHttp {
    @Test
    public void json(){
       String urlStr = "http://api.map.baidu.com/place/v2/search?q=%E9%A5%AD%E5%BA%97&region=%E5%8C%97%E4%BA%AC&output=json&ak=fuGPCnbd3cCCoIax7HGSezSHhxrWmaHm";
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
            public void process(Page page) {
               int size =  new JsonPathSelector("$.results[*]").selectList(
                        page.getRawText()).size();
                JsonPathSelector selector = new JsonPathSelector("");

                for(int i = 0; i< 10; i ++){
//                    System.out.println(new JsonPathSelector("$.results[" + i + "].name").select(page.getRawText()+ "\t"));
//                    System.out.println(new JsonPathSelector("$.results[" + i + "].uid").select(page.getRawText()+ "\t"));
                    System.out.println(selector.select("$.results[" + i + "].name"));
                }
            }

            public Site getSite() {
                return site;
            }
        }).addUrl(urlStr).run();
    }
}
